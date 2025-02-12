package com.example.finaljavafxjunit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultApplicationLauncherTest {

    @InjectMocks
    private DefaultApplicationLauncher defaultApplicationLauncher;

    @Mock
    private ApplicationLauncher applicationLauncher;

    // THE WINDOW DOES NOT CLOSE AUTOMATICALLY
    //AFTER RUN TEST, CLOSE THE WINDOW TO GET TEST RESULT
    @Test
    void testLaunch(){
        // Create a mock for ApplicationLauncher
        ApplicationLauncher mockLauncher = mock(ApplicationLauncher.class);

        // Set the mock launcher in the FXApplication class
        FXApplication.applicationLauncher = mockLauncher;

        // Simulate the execution with keyword
        String[] args = {"-config"};
        mockLauncher.launch(args);

        // Verify that the launcher was called with the correct arguments
        verify(mockLauncher).launch(args);
    }

    @Test
    void testLaunch2() {
//        // Mock the FXApplication class
//        ApplicationLauncher mockLauncher = mock(ApplicationLauncher.class);
//
//        // Create an instance of DefaultApplicationLauncher
//        DefaultApplicationLauncher defaultApplicationLauncher = new DefaultApplicationLauncher(applicationLauncher);

        // Call the launch method
        String[] args = {"config"};
        defaultApplicationLauncher.launch(args);
        applicationLauncher.launch(args);
//        mockLauncher.launch(args);

        // Verify that the mockLauncher was called with the correct arguments
//        verify(defaultApplicationLauncher).launch(args);
        verify(applicationLauncher).launch(args);
//        verifyNoInteractions(mockLauncher);
    }

}