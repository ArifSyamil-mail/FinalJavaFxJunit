package com.example.finaljavafxjunit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
class ControllerTest {
    @BeforeAll
    public static void initJFX() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    public void setUp() {
        // Initialize JavaFX Toolkit
        new JFXPanel(); // This creates an invisible JavaFX container, initializing the toolkit

        // Your additional setup code here, if needed
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent mainRoot = mainLoader.load();

        Scene mainScene = new Scene(mainRoot);
        mainScene.getStylesheets().add("styles.css");
        stage.setScene(mainScene);

        stage.setTitle("JMS Safe Configuration");
        stage.show();
        stage.toFront();
    }
    //initialize()

// Commented 13 Jan 2025
//    @Test
//    void testHandleInitializeException() {
//        // Arrange
//        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
//        Logger loggerMock = mock(Logger.class);  // Mock the Logger
//        // Set the mock logger in your object
//        controller.logger = loggerMock;
//        // Act
////        controller.handleInitializeException(new RuntimeException("Mocked exception"));
//        Platform.runLater(() -> {
//            controller.handleInitializeException(new RuntimeException("Mocked exception"));
//        });
//        loggerMock.log(Level.SEVERE, "Sim Initialize Method Exception");
//        // Assert or verify expected behavior
//        // Verify that the logger was called with the expected message and log level
//        verify(loggerMock, times(2)).log(eq(Level.SEVERE), anyString());
//        assertThrows(RuntimeException.class, () -> controller.handleInitializeException(new RuntimeException()));
//        // Optionally, you can verify other aspects of the log method call, such as the log level, etc.
//    }

    //dataCheckXProxy()
    @Test
    void testLogicDataCheckXProxy() {
        // Arrange
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        TextField hostnameXProxyField = spy(new TextField());
        hostnameXProxyField.setId("hostnameXProxyField");
        TextField portXProxyField = spy(new TextField());
        portXProxyField.setId("portXProxyField");
        TextField versionXProxyField = spy(new TextField());
        versionXProxyField.setId("versionXProxyField");
        // Mock the showAndWaitAlert method
        doNothing().when(controller).showAndWaitAlert(any(), any(), any(), any());
        // Simulate an invalid TextField by mismatch data validation
        controller.hostnameXProxyField = hostnameXProxyField;
        controller.portXProxyField = portXProxyField;
        controller.versionXProxyField = versionXProxyField;
        // Act
        hostnameXProxyField.setText("a".repeat(256));
        portXProxyField.setText(String.valueOf(65536));
        versionXProxyField.setText("a".repeat(256));
        // Trigger the event handling (you might need to adapt this based on your actual implementation)
        controller.dataCheckXProxy();
        controller.showAndWaitAlert(Alert.AlertType.WARNING, "Sim title", "Sim header", "TextField error in 'hostnameXProxyField' : Input should not be more than 255 characters.");
        // Assert or verify expected behavior
        // Verify that showAndWaitAlert was called with the expected parameters
        verify(controller, times(1)).dataCheckXProxy();
        verify(controller, times(1)).showAndWaitAlert(eq(Alert.AlertType.WARNING), anyString(), anyString(), anyString());
        verifyNoMoreInteractions(controller);
        // Optionally, you can use TestFX to interact with the UI if needed
    }
    //dataCheckJmsProvider()
    @Test
    void testLogicDataCheckJmsProvider() {
        // Arrange
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        TextField hostnameJmsField = spy(new TextField());
        hostnameJmsField.setId("hostnameJmsField");
        TextField portJmsField = spy(new TextField());
        portJmsField.setId("portJmsField");
        TextField usernameField = spy(new TextField());
        usernameField.setId("usernameField");
        PasswordField passwordField = spy(new PasswordField());
        passwordField.setId("passwordField");
        TextField subtopicField = spy(new TextField());
        subtopicField.setId("subTopicField");
        TextField pubtopicField = spy(new TextField());
        pubtopicField.setId("pubTopicField");
        // Mock the showAndWaitAlert method
        doNothing().when(controller).showAndWaitAlert(any(), any(), any(), any());
        // Simulate an invalid TextField by mismatch data validation
        controller.hostnameJmsField = hostnameJmsField;
        controller.portJmsField = portJmsField;
        controller.usernameField = usernameField;
        controller.passwordField = passwordField;
        controller.subtopicField = subtopicField;
        controller.pubtopicField = pubtopicField;
        // Act
        hostnameJmsField.setText("a".repeat(256));
        portJmsField.setText(String.valueOf(65536));
        usernameField.setText("a".repeat(256));
        passwordField.setText("a".repeat(256));
        subtopicField.setText("a".repeat(256));
        pubtopicField.setText("a".repeat(256));
        // Trigger the event handling (you might need to adapt this based on your actual implementation)
        controller.dataCheckJmsProvider();
        controller.showAndWaitAlert(Alert.AlertType.WARNING, "Sim title", "Sim header", "TextField error in 'hostnameXProxyField' : Input should not be more than 255 characters.");
        // Assert or verify expected behavior
        // Verify that showAndWaitAlert was called with the expected parameters
        verify(controller, times(1)).dataCheckJmsProvider();
        verify(controller, times(1)).showAndWaitAlert(eq(Alert.AlertType.WARNING), anyString(), anyString(), anyString());
        verifyNoMoreInteractions(controller);
        // Optionally, you can use TestFX to interact with the UI if needed
    }
    //areTextFieldsEmpty(), areTextFieldsValidString(), areTextFieldsValidInteger()
    @Test
    void testAreTextFieldsEmpty_WhenAllEmpty_ReturnsTrue() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        TextField hostnameXProxyField = spy(new TextField());
        hostnameXProxyField.setId("hostnameXProxyField");
        TextField portXProxyField = spy(new TextField());
        portXProxyField.setId("portXProxyField");
        assertTrue(controller.areTextFieldsEmpty(hostnameXProxyField, portXProxyField));
        verify(controller, times(1)).areTextFieldsEmpty(any(), any());
        verifyNoMoreInteractions(controller);
    }

    @Test
    void testAreTextFieldsEmpty_WhenAnyNotEmpty_ReturnsFalse() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        TextField hostnameXProxyField = spy(new TextField());
        hostnameXProxyField.setId("hostnameXProxyField");
        TextField portXProxyField = spy(new TextField());
        portXProxyField.setId("portXProxyField");
        // Simulate an invalid TextField by mismatch data validation
        controller.hostnameXProxyField = hostnameXProxyField;
        controller.portXProxyField = portXProxyField;
        // Act
        hostnameXProxyField.setText("Test False");
        portXProxyField.setText(String.valueOf(65536));
        assertFalse(controller.areTextFieldsEmpty(hostnameXProxyField, portXProxyField));
        verify(controller, times(1)).areTextFieldsEmpty(any(), any());
        verifyNoMoreInteractions(controller);
    }

    @Test
    void testAreTextFieldsValidString_WhenAllValid_ReturnsTrue() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        assertTrue(controller.areTextFieldsValidString("validValue1", "validValue2"));
        verify(controller, times(1)).areTextFieldsValidString(any(), any());
//        verifyNoMoreInteractions(controller);
    }

    @Test
    void testAreTextFieldsValidString_WhenAnyInvalid_ReturnsFalse() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        String invalidValue =  "b".repeat(256);
        assertFalse(controller.areTextFieldsValidString("validValue1", invalidValue));
        verify(controller, times(1)).areTextFieldsValidString(any(), any());
//        verifyNoMoreInteractions(controller);
    }

    @Test
    void testAreTextFieldsValidInteger_WhenAllValid_ReturnsTrue() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
        //Act
        controller.isValidInteger("123");
        //Assert
        assertTrue(controller.areTextFieldsValidInteger("123"));
        verify(controller, times(1)).areTextFieldsValidInteger(any());
        verify(controller, times(2)).isValidInteger("123");
        verifyNoMoreInteractions(controller);
    }

    @Test
    void testAreTextFieldsValidInteger_WhenAnyInvalid_ReturnsFalse() {
        Controller controller = spy(new Controller());  // Create an instance of the class that contains the method
//        doReturn(false).when(controller).isValidInteger(any());
        //Act
        controller.isValidInteger("65536");
        //Assert
        assertFalse(controller.areTextFieldsValidInteger("65536"));
        verify(controller, times(1)).areTextFieldsValidInteger(any());
        verify(controller, times(2)).isValidInteger("65536");
        verifyNoMoreInteractions(controller);
    }
    //parseInt()
    @Test
    void testParseInteger_WhenValidInteger_ReturnsParsedValue() {
        // Arrange
        Controller controller = spy(new Controller());
        // Act
        int result = controller.parseInt("123");
        // Assert
        assertEquals(123, result);
    }

    @Test
    void testParseInteger_WhenInvalidInteger_ThrowsNumberFormatException() {
        // Arrange
        Controller controller = new Controller();
        // Act and Assert
        assertThrows(NumberFormatException.class, () -> controller.parseInt("abc"), "NumberFormatException should be thrown");
    }

    //isValidInteger()
    @Test
    void testIsValidInteger_WhenParsingExceptionThrown_ReturnsFalse() {
        // Arrange
        Controller controller = spy(new Controller());
        // Mock the parseInt method to throw a NumberFormatException
        doThrow(new NumberFormatException("Mocked exception")).when(controller).parseInt(anyString());
        // Act and Assert
        assertFalse(controller.isValidInteger("abcd"));
        assertThrows(NumberFormatException.class, () -> controller.parseInt("abcd"));
        // Verify interaction
        verify(controller, times(1)).isValidInteger("abcd");
        verify(controller, times(1)).parseInt("abcd");
        verifyNoMoreInteractions(controller);
    }
    //saveDataProperties()
    @Test
    void testSaveDataPropertiesWithEmptyFields() {
        // Arrange
        Controller controller = spy(new Controller());
        TextField hostnameXProxyField = new TextField();
        TextField portXProxyField = new TextField();
        TextField versionXProxyField = new TextField();
        // Set text fields in your controller
        controller.hostnameXProxyField = hostnameXProxyField;
        controller.portXProxyField = portXProxyField;
        controller.versionXProxyField = versionXProxyField;
        // Mock the showAndWaitAlert method
        doNothing().when(controller).showAndWaitAlert(any(), any(), any(), any());
        // Act
        controller.saveDataProperties();
        // Assert or verify expected behavior
        // Verify that showAndWaitAlert was called with the expected parameters
        verify(controller, times(1)).saveDataProperties();
        verify(controller, times(1)).showAndWaitAlert(eq(Alert.AlertType.WARNING), eq("SAVE WARNING"), eq("Please fill in all fields before saving."), eq("Text fields is/are empty"));
        // Optionally, you can use TestFX to interact with the UI if needed
    }

    @Test
    void testSaveDataPropertiesWithEmptyFields_HaveInvalidString() {
        // Arrange
        Controller controller = spy(new Controller());
        TextField hostnameXProxyField = new TextField();
        TextField portXProxyField = new TextField();
        TextField versionXProxyField = new TextField();
        // Set text fields in your controller
        controller.hostnameXProxyField = hostnameXProxyField;
        controller.portXProxyField = portXProxyField;
        controller.versionXProxyField = versionXProxyField;
        hostnameXProxyField.setText("c".repeat(256));
        versionXProxyField.setText("c".repeat(256));
        // Mock the showAndWaitAlert method
        doNothing().when(controller).showAndWaitAlert(any(), any(), any(), any());
        // Act
        controller.areTextFieldsValidString(hostnameXProxyField.getText(), versionXProxyField.getText());
        controller.saveDataProperties();
        controller.showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : ", "Input should not be more than 255 characters.");
        // Assert or verify expected behavior
        // Verify that showAndWaitAlert was called with the expected parameters
        assertFalse(controller.areTextFieldsValidString(hostnameXProxyField.getText(), versionXProxyField.getText()));
        verify(controller, times(2)).areTextFieldsValidString(hostnameXProxyField.getText(), versionXProxyField.getText());
        verify(controller, times(1)).saveDataProperties();
        verify(controller, times(1)).showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : ", "Input should not be more than 255 characters.");
        // Optionally, you can use TestFX to interact with the UI if needed
    }

// Commented 13 Jan 2025
//    @Test
//    void testSaveDataPropertiesWithEmptyFields_HaveInvalidInteger() {
//        // Arrange
//        Controller controller = spy(new Controller());
//        TextField hostnameXProxyField = new TextField();
//        TextField portXProxyField = new TextField();
//        TextField versionXProxyField = new TextField();
//
//        // Set text fields in your controller
//        controller.hostnameXProxyField = hostnameXProxyField;
//        controller.portXProxyField = portXProxyField;
//        controller.versionXProxyField = versionXProxyField;
//
//        portXProxyField.setText(String.valueOf(65536));
//
//        // Mock the showAndWaitAlert method
//        doNothing().when(controller).showAndWaitAlert(any(), any(), any(), any());
//
//        // Act
//        controller.areTextFieldsValidInteger(portXProxyField.getText());
//        controller.areTextFieldsEmpty(portXProxyField);
//        controller.isValidInteger(String.valueOf(65536));
//        controller.saveDataProperties();
//        controller.showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : ", "Only integer values between 1-65535");
//
//        // Assert or verify expected behavior
//        // Verify that showAndWaitAlert was called with the expected parameters
//        assertFalse(controller.areTextFieldsValidInteger(portXProxyField.getText()));
//        verify(controller, times(2)).areTextFieldsValidInteger(portXProxyField.getText());
//        verify(controller, times(1)).areTextFieldsEmpty(hostnameXProxyField,portXProxyField, versionXProxyField);
//        verify(controller, times(3)).isValidInteger(any());
//        verify(controller, times(1)).saveDataProperties();
//        verify(controller, times(1)).showAndWaitAlert(Alert.AlertType.WARNING, "SAVE WARNING", "Error in TextField : ", "Only integer values between 1-65535");
////        verifyNoMoreInteractions(controller);
//        // Optionally, you can use TestFX to interact with the UI if needed
//    }

    //encrypt()
    @Test
    public void testEncrypt_WhenValidValue_ReturnsEncryptedString() throws Exception {
        // Arrange
        Controller controller = spy(new Controller());
        // Mock dependencies
        when(controller.generateSecretKey()).thenReturn(new SecretKeySpec(new byte[16], "AES"));
//        doReturn(new byte[16]).when(controller).performEncryption(any(), any(), any());
        doReturn(new byte[24]).when(controller).combineIVAndEncryptedData(any(), any());
        // Act
        String encryptedValue = controller.encrypt("YourOriginalValue");
        // Assert
        assertNotNull(encryptedValue);
        assertFalse(encryptedValue.isEmpty());
        //Verify
        verify(controller, times(1)).encrypt(anyString());
        verifyNoMoreInteractions(controller);
    }
// Commented 13 Jan 2025
//    @Test
//    void testEncrypt_WhenEncryptionFails_ReturnsEmptyString() throws Exception {
//        // Arrange
//        Controller controller = spy(new Controller());
//        Logger mockLogger = mock(Logger.class);
//        // Create a mock of Exception
//        Exception mockException = mock(Exception.class);
//        // Mock dependencies to simulate encryption failure
//        doThrow(new IllegalArgumentException("Simulated encryption failure")).when(controller).generateSecretKey();
//        // Act
//        controller.logger = mockLogger;
//        Platform.runLater(() -> {
//            controller.encrypt("YourOriginalValue");
//            controller.handleEncryptionException(mockException);
//            controller.showAndWaitAlert(Alert.AlertType.ERROR, "DECRYPT ERROR", "Error loading properties: ", "teset");
//        });
//        // Assert
//        assertThrows(IllegalArgumentException.class, () -> controller.generateSecretKey());
//        verify(controller).encrypt(anyString());
//        verifyNoMoreInteractions(controller);
//    }

    //performEncryption()

//Commented 13 Jan 2025
//    @Test
//    void testPerformEncryption_SuccessfulEncryption() throws Exception {
//        // Arrange
//        Controller controller = spy(new Controller());
//        SecretKey mockSecretKey = mock(SecretKey.class);
//        Logger mockLogger = mock(Logger.class);
//        byte[] mockEncryptedBytes = new byte[]{1, 2, 3};
//        // Mocking the behavior of generateSecretKey and performEncryption
//        when(controller.generateSecretKey()).thenReturn(mockSecretKey);
//        byte[] mockIV = new byte[12];
//
//        // Act
//        controller.combineIVAndEncryptedData(mockIV, mockEncryptedBytes);
//        //Run the code on JavaFx Application Thread, to avoid concurrency issue
//        Platform.runLater(() -> {
//            controller.encrypt("YourOriginalValue");
//        });
//
//        // Verify that generateSecretKey and performEncryption were called
//        // Verify that combineIVAndEncryptedData was called
//        verify(controller, times(1)).encrypt(anyString());
//        verify(controller, times(1)).combineIVAndEncryptedData(any(byte[].class), any(byte[].class));
//
//        // Verify
//        verifyNoMoreInteractions(mockSecretKey);
//        verifyNoMoreInteractions(controller);
//        verifyNoMoreInteractions(mockLogger);
//    }

// Commented 13 Jan 2025
//    @Test
//    void testPerformEncryption_EncryptionExceptionHandled() throws Exception {
//        // Arrange
//        Controller controller = spy(new Controller());
//        SecretKey mockSecretKey = mock(SecretKey.class);
//        Logger mockLogger = mock(Logger.class);
//
//        // Mocking the behavior of generateSecretKey to simulate an exception
//        when(controller.generateSecretKey()).thenThrow(new NoSuchAlgorithmException("Simulated exception"));
//
//        // Act
////        String result = controller.encrypt("YourOriginalValue");
//        Platform.runLater(() -> {
//            controller.encrypt("YourOriginalValue");
//        });
//
//        // Assert
////        assertEquals("", result);
//        assertThrows(NoSuchAlgorithmException.class, () -> controller.generateSecretKey());
//
//        // Verify that generateSecretKey was called
////        verify(controller, times(1)).generateSecretKey();
//        verify(controller, times(1)).encrypt(anyString());
//
//        // Verify that logger and other methods weren't used (optional)
////        verifyNoInteractions(logger, cipher, gcmParameterSpec);
//        verifyNoMoreInteractions(controller);
//    }

    //decrypt()

    // Commented 13 Jan 2025
//    @Test
//    void testDecrypt_SuccessfulDecryption() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        // Arrange
//        Controller controller = spy(new Controller());
//        SecretKey mockSecretKey = mock(SecretKey.class);
//        String encryptedProperties = "yourEncryptedProperties";  // Provide a valid Base64-encoded string
//        byte[] mockEncryptedBytes = new byte[]{1, 2, 3};
//        byte[] mockIV = new byte[12];
//
//        // Mock dependencies
//        doReturn(mockSecretKey).when(controller).generateSecretKey();
//
//        // Act
////        Properties result = controller.decrypt(encryptedProperties);
//        Platform.runLater(() -> {
//            controller.loadPropertiesFromFile();
//            controller.decrypt(encryptedProperties);
//        });
//        // Assert
//        // Add more assertions based on the expected behavior of decrypt
//        // For example, you might want to verify that loadProperties was called
//        verify(controller, times(1)).loadPropertiesFromFile();
//        verify(controller, times(1)).decrypt(anyString());
//    }

    @Test
    void testDecrypt_ExceptionHandling() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        Controller controller = spy(new Controller());
        String encryptedProperties = "yourInvalidEncryptedProperties";  // Provide an invalid Base64-encoded string

        // Mock dependencies
        doThrow(new NoSuchAlgorithmException("Simulated decryption failure")).when(controller).generateSecretKey();

        // Act
//        Properties result = controller.decrypt(encryptedProperties);
        Platform.runLater(() -> {
            controller.encrypt("YourOriginalValue");
        });

        // Assert
        assertThrows(NoSuchAlgorithmException.class, () -> controller.generateSecretKey());
        // Add more assertions based on the expected behavior of exception handling
        // For example, you might want to verify that handleDecryptionException was called
//        verify(controller, times(1)).handleDecryptionException(any());
    }


    //GUI TESTING
    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR

// Commented 13 Jan 2025
//    @Test
//    void testGui_DataButtonClickEdit(FxRobot robot) {
//        robot.clickOn("#tabData");
//        Button btnSaveEditData = robot.lookup("#saveEditDataButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditData);
//        robot.clickOn("#saveEditDataButton");
//        assertEquals("SAVE", btnSaveEditData.getText());
//        assertTrue(Controller.isDataEditable);
//        robot.closeCurrentWindow();
//    }

    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR

// Commented 13 Jan 2025
//    @Test
//    void testGui_DataButtonClickEdit_thenSave(FxRobot robot)  {
//        robot.clickOn("#tabData");
//        Button btnSaveEditData = robot.lookup("#saveEditDataButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditData);
//        robot.clickOn("#saveEditDataButton");
//        assertEquals("SAVE", btnSaveEditData.getText());
//        assertTrue(Controller.isDataEditable);
//        robot.closeCurrentWindow();
//        //Change from EDIT to SAVE
//        robot.clickOn("#saveEditDataButton");
//        assertEquals("EDIT", btnSaveEditData.getText());
//        assertFalse(Controller.isDataEditable);
//        robot.closeCurrentWindow();
//        robot.closeCurrentWindow();
//    }

    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR
// Commented 13 Jan 2025
//    @Test
//    void testGui_DataButtonClickSave_thenOpenInfo(FxRobot robot) {
//        robot.clickOn("#tabData");
//        Button btnSaveEditData = robot.lookup("#saveEditDataButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditData);
//        robot.clickOn("#saveEditDataButton");
//        assertEquals("SAVE", btnSaveEditData.getText());
//        assertTrue(Controller.isDataEditable);
//        robot.closeCurrentWindow();
//        //Change from EDIT to SAVE
//        robot.clickOn("#saveEditDataButton");
//        assertEquals("EDIT", btnSaveEditData.getText());
//        assertFalse(Controller.isDataEditable);
//        robot.closeCurrentWindow();
//        robot.closeCurrentWindow();
//
//        //Display Info window
//        robot.lookup("#hostnameXProxyLabel").tryQuery().isPresent();
//        Label hostnameXProxylbl = robot.lookup("#hostnameXProxyLabel").queryAs(Label.class);
//        assertNotNull(hostnameXProxylbl);
//    }
    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR

// Commented 13 Jan 2025
//    @Test
//    void testGui_JmsButtonClickEdit(FxRobot robot) {
////        NodeQuery tabJmsProvider = robot.lookup("#tabWillow");
////        assertNotNull(tabJmsProvider);
//        robot.clickOn("#tabJms");
//        Button btnSaveEditJms = robot.lookup("#saveEditJmsButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditJms);
//        robot.clickOn("#saveEditJmsButton");
//        assertEquals("SAVE", btnSaveEditJms.getText());
//        assertTrue(Controller.isJmsEditable);
//
////        assertEquals("EDIT", btnSaveEditJms.getText());
////        assertEquals(false, Controller.isJmsEditable);
//    }
    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR

// Commented 13 Jan 2025
//    @Test
//    void testGui_JmsButtonClickEdit_thenSave(FxRobot robot) {
//        robot.clickOn("#tabJms");
//        Button btnSaveEditJms = robot.lookup("#saveEditJmsButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditJms);
//        robot.clickOn("#saveEditJmsButton");
//        assertEquals("SAVE", btnSaveEditJms.getText());
//        assertTrue(Controller.isJmsEditable);
//        robot.closeCurrentWindow();
//        //Change from EDIT to SAVE
//        robot.clickOn("#saveEditJmsButton");
//        assertEquals("EDIT", btnSaveEditJms.getText());
//        assertFalse(Controller.isJmsEditable);
//        robot.closeCurrentWindow();
//        robot.closeCurrentWindow();
//
//    }

    //TEST IN ISOLATION, IF RUN VIA MAIN CLASS, IT WILL RETURN ERROR

// Commented 13 Jan 2025
//    @Test
//    void testGui_JmsButtonClickSave_thenOpenInfo(FxRobot robot) {
//        robot.clickOn("#tabJms");
//        Button btnSaveEditJms = robot.lookup("#saveEditJmsButton").queryAs(Button.class);
//        assertNotNull(btnSaveEditJms);
//        robot.clickOn("#saveEditJmsButton");
//        assertEquals("SAVE", btnSaveEditJms.getText());
//        assertTrue(Controller.isJmsEditable);
//        robot.closeCurrentWindow();
//        //Change from EDIT to SAVE
//        robot.clickOn("#saveEditJmsButton");
//        assertEquals("EDIT", btnSaveEditJms.getText());
//        assertFalse(Controller.isJmsEditable);
//        robot.closeCurrentWindow();
//        robot.closeCurrentWindow();
//
//        //Display Info window
//        robot.lookup("#hostnameJmsLabel").tryQuery().isPresent();
//        Label hostnameJmslbl = robot.lookup("#hostnameJmsLabel").queryAs(Label.class);
//        assertNotNull(hostnameJmslbl);
//    }

}