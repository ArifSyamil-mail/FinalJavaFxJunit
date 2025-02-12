package com.example.finaljavafxjunit;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoController {

    @FXML
    private static final String SECRET_KEY = "Willow@1";// Change this to your secret key
    @FXML
    private static final String SALT = "Xentral";
    @FXML
    protected Label hostnameXProxyLabel, portXProxyLabel, versionXProxyLabel;
    @FXML
    protected Label serverLabel, hostnameDataLabel, portDataLabel, apiTokenLabel, providerLabel, apiVersionLabel;
    @FXML
    protected Label xjmsIntervalLabel, xjmsDebugLevelLabel;
    @FXML
    protected Label hostnameJmsLabel, portJmsLabel, usernameLabel, passwordLabel, subtopicLabel, pubtopicLabel;
    @FXML
    private Button returnJmsButton, returnDataButton;
    @FXML
    // Define a separator or identifier for each tab
    protected static final String X_PROXY_IDENTIFIER = "xproxy.";
    @FXML
    protected static final String DATA_SERVER_IDENTIFIER = "xdataserver.";
    @FXML
    protected static final String JMS_SAFE_IDENTIFIER = "xjms.";
    @FXML
    protected static final String JMS_PROVIDER_IDENTIFIER = "jms-provider.";
    @FXML
    public Controller mainApplicationController;
    @FXML
    protected Properties properties = new Properties();
    @FXML
    private static final String FILENAME = "xjms.properties";
    @FXML
    protected static Logger logger = Logger.getLogger(InfoController.class.getName());

    public void initPropertyInfo() {
        try {
            loadPropertiesFromFile();
            setXProxyProperties();
            setDataServerProperties();
            setJmsSafeProperties();
            setJmsProviderProperties();

            setButtonActions();
            logger.log(Level.INFO, "Properties initialized successfully.");
        } catch (RuntimeException e) {
            handleInitializeError(e);
        }
    }
    protected void setXProxyProperties() {
        hostnameXProxyLabel.setText(properties.getProperty(X_PROXY_IDENTIFIER + "ip"));
        portXProxyLabel.setText(properties.getProperty(X_PROXY_IDENTIFIER + "http-port"));
        versionXProxyLabel.setText(properties.getProperty(X_PROXY_IDENTIFIER + "version"));
    }
    protected void setDataServerProperties() {
        serverLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "server-name"));
        hostnameDataLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "hostname"));
        portDataLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "http-port"));
        apiTokenLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "api-token"));
        providerLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "data-provider"));
        apiVersionLabel.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + "version"));
    }
    protected void setJmsSafeProperties() {
        xjmsIntervalLabel.setText(properties.getProperty(JMS_SAFE_IDENTIFIER + "lifesign-interval"));
        xjmsDebugLevelLabel.setText(properties.getProperty(JMS_SAFE_IDENTIFIER + "debug-level"));
    }
    protected void setJmsProviderProperties() {
        hostnameJmsLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "hostname"));
        portJmsLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "port"));
        usernameLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "username"));
        passwordLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "password"));
        subtopicLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "topic-sub"));
        pubtopicLabel.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + "topic-pub"));
    }
    private void setButtonActions() {
        returnJmsButton.setOnAction(event -> returnToMain());
        returnDataButton.setOnAction(event -> returnToMain());
    }
    //IOException, loadPropertiesFromFile()
    private void handleLoadPropertiesError(Exception e) {
        logger.log(Level.SEVERE, "Error loading properties", e);
        showAndWaitAlert(Alert.AlertType.ERROR, "LOAD ERROR","Error loading properties: ", e.getMessage());
    }
    protected void handleInitializeError(Exception e) {
        logger.log(Level.SEVERE, "Error initialize properties", e);
        showAndWaitAlert(Alert.AlertType.ERROR, "INITIALIZE ERROR","Error initialize properties: ", e.getMessage());
    }
    protected void loadPropertiesFromFile() {
        try (InputStream input = new FileInputStream(FILENAME)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder encryptedProperties = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                encryptedProperties.append(line);
            }

            // Decrypt the encrypted properties
            properties = decrypt(encryptedProperties.toString());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties", e);
            handleLoadPropertiesError(e);
        }
    }

//    private Properties decrypt(String encryptedProperties, Properties properties) {
//        try {
//            // Decode the Base64 string
//            byte[] combined = Base64.getDecoder().decode(encryptedProperties);
//
//            // Extract IV and encrypted data
//            byte[] iv = Arrays.copyOfRange(combined, 0, 12); // 12 bytes IV for GCM
//            byte[] encryptedBytes = Arrays.copyOfRange(combined, 12, combined.length);
//
//            // Generate a secret key
//            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            SecretKey tmp = factory.generateSecret(spec);
//            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
//
//            // Create a Cipher instance with GCM mode and NoPadding
//            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//            cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(128, iv));
//
//            // Decrypt the data
//            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
//
//            // Load the decrypted properties
//            properties.load(new ByteArrayInputStream(decryptedBytes));
//
//        } catch (Exception e) {
//            // Handle exceptions
//            handleDecryptionError(e);
//        }
//        return properties;
//    }
//    private void handleDecryptionError(Exception e) {
//        // Handle the decryption error more gracefully, show a user-friendly alert, etc.
//        logger.log(Level.SEVERE, "Error decrypting properties", e);
//        showAndWaitAlert(Alert.AlertType.ERROR, "DECRYPT ERROR","Error decrypt properties: ", e.getMessage());
//    }

    //DECRYPT PROPERTIES : AES/GCM/NoPadding DECRYPTION
    protected Properties decrypt(String encryptedProperties) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedProperties);
            byte[] iv = Arrays.copyOfRange(combined, 0, 12);
            byte[] encryptedBytes = Arrays.copyOfRange(combined, 12, combined.length);

            SecretKey secret = generateSecretKey();

            byte[] decryptedBytes = performDecryption(secret, iv, encryptedBytes);

            loadProperties(properties, decryptedBytes);

        } catch (Exception e) {
            handleDecryptionError(e);
        }
        return properties;
    }
    protected SecretKey generateSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
    protected byte[] performDecryption(SecretKey secret, byte[] iv, byte[] encryptedBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(128, iv));
        return cipher.doFinal(encryptedBytes);
    }
    private void loadProperties(Properties properties, byte[] decryptedBytes) throws IOException {
        properties.load(new ByteArrayInputStream(decryptedBytes));
    }
    protected void handleDecryptionError(Exception e) {
        logger.log(Level.SEVERE, "Error decrypting properties", e.getCause());
        showAndWaitAlert(Alert.AlertType.ERROR, "DECRYPT ERROR", "Error loading properties: ", e.getMessage());
    }

    protected void showAndWaitAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = createAlert(alertType, title, header, content);
        alert.showAndWait();
    }

    protected Alert createAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
    public void setMainApplicationController(Controller mainApplicationController) {
        this.mainApplicationController = mainApplicationController;
    }

    @FXML
    public void returnToMain() {
        Stage stage = (Stage) returnJmsButton.getScene().getWindow();
        stage.close();
    }
}
