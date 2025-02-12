package com.example.finaljavafxjunit;

import javafx.application.Application;

public class DefaultApplicationLauncher implements ApplicationLauncher {
//Implements the ApplicationLauncher interface.
//It has a constructor that takes an ApplicationLauncher as a dependency.
//The launch method calls Application.launch(FXApplication.class, args) to start the JavaFX application.
    private ApplicationLauncher applicationLauncher;
    public DefaultApplicationLauncher(ApplicationLauncher applicationLauncher) {
        this.applicationLauncher = applicationLauncher;
    }
    @Override
    public void launch(String[] args) {
        Application.launch(FXApplication.class, args);
    }
}

