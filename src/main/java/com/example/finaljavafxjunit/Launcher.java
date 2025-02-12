package com.example.finaljavafxjunit;

public class Launcher {
    //The main method is the entry point of the application.
    //It instantiates an ApplicationLauncher (dependency injection) named launcher.
    //It then calls the execute method of the FXApplication class, passing the command-line arguments (args) and the launcher.
    private static ApplicationLauncher applicationLauncher;
    public static void main(String[] args) {
        // Instantiate your ApplicationLauncher (dependency injection)
        ApplicationLauncher launcher = new DefaultApplicationLauncher(applicationLauncher);

        // Execute the FXApplication
        FXApplication.execute(args, launcher);
    }
// Overall Flow:
//Launcher.main creates an instance of DefaultApplicationLauncher.
//DefaultApplicationLauncher launches the JavaFX application (FXApplication) when its launch method is called.
//FXApplication initializes the application, loads the main scene, and shows the primary stage.
//The keyword check in FXApplication.execute ensures that the application is launched only when the specified keyword (-config) is present in the command-line arguments.
}
