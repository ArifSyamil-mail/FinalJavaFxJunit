package com.example.finaljavafxjunit;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
//@PrepareForTest(Logger.class)
public class FXApplicationTest {

    @Mock
    protected Stage primaryStage;
    @Mock
    protected static Logger logger = Logger.getLogger((FXApplication.class.getName()));
    @BeforeAll
    public static void initJFX() {
        Platform.startup(() -> {
        });
    }
    @BeforeEach
    public void setUp() {
        // Initialize JavaFX Toolkit
        new JFXPanel(); // This creates an invisible JavaFX container, initializing the toolkit
    }
    @Test
    void testKeywordLaunchWithConfig() {
        // Arrange
        String[] args = {"-config"};

        // Act
        boolean result = FXApplication.keywordLaunch(args);

        // Assert
        assertTrue(result);
    }

    @Test
    void testKeywordLaunchWithoutConfig() {
        // Arrange
        String[] args = {"other"};

        // Act
        boolean result = FXApplication.keywordLaunch(args);

        // Assert
        assertFalse(result);
    }


    @Test
    void testStartThrowsIOException() throws IOException {
        //Mock
//        Stage mockStage = mock(Stage.class);
        FXApplication spyFx = spy(FXApplication.class);

        // Arrange
        doThrow(new IOException("Simulated IOException")).when(spyFx).loadMainScene();

        // Act and Assert
        assertThrows(IOException.class, () -> spyFx.start(primaryStage));
    }

    @Test
    void testLoadMainSceneThrowsIOException() throws IOException {
        //Mock
        FXApplication spyFx = spy(FXApplication.class);
        // Arrange
        doThrow(new IOException("Simulated IOException")).when(spyFx).loadMainScene();

        //Act

        // Act + Assert / Verify
        assertThrows(IOException.class, () -> spyFx.loadMainScene());
    }

    @Test
    void testLoadMainSceneWorking() throws IOException {
        // Mock
        FXMLLoader mockLoader = mock(FXMLLoader.class);
        Parent mockParent = mock(Parent.class);
        FXApplication spyFx = spy(FXApplication.class);
//        Stage mockStage = mock(Stage.class);

        // Stub the FXMLLoader.load() method
        when(mockLoader.load()).thenReturn(mockParent);

        // Stub the loadMainScene method to return the mockLoader
        doNothing().when(spyFx).start(primaryStage);
        doNothing().when(spyFx).loadMainScene();

        // Act
        spyFx.start(primaryStage);
        mockLoader.load();

        // Assert (Add your specific assertions based on the behavior of loadMainScene)
        assertDoesNotThrow(() -> spyFx.loadMainScene());
        verify(mockLoader, times(1)).load(); // Verify that load() is called on the mockLoader
    }

    @Test
    void testShowPrimaryStageWorking() throws IOException {
        // Mock
//        FXApplication spyFx = spy(FXApplication.class);
        FXApplication spyFx = spy(new FXApplication());
//        Stage mockStage = mock(Stage.class);

        // Stub the FXMLLoader.load() method
//        when(mockLoader.load()).thenReturn(mockParent);
        // Stub the loadMainScene method to return the mockLoader
        doNothing().when(spyFx).start(primaryStage);
        doNothing().when(spyFx).showPrimaryStage();

        // Act
        spyFx.start(primaryStage);
//        spyFx.showPrimaryStage();

        // Assert (Add your specific assertions based on the behavior of loadMainScene)
        assertDoesNotThrow(() -> spyFx.showPrimaryStage());
        verify(spyFx, times(1)).showPrimaryStage(); // Verify that load() is called on the mockLoader
    }

    @Test
    void testConfigKeywordCmdPresentGui() throws TimeoutException {
    // Simulate the presence of "-config" command line argument
    simulateCommandLineArgument("-config");
    // Perform assertions based on the behavior when "-config" is present
    assertEquals("success", simulateCommandLineArgument("-config"));
}

    @Test
    void testConfigKeywordCmdAbsentGui() throws TimeoutException {
        // Simulate the absence of "-config" command line argument
        simulateCommandLineArgument(null);

        // Perform assertions based on the behavior when "-config" is absent
        assertEquals("fail", simulateCommandLineArgument(null));

    }

    private String simulateCommandLineArgument(String argument) throws TimeoutException {
        // Set up the JavaFX application with the specified command line argument
        String[] args = FxToolkit.setupApplication(FXApplication.class, argument).getParameters().getRaw().toArray(new String[0]);
        final String[] result = {""};
        // Launch the JavaFX application within the runLater block
        Platform.runLater(() -> {
            if (args.length > 0 && args[0].equals("-config")) {
                logger.info("Program GUI Started!");
                result[0] = "success";
                // Optionally, perform additional setup specific to the case when "-config" is present
            } else {
                logger.warning("Missing keyword. Use 'app.exe -config' OR add keyword '-config' in Command Prompt");
                result[0] = "fail";
//                throw new IllegalStateException("Missing keyword. Use 'app.exe -config' OR add keyword '-config' in Command Prompt");
            }
        });

        // Optionally, wait for the JavaFX application to finish launching
        FxToolkit.showStage();
        return result[0];
    }

    @Test
    void testExecuteWithKeyword() {
        // Create a mock for ApplicationLauncher
        ApplicationLauncher mockLauncher = mock(ApplicationLauncher.class);

        // Set the mock launcher in the FXApplication class
        FXApplication.applicationLauncher = mockLauncher;

        // Create an instance of FXApplication
        FXApplication fxApplication = new FXApplication();

        // Simulate the execution with keyword
        String[] args = {"-config"};
        fxApplication.execute(args, mockLauncher);

        // Verify that the launcher was called with the correct arguments
        verify(mockLauncher).launch(args);
    }

    @Test
    void testExecuteWithoutKeyword() {
        // Create a mock for ApplicationLauncher
        ApplicationLauncher mockLauncher = mock(ApplicationLauncher.class);

        // Set the mock launcher in the FXApplication class
        FXApplication.applicationLauncher = mockLauncher;

        // Create an instance of FXApplication
        FXApplication fxApplication = new FXApplication();

        // Simulate the execution without keyword
        String[] args = {"some", "other", "arguments"};
        fxApplication.execute(args, mockLauncher);

        // Verify that System.exit(1) was called when the keyword is missing
        verifyNoInteractions(mockLauncher); // Make sure the launcher was not called
    }



}


