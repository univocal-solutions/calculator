package org.univocal.application;

/**
 * JavaFX Launcher to work around module system issues in fat JARs.
 * This class doesn't extend Application, avoiding JavaFX module loading issues.
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class JavaFXLauncher {
    public static void main(String[] args) {
        // Launch the actual JavaFX application
        CalculatorApp.main(args);
    }
}