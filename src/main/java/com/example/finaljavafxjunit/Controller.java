package com.example.finaljavafxjunit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    protected TextField hostnameXProxyField;
    @FXML
    protected TextField portXProxyField;
    @FXML
    protected TextField versionXProxyField;
    @FXML
    private TextField serverField, hostnameDataField, portDataField, apiTokenField, providerField, apiVersionField;
    @FXML
    private TextField xjmsDebugLevelField, xjmsIntervalField;

    @FXML
    protected TextField hostnameJmsField;
    @FXML
    protected TextField portJmsField;
    @FXML
    protected TextField usernameField;
    @FXML
    protected TextField subtopicField;
    @FXML
    protected TextField pubtopicField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    private Button saveEditJmsButton, saveEditDataButton;
    protected static boolean isJmsEditable = false;
    protected static boolean isDataEditable = false;
    private static final String SECRET_KEY = "Willow@1"; // Change this to your secret key
    private static final String SALT = "Xentral";
    private Properties properties = new Properties();

    // Define a separator or identifier for each tab
    private static final String X_PROXY_IDENTIFIER = "xproxy.";
    private static final String DATA_SERVER_IDENTIFIER = "xdataserver.";
    private static final String JMS_SAFE_IDENTIFIER = "xjms.";
    private static final String JMS_PROVIDER_IDENTIFIER = "jms-provider.";
    private static final String FILENAME = "xjms.properties";
    private static final String LOCKTEXT = "locked-text-field";
    protected static Logger logger = Logger.getLogger(Controller.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadPropertiesFromFile();
            setXProxyProperties(hostnameXProxyField, portXProxyField, versionXProxyField,
                    "ip", "http-port", "version");
            setDataServerProperties(serverField, hostnameDataField, portDataField, apiTokenField, providerField, apiVersionField,
                    "server-name", "hostname", "http-port", "api-token", "data-provider", "version");
            setJmsSafeProperties(xjmsIntervalField, xjmsDebugLevelField,
                    "lifesign-interval", "debug-level");
            setJmsProviderProperties(hostnameJmsField, portJmsField, usernameField, passwordField, subtopicField, pubtopicField,
                    "hostname", "port", "username", "password", "topic-pub", "topic-sub");

            dataCheckXProxy();
            dataCheckDataServer();
            dataCheckJmsSafe();
            dataCheckJmsProvider();

            setButtonActions();
            //END
        } catch (Exception e) {
            handleInitializeException(e);
        }
    }

    protected void handleInitializeException(Exception e) {
        logger.log(Level.SEVERE, e.getMessage());
        showAndWaitAlert(Alert.AlertType.ERROR, "Initialize Failed", "Error initializing : ", e.getMessage());
    }

    protected void setXProxyProperties(TextField hostnameXProxyField, TextField portXProxyField, TextField versionXProxyField,
            String xProxyIp, String xProxyPort, String xProxyVersion) {
        setStyleClassAndEditable(hostnameXProxyField, isDataEditable);
        setStyleClassAndEditable(portXProxyField, isDataEditable);
        setStyleClassAndEditable(versionXProxyField, isDataEditable);

        hostnameXProxyField.setText(properties.getProperty(X_PROXY_IDENTIFIER + xProxyIp));
        portXProxyField.setText(properties.getProperty(X_PROXY_IDENTIFIER + xProxyPort));
        versionXProxyField.setText(properties.getProperty(X_PROXY_IDENTIFIER + xProxyVersion));
    }
    protected void setDataServerProperties(
            TextField serverField, TextField hostnameDataField, TextField portDataField, TextField apiTokenField, TextField providerField, TextField apiVersionField,
            String dataServer, String dataHostname, String dataPort, String dataApiToken, String dataProvider, String dataApiVersion) {

        //JMS PROVIDER TAB: JMS_PROVIDER_IDENTIFIER
        setStyleClassAndEditable(serverField, isDataEditable);
        setStyleClassAndEditable(hostnameDataField, isDataEditable);
        setStyleClassAndEditable(portDataField, isDataEditable);
        setStyleClassAndEditable(apiTokenField, isDataEditable);
        setStyleClassAndEditable(providerField, isDataEditable);
        setStyleClassAndEditable(apiVersionField, isDataEditable);

        serverField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataServer));
        hostnameDataField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataHostname));
        portDataField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataPort));
        apiTokenField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataApiToken));
        providerField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataProvider));
        apiVersionField.setText(properties.getProperty(DATA_SERVER_IDENTIFIER + dataApiVersion));
    }
    protected void setJmsSafeProperties(TextField xjmsIntervalField, TextField xjmsDebugLevelField,
                                       String xjmsInterval, String xjmsDebug) {
        setStyleClassAndEditable(xjmsIntervalField, isDataEditable);
        setStyleClassAndEditable(xjmsDebugLevelField, isDataEditable);

        xjmsIntervalField.setText(properties.getProperty(JMS_SAFE_IDENTIFIER + xjmsInterval));
        xjmsDebugLevelField.setText(properties.getProperty(JMS_SAFE_IDENTIFIER + xjmsDebug));
    }
    protected void setJmsProviderProperties(TextField hostnameJmsField, TextField portJmsField, TextField usernameField, TextField passwordField, TextField subtopicField, TextField pubtopicField,
            String jmsIp, String jmsPort, String jmsUsername, String jmsPassword, String jmsSubtopic, String jmsPubtopic) {

        //JMS PROVIDER TAB: JMS_PROVIDER_IDENTIFIER
        setStyleClassAndEditable(hostnameJmsField, isJmsEditable);
        setStyleClassAndEditable(portJmsField, isJmsEditable);
        setStyleClassAndEditable(usernameField, isJmsEditable);
        setStyleClassAndEditable(passwordField, isJmsEditable);
        setStyleClassAndEditable(subtopicField, isJmsEditable);
        setStyleClassAndEditable(pubtopicField, isJmsEditable);

        hostnameJmsField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsIp));
        portJmsField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsPort));
        usernameField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsUsername));
        passwordField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsPassword));
        subtopicField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsSubtopic));
        pubtopicField.setText(properties.getProperty(JMS_PROVIDER_IDENTIFIER + jmsPubtopic));
    }

    private void setStyleClassAndEditable(TextField textField, boolean isDataEditable) {
        textField.getStyleClass().add(LOCKTEXT);
        textField.setEditable(isDataEditable);
    }
    private void setButtonActions() {
        saveEditDataButton.setOnAction(event -> readDataOnly());
    }

    protected void dataCheckXProxy() {
        //Create Alert Message JUST AFTER entering key
        //XPROXY DATA VALIDATION
        hostnameXProxyField.setOnKeyTyped(event -> {
            if (!isValidString(hostnameXProxyField.getText()) || hostnameXProxyField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, "Text Type Mismatch", "Text Error", "TextField error in " + "'" + hostnameXProxyField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        portXProxyField.setOnKeyTyped(event -> {
            if (!isValidInteger(portXProxyField.getText()) || portXProxyField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, "Text Type Mismatch", "Text Error", "TextField error in " + "'" + portXProxyField.getId() + "'" + " : Input should be integer values between 1-65535");
            }
        });
        versionXProxyField.setOnKeyTyped(event -> {
            if (!isValidString(versionXProxyField.getText()) || versionXProxyField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, "Text Type Mismatch", "Text Error", "TextField error in " + "'" + versionXProxyField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
    }
    protected void dataCheckDataServer() {
        //Create Alert Message JUST AFTER entering EVENT KEY ->
        //JMS PROVIDER DATA VALIDATION
        serverField.setOnKeyTyped(event -> {
            if (!isValidString(serverField.getText()) || serverField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + serverField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        hostnameDataField.setOnKeyTyped(event -> {
            if (!isValidString(hostnameDataField.getText()) || hostnameDataField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + hostnameDataField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        portDataField.setOnKeyTyped(event -> {
            if (!isValidInteger(portDataField.getText()) || portDataField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + portDataField.getId() + "'" + " : Input should be integer values between 1-65535.");
            }
        });
        apiTokenField.setOnKeyTyped(event -> {
            if (!isValidApi(apiTokenField.getText()) || apiTokenField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + apiTokenField.getId() + "'" + " : Input should not be more than 1023 characters.");
            }
        });
        providerField.setOnKeyTyped(event -> {
            if (!isValidString(providerField.getText()) || providerField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + providerField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        apiVersionField.setOnKeyTyped(event -> {
            if (!isValidString(apiVersionField.getText()) || apiVersionField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + apiVersionField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
    }
    protected void dataCheckJmsSafe() {
        //Create Alert Message JUST AFTER entering key
        //XPROXY DATA VALIDATION
        xjmsIntervalField.setOnKeyTyped(event -> {
            if (!isValidInterval(xjmsIntervalField.getText()) || xjmsIntervalField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, "Text Type Mismatch", "Text Error", "TextField error in " + "'" + xjmsIntervalField.getId() + "'" + " : Input should be integer values between 0-120.");
            }
        });
        xjmsDebugLevelField.setOnKeyTyped(event -> {
            if (!isValidString(xjmsDebugLevelField.getText()) || xjmsDebugLevelField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, "Text Type Mismatch", "Text Error", "TextField error in " + "'" + xjmsDebugLevelField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
    }
    protected void dataCheckJmsProvider() {
        //Create Alert Message JUST AFTER entering EVENT KEY ->
        //JMS PROVIDER DATA VALIDATION
        hostnameJmsField.setOnKeyTyped(event -> {
            if (!isValidString(hostnameJmsField.getText()) || hostnameJmsField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + hostnameJmsField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        portJmsField.setOnKeyTyped(event -> {
            if (!isValidInteger(portJmsField.getText()) || portJmsField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + portJmsField.getId() + "'" + " : Input should be integer values between 1-65535");
            }
        });
        usernameField.setOnKeyTyped(event -> {
            if (!isValidString(usernameField.getText()) || usernameField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + usernameField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        passwordField.setOnKeyTyped(event -> {
            if (!isValidString(passwordField.getText()) || passwordField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + passwordField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        subtopicField.setOnKeyTyped(event -> {
            if (!isValidString(subtopicField.getText()) || subtopicField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + subtopicField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
        pubtopicField.setOnKeyTyped(event -> {
            if (!isValidString(pubtopicField.getText()) || pubtopicField.getText().isEmpty()) {
                showAndWaitAlert(Alert.AlertType.WARNING, null, null, "TextField error in " + "'" + pubtopicField.getId() + "'" + " : Input should not be more than 255 characters.");
            }
        });
    }

    public void saveDataProperties() {
        //Check for empty text fields
        if (areTextFieldsEmpty(
                hostnameXProxyField, portXProxyField, versionXProxyField,
                serverField, hostnameDataField, portDataField, apiTokenField, providerField, apiVersionField,
                xjmsIntervalField, xjmsDebugLevelField,
                hostnameJmsField, portJmsField, usernameField, passwordField, subtopicField, pubtopicField)) {
            showAndWaitAlert(Alert.AlertType.WARNING,"SAVE WARNING","Please fill in all fields before saving.","Text fields is/are empty");
            return;
        }
        //Check for valid string of "hostname", "version", 255 characters
        if (!areTextFieldsValidString(
                hostnameXProxyField.getText(), versionXProxyField.getText(),
                serverField.getText(), hostnameDataField.getText(), providerField.getText(), apiVersionField.getText(),
                xjmsDebugLevelField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING",
                    "Error in TextField : "
                            + hostnameXProxyField.getId() + " / " + versionXProxyField.getId()
                            + serverField.getId() + " / " + hostnameDataField.getId() + providerField.getId() + " / " + apiVersionField.getId()
                            + xjmsDebugLevelField.getId(),
                    "Input should not be more than 255 characters.");
            return;
        }
        //Check for integer type / values between 1-65535
        if (!areTextFieldsValidInteger(portXProxyField.getText(), portDataField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : " + portXProxyField.getId() + " / " + portDataField.getId() , "Only integer values between 1-65535");
            return;
        }
        if (!areTextFieldsValidApi(apiTokenField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : " + portXProxyField.getId() , "Input should not be more than 1023 characters.");
            return;
        }
        if (!areTextFieldsValidInterval(xjmsIntervalField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : " + portXProxyField.getId() , "Input should be integer values between 0-120.");
            return;
        }
        // Set properties for DATA SERVER
        properties.setProperty(X_PROXY_IDENTIFIER + "ip", hostnameXProxyField.getText());
        properties.setProperty(X_PROXY_IDENTIFIER + "http-port", portXProxyField.getText());
        properties.setProperty(X_PROXY_IDENTIFIER + "version", versionXProxyField.getText());

        properties.setProperty(DATA_SERVER_IDENTIFIER + "server-name", serverField.getText());
        properties.setProperty(DATA_SERVER_IDENTIFIER + "hostname", hostnameDataField.getText());
        properties.setProperty(DATA_SERVER_IDENTIFIER + "http-port", portDataField.getText());
        properties.setProperty(DATA_SERVER_IDENTIFIER + "api-token", apiTokenField.getText());
        properties.setProperty(DATA_SERVER_IDENTIFIER + "data-provider", providerField.getText());
        properties.setProperty(DATA_SERVER_IDENTIFIER + "version", apiVersionField.getText());

        properties.setProperty(JMS_SAFE_IDENTIFIER + "lifesign-interval", xjmsIntervalField.getText());
        properties.setProperty(JMS_SAFE_IDENTIFIER + "debug-level", xjmsDebugLevelField.getText());
            //Call method savePropertiesToFile()
            try {
                savePropertiesToFile();
                showAndWaitAlert(Alert.AlertType.INFORMATION,"SAVE SUCCESS","Properties saved successfully.",null);
                onSaveButtonClick();
            } catch (IOException e) {
                showAndWaitAlert(Alert.AlertType.ERROR,"SAVE ERROR","Error saving properties: ",e.getMessage());
                logger.log(Level.WARNING, e.getMessage());
            }
    }

    protected boolean areTextFieldsEmpty(TextField... textFields) {
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected boolean areTextFieldsValidString(String... values) {
        for (String value : values) {
            if (!isValidString(value)) {
                return false;
            }
        }
        return true;
    }
    protected boolean areTextFieldsValidApi(String... values) {
        for (String value : values) {
            if (!isValidApi(value)) {
                return false;
            }
        }
        return true;
    }
    protected boolean areTextFieldsValidInteger(String... values) {
        for (String value : values) {
            if (isValidInteger(value)) {
                return true;
            }
        }
        return false;
    }
    protected boolean areTextFieldsValidInterval(String... values) {
        for (String value : values) {
            if (isValidInterval(value)) {
                return true;
            }
        }
        return false;
    }

    //Data validation and range for DATA SERVER TAB: hostname, servername, provider (255)
    //Data validation and range for JMS PROVIDER TAB: hostname, username, password, subtopic, pubtopic (255)
    protected boolean isValidString(String value){
        return value.length() <= 255;
    }
    //Data validation and range for api (1023)
    protected boolean isValidApi(String value){
        return value.length() <= 1023;
    }
    //Data validation and range for portData & portJms (1 - 65535)
    protected boolean isValidInteger(String value) {
        try {
            // Try parsing the value as an integer
            int intValue = Integer.parseInt(value);
            // Check if the parsed integer is within the specified range
            return intValue >= 1 && intValue <= 65535;
        } catch (NumberFormatException e) {
            // The value is not a valid integer
            return false;
        }
    }

    protected int parseInt(String value){
        return Integer.parseInt(value);
    }
    //Data validation for "interval", (0 - 60) - OLD
    //Data validation for "interval", (0 - 120) - NEW
    private boolean isValidInterval(String value) {
        try {
            // Try parsing the value as an integer
            int intValue = Integer.parseInt(value);
            // Check if the parsed integer is within the specified range
            return intValue >= 0 && intValue <= 120;
        } catch (NumberFormatException e) {
            // The value is not a valid integer
            return false;
        }
    }


    @FXML
    public void saveJmsProperties() {
        //Check for empty text fields
        if (areTextFieldsEmpty(hostnameJmsField, usernameField, passwordField, subtopicField, pubtopicField)) {
            showAndWaitAlert(Alert.AlertType.WARNING,"SAVE WARNING","Please fill in all fields before saving.","Text fields is/are empty");
            return;
        }
        //Check for valid string of "hostname", "username", "password", "subtopic", "pubtopic", 255 characters
        if (!areTextFieldsValidString(hostnameJmsField.getText(), usernameField.getText(), passwordField.getText(), subtopicField.getText(), pubtopicField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING,
                    "SAVE WARNING",
                    "Error in TextField : " + hostnameJmsField.getId() + " / " + usernameField.getId() + " / " + passwordField.getId() + " / " + subtopicField.getId() + " / " + pubtopicField.getId() ,
                    "Input should not be more than 255 characters.");
            return;
        }
        //Check for integer type / values between 1-65535
        if (!isValidInteger(portJmsField.getText())) {
            showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : " + portJmsField.getId() , "Only integer values between 1-65535");
            return;
        }
        // Set properties for JMS PROVIDER
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "hostname", hostnameJmsField.getText());
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "port", portJmsField.getText());
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "username", usernameField.getText());
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "password", passwordField.getText());
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "topic-sub", subtopicField.getText());
        properties.setProperty(JMS_PROVIDER_IDENTIFIER + "topic-pub", pubtopicField.getText());

        //Call method savePropertiesToFile()
        try {
            savePropertiesToFile();
            showAndWaitAlert(Alert.AlertType.INFORMATION,"SAVE SUCCESS","Properties saved successfully.",null);
            onSaveButtonClick();
        } catch (IOException e) {
            showAndWaitAlert(Alert.AlertType.ERROR,"SAVE ERROR","Error saving properties: ",e.getMessage());
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    protected String encrypt(String value) {
        try {
            // ... existing code ...
            // Generate a secure random IV
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[12]; // 12 bytes IV for GCM
            random.nextBytes(iv);
            // Generate a secret key
            SecretKey secret = generateSecretKey();

            // Extract the encryption logic into a separate method
            byte[] encryptedBytes = performEncryption(value, secret, iv);

            // Combine IV and encrypted data and encode as Base64 string
            byte[] combined = combineIVAndEncryptedData(iv, encryptedBytes);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            // Handle exceptions
            handleEncryptionException(e);
            return "";
        }
    }

    // Separate method for encryption logic
    protected static byte[] performEncryption(String value, SecretKey secret, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(128, iv));
        return cipher.doFinal(value.getBytes());
    }

    // Separate method for combining IV and encrypted data
    protected byte[] combineIVAndEncryptedData(byte[] iv, byte[] encryptedBytes) {
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
        return combined;
    }
    protected void handleEncryptionException(Exception e) {
        logger.log(Level.SEVERE, "Error encrypting properties", e.getCause());
        showAndWaitAlert(Alert.AlertType.ERROR, "DECRYPT ERROR", "Error loading properties: ", e.getMessage());
    }
    protected SecretKey generateSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }
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
            handleDecryptionException(e);
        }
        return properties;
    }

    protected byte[] performDecryption(SecretKey secret, byte[] iv, byte[] encryptedBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(128, iv));
        return cipher.doFinal(encryptedBytes);
    }

    protected void loadProperties(Properties properties, byte[] decryptedBytes) throws IOException {
        properties.load(new ByteArrayInputStream(decryptedBytes));
    }

    protected void handleDecryptionException(Exception e) {
        logger.log(Level.SEVERE, "Error decrypting properties", e.getCause());
        showAndWaitAlert(Alert.AlertType.ERROR, "DECRYPT ERROR", "Error loading properties: ", e.getMessage());
    }

//        private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
//            Alert alert = createAlert(alertType, title, header, content);
//            if (alertType == Alert.AlertType.ERROR) {
//                alert.showAndWait();
//            } else {
//                alert.show();
//            }
//        }

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

    private void savePropertiesToFile() {
        try (OutputStream output = new FileOutputStream(FILENAME)) {
            // Serialize the properties
            ByteArrayOutputStream serializedProperties = new ByteArrayOutputStream();
            properties.store(serializedProperties, "");

            // Encrypt the serialized properties
            String encryptedProperties = encrypt(serializedProperties.toString());
            logger.info("Encrypted Properties : " + encryptedProperties + "\n");
            // Save the encrypted properties to the file
            output.write(encryptedProperties.getBytes());
            loadPropertiesFromFile();
        } catch (IOException e) {
            handleSavePropertiesException(e);
        }
    }
    private void handleSavePropertiesException(Exception e) {
        logger.log(Level.SEVERE, "Error saving properties", e.getCause());
        showAndWaitAlert(Alert.AlertType.ERROR, "SAVE ERROR", "Error saving properties: ", e.getMessage());
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
            logger.info("Decrypted Properties : \n");
            //Create a TreeMap to arrange the Properties Object in alphabetical order
            TreeMap<String, String> propMap = new TreeMap<>((Map) properties);
            Set<Map.Entry<String, String>> propSet;
            propSet = propMap.entrySet();
            //Print the output
            for (Map.Entry<String, String> entry : propSet) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

        } catch (IOException e) {
            handleLoadPropertiesException(e);
        }
    }
    private void handleLoadPropertiesException(Exception e) {
        logger.log(Level.SEVERE, "Error loading properties", e.getCause());
        showAndWaitAlert(Alert.AlertType.ERROR,"LOAD ERROR", "Error message: ","A new file named \"xjms.properties\" will be created when entering and saving input.");
    }
    public void readDataOnly(){
        isDataEditable = !isDataEditable;
        toggleDataEditableFields(hostnameXProxyField, portXProxyField, versionXProxyField);
        toggleDataEditableFields(serverField, hostnameDataField, portDataField, apiTokenField, providerField, apiVersionField);
        toggleDataEditableFields(xjmsIntervalField, xjmsDebugLevelField);
        if(isDataEditable) {
            saveEditDataButton.setText("SAVE");
            showAndWaitAlert(Alert.AlertType.WARNING,"EDIT WARNING", "Edit enable","Text fields UNLOCKED.");
        } else {
            saveEditDataButton.setText("EDIT");
            showAndWaitAlert(Alert.AlertType.WARNING,"EDIT WARNING", "Edit disabled","Text fields LOCKED.");
            saveDataProperties();
        }
    }
    public void readJmsOnly(){
        isJmsEditable = !isJmsEditable;
        toggleJmsEditableFields(hostnameJmsField, portJmsField, usernameField, passwordField, subtopicField, pubtopicField);

        if(isJmsEditable) {
            saveEditJmsButton.setText("SAVE");
            showAndWaitAlert(Alert.AlertType.WARNING,"EDIT WARNING", "Edit enable","Text fields UNLOCKED.");
        } else {
            saveEditJmsButton.setText("EDIT");
            showAndWaitAlert(Alert.AlertType.WARNING,"EDIT WARNING", "Edit disabled","Text fields LOCKED.");
            saveJmsProperties();
        }
    }
    private void toggleDataEditableFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setEditable(isDataEditable);
            if (isDataEditable) {
                textField.getStyleClass().remove(LOCKTEXT);
            } else {
                textField.getStyleClass().add(LOCKTEXT);
            }
        }
    }
    private void toggleJmsEditableFields(TextField... textFields) {
        for (TextField textField : textFields) {
            textField.setEditable(isJmsEditable);
            if (isJmsEditable) {
                textField.getStyleClass().remove(LOCKTEXT);
            } else {
                textField.getStyleClass().add(LOCKTEXT);
            }
        }
    }

    @FXML
    public void onSaveButtonClick() throws IOException {
        Stage propertyInfoStage = createPropertyInfoStage();
        FXMLLoader loader = createFXMLLoader();
        Parent root = loadFXML(loader);

        InfoController propertyInfoController = loader.getController();
        initializeInfoController(propertyInfoController);

        propertyInfoStage.setTitle("JMS Safe Configuration: PROPERTIES INFO");
        propertyInfoStage.setScene(new Scene(root));
        propertyInfoStage.show();
    }

    protected Stage createPropertyInfoStage() {
        return new Stage();
    }

    protected FXMLLoader createFXMLLoader() {
        return new FXMLLoader(getClass().getResource("display.fxml"));
    }

    private Parent loadFXML(FXMLLoader loader) throws IOException {
        return loader.load();
    }

    protected void initializeInfoController(InfoController controller) {
        controller.initPropertyInfo();
        controller.setMainApplicationController(this);
    }
    @FXML
    public void onSaveButtonClick2() throws IOException {
        // Load the new GUI for property information
        Stage propertyInfoStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display.fxml"));
        Parent root = loader.load();
        // Set the controller for the new GUI
        InfoController propertyInfoController = loader.getController();
        propertyInfoController.initPropertyInfo(); // Pass property data
        propertyInfoController.setMainApplicationController(this); // Pass a reference to the MainApplication controller
        //Display GUI
        propertyInfoStage.setTitle("JMS Safe Configuration: PROPERTIES INFO");
        propertyInfoStage.setScene(new Scene(root));
        propertyInfoStage.show();
    }

}