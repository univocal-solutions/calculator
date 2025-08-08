package org.univocal.application;

import javafx.application.Application;
import javafx.stage.Stage;
import org.univocal.controller.CalculatorController;
import org.univocal.ui.CalculatorWindow;

/**
 * Main application class responsible for starting the JavaFX calculator application.
 * This class serves as the entry point and coordinates the creation of the controller
 * and main window components.
 *
 * Features:
 * - Clean separation of concerns
 * - Proper component initialization
 * - Error handling for startup
 *
 * @author Univocal Team
 * @version 2.0
 */
public class CalculatorApp extends Application {

    private CalculatorController controller;
    private CalculatorWindow calculatorWindow;

    /**
     * Starts the calculator application.
     * Creates the controller and window, then displays the calculator.
     *
     * @param primaryStage the primary stage provided by JavaFX
     * @throws Exception if there's an error during application startup
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Create the controller for business logic
            controller = new CalculatorController();

            // Create the main calculator window
            calculatorWindow = new CalculatorWindow(controller);

            // Show the calculator window
            calculatorWindow.show(primaryStage);

        } catch (Exception e) {
            System.err.println("Error starting calculator application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Stops the calculator application.
     * Performs cleanup of resources when the application is closing.
     *
     * @throws Exception if there's an error during application shutdown
     */
    @Override
    public void stop() throws Exception {
        try {
            // Clean up the calculator window if it exists
            if (calculatorWindow != null) {
                calculatorWindow.close();
            }

        } catch (Exception e) {
            System.err.println("Error stopping calculator application: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Always call the parent stop method
            super.stop();
        }
    }

    /**
     * Main method - entry point for the application.
     * Launches the JavaFX application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}