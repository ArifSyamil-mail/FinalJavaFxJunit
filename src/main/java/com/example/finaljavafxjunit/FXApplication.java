package com.example.finaljavafxjunit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class FXApplication extends Application {
//Extends javafx.application.Application and represents the JavaFX application.
//Defines the start method, which is the main entry point for JavaFX applications. It initializes the primary stage, loads the main FXML file, and sets up the scene.
//The loadMainScene method loads the main FXML file and sets up the scene.
//The showPrimaryStage method sets the title and shows the primary stage.
//The execute method is a static method that is called from Launcher.main. It checks whether a specific keyword (-config) is present in the command-line arguments. If so, it logs a message and launches the application using the provided ApplicationLauncher.
    protected static ApplicationLauncher applicationLauncher;
    protected Stage primaryStage;
    protected static Logger logger = Logger.getLogger((FXApplication.class.getName()));

    @Override
    public void start(Stage stage) throws IOException {
        // Store the primary stage for later use
        primaryStage = stage;

        // Load the main application FXML file and set the controller, Controller.java class
        loadMainScene();

        // Show the main application window
        showPrimaryStage();
    }

    protected void loadMainScene() throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent mainRoot = mainLoader.load();

        Scene mainScene = new Scene(mainRoot);
        mainScene.getStylesheets().add("styles.css");
        primaryStage.setScene(mainScene);
    }
    protected void showPrimaryStage() {
        primaryStage.setTitle("JMS Safe Configuration");
        primaryStage.show();
    }
    // Launch application
    public static void launchApplication(String[] args) {
//        applicationLauncher.launch(args);
        Application.launch(args);
    }

    // Main method for standalone execution
    public static void execute(String[] args, ApplicationLauncher launcher) {
//        if (keywordLaunch(args)) {
//            logger.info("Program GUI Started!");
//            launchApplication(args);
//
//        } else {
//            logger.warning("Missing keyword. Use 'app.exe -config' OR add keyword '-config' in Command Prompt");
//            System.exit(1);
//        }

// NO KEYWORD NEEDED
        logger.info("Program GUI Started!");
                    launcher.launch(args);
    }

    protected static boolean keywordLaunch(String[] args) {
        return args.length > 0 && args[0].equals("-config");
    }
}
