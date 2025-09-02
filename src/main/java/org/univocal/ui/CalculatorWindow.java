package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.univocal.controller.CalculatorController;
import org.univocal.models.CalculatorSettings;
import org.univocal.utils.Operation;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Main calculator window with clean minimalist design and context menu settings.
 * Features theme switching via right-click context menu to maintain clean aesthetics.
 * No visual settings elements - pure minimalism with hidden functionality.
 *
 * @author Calculator Team
 * @version 2.0
 */
public class CalculatorWindow implements
        CalculatorButtonPanel.ButtonClickHandler,
        CalculatorKeyboardHandler.KeyboardInputHandler,
        SettingsDialog.ThemeChangeListener {

    // Controller and settings
    private final CalculatorController controller;
    private final CalculatorSettings settings;

    // UI Components
    private final CalculatorDisplay display;
    private final CalculatorButtonPanel buttonPanel;
    private final CalculatorKeyboardHandler keyboardHandler;

    // Window components
    private Stage primaryStage;
    private Scene scene;
    private BorderPane mainLayout;

    // Context menu and dialogs
    private ContextMenu contextMenu;
    private SettingsDialog settingsDialog;
    private AboutDialog aboutDialog;

    // Window configuration constants
    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 600; // Reduced since no header needed
    private static final String WINDOW_TITLE = "Calculator";
    private static final int MAIN_PADDING = 25;

    /**
     * Creates a new calculator window.
     *
     * @param controller the calculator controller for business logic
     * @throws IllegalArgumentException if controller is null
     */
    public CalculatorWindow(CalculatorController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.settings = new CalculatorSettings();
        this.display = new CalculatorDisplay();
        this.buttonPanel = new CalculatorButtonPanel(this);
        this.keyboardHandler = new CalculatorKeyboardHandler(this);
    }

    /**
     * Shows the calculator window with clean minimalist design.
     *
     * @param stage the primary stage to display the calculator in
     * @throws IllegalArgumentException if stage is null
     */
    public void show(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.primaryStage = stage;

        createMainLayout();
        createScene();
        setupContextMenu();
        setupKeyboardHandling();
        configureStage();
        applyCurrentTheme();

        primaryStage.show();
        mainLayout.requestFocus();
        updateDisplayFromController();
    }

    /**
     * Closes the calculator window and cleans up resources.
     */
    public void close() {
        if (keyboardHandler != null) {
            keyboardHandler.detachFromScene();
        }

        if (settingsDialog != null) {
            settingsDialog = null;
        }

        if (aboutDialog != null) {
            aboutDialog = null;
        }

        if (primaryStage != null) {
            primaryStage.close();
        }
    }

    /**
     * Creates the clean main layout without any header elements.
     * Pure minimalism - just display and buttons.
     */
    private void createMainLayout() {
        mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(MAIN_PADDING));

        // Clean layout: display at top, buttons at bottom
        mainLayout.setTop(display.getDisplayContainer());
        mainLayout.setBottom(buttonPanel.getButtonPanel());
    }

    /**
     * Creates the JavaFX scene.
     */
    private void createScene() {
        scene = new Scene(mainLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    /**
     * Sets up the right-click context menu for the entire window.
     * Provides access to settings and about dialog without visual clutter.
     */
    private void setupContextMenu() {
        contextMenu = new ContextMenu();

        // Settings menu item
        MenuItem settingsItem = new MenuItem("Settings...");
        settingsItem.setOnAction(e -> showSettingsDialog());

        // Separator for visual grouping
        SeparatorMenuItem separator = new SeparatorMenuItem();

        // About menu item
        MenuItem aboutItem = new MenuItem("About Calculator...");
        aboutItem.setOnAction(e -> showAboutDialog());

        // Keyboard shortcuts menu item
        MenuItem keyboardItem = new MenuItem("Keyboard Shortcuts");
        keyboardItem.setOnAction(e -> showKeyboardShortcuts());

        // Add all items to context menu
        contextMenu.getItems().addAll(settingsItem, separator, aboutItem, keyboardItem);

        // Apply context menu to the entire main layout
        mainLayout.setOnContextMenuRequested(e -> {
            contextMenu.show(mainLayout, e.getScreenX(), e.getScreenY());
        });

        // Hide context menu only on left-button press (avoid closing on right-click release on macOS)
        mainLayout.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (e.getButton() == MouseButton.PRIMARY && contextMenu.isShowing()) {
                contextMenu.hide();
            }
        });
    }

    /**
     * Sets up keyboard event handling for the scene.
     */
    private void setupKeyboardHandling() {
        keyboardHandler.attachToScene(scene);
    }

    /**
     * Configures the primary stage properties.
     */
    private void configureStage() {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        // Set window size constraints
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);

        // Handle window close event
        primaryStage.setOnCloseRequest(e -> close());
    }

    /**
     * Applies the current theme to all components.
     */
    private void applyCurrentTheme() {
        // Apply theme to main window background
        String backgroundColor = settings.isDarkTheme() ? "#2c3e50" : "#f8f9fa";
        mainLayout.setStyle(String.format("-fx-background-color: %s;", backgroundColor));

        // Apply theme to display
        display.applyTheme(settings);

        // Apply theme to button panel
        buttonPanel.applyTheme(settings);

        // Update context menu theme
        updateContextMenuTheme();
    }

    /**
     * Updates context menu styling based on current theme.
     */
    private void updateContextMenuTheme() {
        if (contextMenu != null) {
            String bgColor = settings.isDarkTheme() ? "#34495e" : "#ffffff";
            String textColor = settings.isDarkTheme() ? "#ecf0f1" : "#2c3e50";

            String menuStyle = String.format(
                    "-fx-background-color: %s; -fx-text-fill: %s;",
                    bgColor, textColor
            );

            contextMenu.setStyle(menuStyle);
        }
    }

    /**
     * Shows the settings dialog.
     */
    private void showSettingsDialog() {
        if (settingsDialog == null) {
            settingsDialog = new SettingsDialog(primaryStage, settings, this);
        }
        settingsDialog.show();
    }

    /**
     * Shows the about dialog.
     */
    private void showAboutDialog() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog(primaryStage, settings);
        }
        aboutDialog.show();
    }

    /**
     * Shows keyboard shortcuts information.
     */
    private void showKeyboardShortcuts() {
        String shortcuts = keyboardHandler.getKeyboardShortcuts();

        // Create a simple alert-style dialog for shortcuts
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION
        );
        alert.setTitle("Keyboard Shortcuts");
        alert.setHeaderText("Calculator Keyboard Shortcuts");
        alert.setContentText(shortcuts);
        alert.initOwner(primaryStage);
        alert.showAndWait();
    }

    /**
     * Updates the display with the current controller state.
     */
    private void updateDisplayFromController() {
        String displayText = controller.getCurrentDisplay();

        if (controller.hasError()) {
            display.showError(displayText);
        } else {
            display.updateText(displayText);
        }
    }

    /**
     * Updates display after any operation and handles error states.
     *
     * @param result the result string from the controller
     */
    private void updateDisplayAfterOperation(String result) {
        if (controller.hasError()) {
            display.showError(result);
        } else {
            display.updateText(result);
        }
    }

    // Implementation of ThemeChangeListener interface

    @Override
    public void onThemeChanged(CalculatorSettings.Theme newTheme) {
        settings.setTheme(newTheme);
        applyCurrentTheme();
    }

    // Implementation of ButtonClickHandler interface

    @Override
    public void onNumberClick(String number) {
        String result = controller.handleNumberInput(number);
        updateDisplayAfterOperation(result);
    }

    @Override
    public void onDecimalClick() {
        String result = controller.handleDecimalInput();
        updateDisplayAfterOperation(result);
    }

    @Override
    public void onOperatorClick(Operation operation) {
        String result = controller.handleOperation(operation);
        updateDisplayAfterOperation(result);
    }

    @Override
    public void onClearClick() {
        String result = controller.handleOperation(Operation.CLEAR);
        updateDisplayAfterOperation(result);
    }

    @Override
    public void onBackspaceClick() {
        String result = controller.handleOperation(Operation.BACKSPACE);
        updateDisplayAfterOperation(result);
    }

    @Override
    public void onSquareClick() {
        try {
            String currentDisplay = controller.getCurrentDisplay();
            double current = Double.parseDouble(currentDisplay);

            // Perform square operation using power function
            controller.handleOperation(Operation.CLEAR);
            controller.handleNumberInput(String.valueOf(current));
            controller.handleOperation(Operation.POWER);
            controller.handleNumberInput("2");
            String result = controller.handleOperation(Operation.EQUALS);

            updateDisplayAfterOperation(result);
        } catch (NumberFormatException e) {
            display.showError("Error");
        }
    }

    // Implementation of KeyboardInputHandler interface

    @Override
    public void onNumberInput(String number) {
        onNumberClick(number);
    }

    @Override
    public void onDecimalInput() {
        onDecimalClick();
    }

    @Override
    public void onOperatorInput(Operation operation) {
        onOperatorClick(operation);
    }

    @Override
    public void onClearInput() {
        onClearClick();
    }

    @Override
    public void onBackspaceInput() {
        onBackspaceClick();
    }

    @Override
    public void onClearEntryInput() {
        String result = controller.handleOperation(Operation.CLEAR_ENTRY);
        updateDisplayAfterOperation(result);
    }

    // Getter methods for testing and external access

    /**
     * Gets the calculator display component.
     *
     * @return the calculator display
     */
    public CalculatorDisplay getDisplay() {
        return display;
    }

    /**
     * Gets the button panel component.
     *
     * @return the button panel
     */
    public CalculatorButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    /**
     * Gets the keyboard handler component.
     *
     * @return the keyboard handler
     */
    public CalculatorKeyboardHandler getKeyboardHandler() {
        return keyboardHandler;
    }

    /**
     * Gets the calculator settings.
     *
     * @return the calculator settings
     */
    public CalculatorSettings getSettings() {
        return settings;
    }

    /**
     * Gets the main JavaFX scene.
     *
     * @return the scene, or null if not yet created
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Gets the primary stage.
     *
     * @return the stage, or null if not yet set
     */
    public Stage getStage() {
        return primaryStage;
    }

    /**
     * Checks if the window is currently showing.
     *
     * @return true if window is showing, false otherwise
     */
    public boolean isShowing() {
        return primaryStage != null && primaryStage.isShowing();
    }
}