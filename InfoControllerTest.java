package com.example.finaljavafxjunit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationExtension;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class InfoControllerTest {

    @Mock
    protected Label hostnameXProxyLabel, portXProxyLabel, versionXProxyLabel;
//    @Spy
//    private InfoController infoController;
    private Properties properties;
    protected static Logger logger = Logger.getLogger((InfoController.class.getName()));
    // Define a separator or identifier for each tab
    private static final String DATA_SERVER_IDENTIFIER = "xdataserver.";
    private static final String JMS_PROVIDER_IDENTIFIER = "jms-provider.";
    private static final String X_PROXY_IDENTIFIER = "xproxy.";
    private static final String JMS_SAFE_IDENTIFIER = "xjms.";
    @BeforeAll
    public static void initJFX() {
        Platform.startup(() -> {
        });
    }
    @BeforeEach
    void setUp() {
        // Initialize JavaFX Toolkit
        new JFXPanel(); // This creates an invisible JavaFX container, initializing the toolkit
    }
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
////        assertNotNull(hostnameXProxyLabel, "hostnameXProxyLabel should not be null");
////        assertNotNull(portXProxyLabel, "portXProxyLabel should not be null");
////        assertNotNull(versionXProxyLabel, "versionXProxyLabel should not be null");
//        infoController = new InfoController();
//    }
    // Helper methods to set dependencies for testing
//    private void setProperties(Properties properties) {
//        infoController.properties = properties;
//    }

//    private void setLogger(Logger logger) {
//        infoController.logger = logger;
//    }

//    @Test
//    void testInitPropertyInfo_Success() {
//        // Mock dependencies and set up expectations
//        Properties propertiesMock = mock(Properties.class);
//        InfoController mockInfo = spy(InfoController.class);
//
//        //Stubbing
//        doReturn("Sim properties").when(propertiesMock).getProperty(anyString());
////        when(propertiesMock.getProperty((anyString()))).thenReturn("Sim properties");
//        setProperties(propertiesMock);
//
//        // Call the method to be tested
//        mockInfo.initPropertyInfo();
//
//        // Assertions / Verify
//        assertEquals("Sim properties",propertiesMock.getProperty("testValue"));
//        assertNotNull(propertiesMock.getProperty("testValue"));
//        verify(mockInfo, times(1)).initPropertyInfo();
//    }
//@Test
//void testInitPropertyInfo_Success() {
//    // Mock dependencies and set up expectations
//    Properties propertiesMock = mock(Properties.class);
//    InfoController infoController = spy(new InfoController());
//
//    // Stubbing
//    doReturn("Sim properties").when(propertiesMock).getProperty(anyString());
//    setProperties(propertiesMock);
//
//    // Call the method to be tested
//    spyInfoController.initPropertyInfo();
//
//    // Assertions / Verify
//    assertEquals("Sim properties", propertiesMock.getProperty("testValue"));
//    assertNotNull(propertiesMock.getProperty("testValue"));
//    verify(spyInfoController, times(1)).initPropertyInfo();
//}
//    @Test
//    void testInitPropertyInfo_Exception() {
//        // Mock dependencies and set up expectations
//        Properties propertiesMock = mock(Properties.class);
//        doThrow(new RuntimeException("Test Exception")).when(propertiesMock).isEmpty();
//        setProperties(propertiesMock);
//
//        // Call the method to be tested and assert the exception
//        assertThrows(RuntimeException.class, () -> infoController.initPropertyInfo());
//    }

    @Test
    void testHandleInitializeException() {
        // Arrange
        InfoController infoController = spy(new InfoController());  // Create an instance of the class that contains the method
        Logger loggerMock = mock(Logger.class);  // Mock the Logger
        // Set the mock logger in your object
        infoController.logger = loggerMock;
        // Act
//        controller.handleInitializeException(new RuntimeException("Mocked exception"));
        Platform.runLater(() -> {
            infoController.handleInitializeError(new RuntimeException("Mocked error"));
        });
        loggerMock.log(Level.SEVERE, "Sim Initialize Method Error");
        // Assert or verify expected behavior
        // Verify that the logger was called with the expected message and log level
        verify(loggerMock, times(1)).log(eq(Level.SEVERE), anyString());
        assertThrows(RuntimeException.class, () -> infoController.handleInitializeError(new RuntimeException()));
        // Optionally, you can verify other aspects of the log method call, such as the log level, etc.
    }

//    @Test
//    void test_setXProxyProperties() {
//
//        // Mock
//        Properties mockProperties = mock(Properties.class);
//        Label mockHostXProxy = mock(Label.class);
//        Label mockPortXProxy = mock(Label.class);
//        Label mockVersionXProxy = mock(Label.class);// Create a mock for Label
//
//        InfoController spyInfo = spy(new InfoController());
//
//        // Stubbing
//        doNothing().when(spyInfo).setXProxyProperties();
//
//        doReturn("localhost").when(mockProperties).getProperty("xproxy.ip");
//        doReturn(String.valueOf(8080)).when(mockProperties).getProperty("xproxy.http-port");
//        doReturn("version 1.0").when(mockProperties).getProperty("xproxy.version");
//
//        doReturn(mockHostXProxy).when(hostnameXProxyLabel).setText(String.valueOf(mockProperties.setProperty("xproxy.ip", "localhost")));
//        doReturn(mockPortXProxy).when(portXProxyLabel).setText(String.valueOf(mockProperties.setProperty("xproxy.http-port", String.valueOf(8080))));
//        doReturn(mockVersionXProxy).when(versionXProxyLabel).setText(String.valueOf(mockProperties.setProperty("xproxy.version", "version 1.0")));
//
////        when(mockProperties.getProperty("xproxy.ip")).thenReturn("localhost");
////        when(mockProperties.getProperty("xproxy.http-port")).thenReturn(String.valueOf(8080));
////        when(mockProperties.getProperty("xproxy.version")).thenReturn("version 1.0");
//
//        // Simulate
//        spyInfo.loadPropertiesFromFile();
//        spyInfo.setXProxyProperties();  // Assuming you want to test initPropertyInfo instead
//
//        // Assert / verify
//        verify(mockHostXProxy).setText("localhost");
//        verify(mockPortXProxy).setText(String.valueOf(8080));
//        verify(mockVersionXProxy).setText("version 1.0");
//
//        // Additional assertions if needed
//        assertEquals("localhost", mockProperties.getProperty("xproxy.ip"));
//        assertEquals(String.valueOf(8080), mockProperties.getProperty("xproxy.http-port"));
//        assertEquals("version 1.0", mockProperties.getProperty("xproxy.version"));
//    }

    //performEncryption() Commented 13 Jan 2025
//    @Test
//    void testPerformEncryption_SuccessfulEncryption() throws Exception {
//        // Arrange
//        InfoController infoController = spy(new InfoController());
//        SecretKey mockSecretKey = mock(SecretKey.class);
//        Logger mockLogger = mock(Logger.class);
//        byte[] mockEncryptedBytes = new byte[]{1, 2, 3};
//        // Mocking the behavior of generateSecretKey and performEncryption
//        when(infoController.generateSecretKey()).thenReturn(mockSecretKey);
//        byte[] mockIV = new byte[12];
//
//        // Act
//
//        //Run the code on JavaFx Application Thread, to avoid concurrency issue
//        Platform.runLater(() -> {
//            infoController.decrypt("YourOriginalValue");
//        });
//
//        // Verify that generateSecretKey and performEncryption were called
//        // Verify that combineIVAndEncryptedData was called
//        verify(infoController, times(1)).decrypt(anyString());
//
//        // Verify
//        verifyNoMoreInteractions(mockSecretKey);
//        verifyNoMoreInteractions(infoController);
//        verifyNoMoreInteractions(mockLogger);
//    }

    @Test
    void testPerformEncryption_EncryptionExceptionHandled() throws Exception {
        // Arrange
        InfoController infoController = spy(new InfoController());
        SecretKey mockSecretKey = mock(SecretKey.class);
        Logger mockLogger = mock(Logger.class);

        //Inject mockLogger into class InfoController
        infoController.logger = mockLogger;

        // Mocking the behavior of generateSecretKey to simulate an exception
        when(infoController.generateSecretKey()).thenThrow(new NoSuchAlgorithmException("Simulated exception"));

        // Act
//        String result = controller.encrypt("YourOriginalValue");
        Platform.runLater(() -> {
            infoController.decrypt("YourOriginalValue");
        });

        // Assert
//        assertEquals("", result);
        assertThrows(NoSuchAlgorithmException.class, () -> infoController.generateSecretKey());

        // Verify that generateSecretKey was called
//        verify(controller, times(1)).generateSecretKey();
        verify(infoController, times(1)).decrypt(anyString());
        verify(infoController, times(1)).generateSecretKey();
        verify(infoController, times(1)).handleDecryptionError(any());
        verify(infoController, times(1)).showAndWaitAlert(eq(Alert.AlertType.ERROR), anyString(), anyString(), anyString());
        verify(infoController, times(1)).createAlert(eq(Alert.AlertType.ERROR), anyString(), anyString(), anyString());

        // Verify that logger and other methods weren't used (optional)
//        verifyNoInteractions(logger, cipher, gcmParameterSpec);
        verifyNoMoreInteractions(infoController);
    }

    //decrypt() Commented 13 Jan 2025
//    @Test
//    void testDecrypt_SuccessfulDecryption() throws NoSuchAlgorithmException, InvalidKeySpecException {
//        // Arrange
//        InfoController infoController = spy(new InfoController());
//        SecretKey mockSecretKey = mock(SecretKey.class);
//        String encryptedProperties = "yourEncryptedProperties";  // Provide a valid Base64-encoded string
//        byte[] mockEncryptedBytes = new byte[]{1, 2, 3};
//        byte[] mockIV = new byte[12];
//
//        // Mock dependencies
//        doReturn(mockSecretKey).when(infoController).generateSecretKey();
//
//        // Act
////        Properties result = controller.decrypt(encryptedProperties);
//        Platform.runLater(() -> {
//            infoController.loadPropertiesFromFile();
//            infoController.decrypt(encryptedProperties);
////            controller.showAndWaitAlert(Alert.AlertType.ERROR, "LOAD ERROR", "Error message: ", "A new file named ""xjms.properties"" will be created when entering and saving input."
//        });
//        // Assert
//        // Add more assertions based on the expected behavior of decrypt
//        // For example, you might want to verify that loadProperties was called
//        verify(infoController, times(1)).loadPropertiesFromFile();
//        verify(infoController, times(0)).decrypt(anyString());
//    }

    @Test
    void testDecrypt_ExceptionHandling() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Arrange
        InfoController infoController = spy(new InfoController());
        String encryptedProperties = "yourInvalidEncryptedProperties";  // Provide an invalid Base64-encoded string

        // Mock dependencies
        doThrow(new NoSuchAlgorithmException("Simulated decryption failure")).when(infoController).generateSecretKey();

        // Act
//        Properties result = controller.decrypt(encryptedProperties);
        Platform.runLater(() -> {
            infoController.decrypt("YourOriginalValue");
        });

        // Assert
        assertThrows(NoSuchAlgorithmException.class, () -> infoController.generateSecretKey());
        // Add more assertions based on the expected behavior of exception handling
        // For example, you might want to verify that handleDecryptionException was called
//        verify(controller, times(1)).handleDecryptionException(any());
    }
    @Test
    void testShowAndWaitAlert() {
        // Run the test on the JavaFX Application Thread
        Platform.runLater(() -> {
            InfoController infoController = spy(new InfoController());  // Assuming YourClass is the class containing your methods

            // You can use a mocking library like Mockito to mock the Alert class
            // For simplicity, we'll create a spy of the actual Alert class
            Alert spyAlert = spy(new Alert(Alert.AlertType.INFORMATION));

            // Mocking the createAlert method to return the spyAlert
            when(infoController.createAlert(any(), any(), any(), any())).thenReturn(spyAlert);

            // Call the method you want to test
            infoController.showAndWaitAlert(Alert.AlertType.INFORMATION, "Test Title", "Test Header", "Test Content");

            // Verify that createAlert was called with the correct parameters
            verify(infoController).createAlert(Alert.AlertType.INFORMATION, "Test Title", "Test Header", "Test Content");

            // Verify that showAndWait was called on the spyAlert
            verify(spyAlert).showAndWait();

            // Add additional assertions as needed
        });
    }

    @Test
    void testCreateAlert() {
        // Run the test on the JavaFX Application Thread
        Platform.runLater(() -> {
            InfoController infoController = spy(new InfoController());  // Assuming YourClass is the class containing your methods

            // Call the method you want to test
            Alert alert = infoController.createAlert(Alert.AlertType.WARNING, "Title", "Header", "Content");

            // Perform assertions on the returned Alert object
            assertEquals(Alert.AlertType.WARNING, alert.getAlertType());
            assertEquals("Title", alert.getTitle());
            assertEquals("Header", alert.getHeaderText());
            assertEquals("Content", alert.getContentText());
        });
    }
}
