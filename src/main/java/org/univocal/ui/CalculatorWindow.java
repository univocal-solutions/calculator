package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.univocal.controller.CalculatorController;
import org.univocal.utils.Operation;

/**
 * Ultra-clean calculator window with minimalist design.
 * Features a pristine white interface with subtle shadows and perfect spacing.
 *
 * @author Calculator Team
 * @version 2.0
 */
public class CalculatorWindow implements
        CalculatorButtonPanel.ButtonClickHandler,
        CalculatorKeyboardHandler.KeyboardInputHandler {

    // Controller reference
    private final CalculatorController controller;

    // UI Components
    private final CalculatorDisplay display;
    private final CalculatorButtonPanel buttonPanel;
    private final CalculatorKeyboardHandler keyboardHandler;

    // Window components
    private Stage primaryStage;
    private Scene scene;
    private BorderPane mainLayout;

    // Clean window configuration
    private static final int WINDOW_WIDTH = 480;  // Increased from 400 to accommodate 5 columns
    private static final int WINDOW_HEIGHT = 600; // Decreased from 680 since we have 5 rows instead of 6
    private static final String WINDOW_TITLE = "Calculator";
    private static final String BACKGROUND_COLOR = "#f8f9fa";      // Ultra-light gray
    private static final int MAIN_PADDING = 25;

    public CalculatorWindow(CalculatorController controller) {
        if (controller == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }

        this.controller = controller;
        this.display = new CalculatorDisplay();
        this.buttonPanel = new CalculatorButtonPanel(this);
        this.keyboardHandler = new CalculatorKeyboardHandler(this);
    }

    /**
     * Shows the ultra-clean calculator window.
     */
    public void show(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.primaryStage = stage;

        createMainLayout();
        createScene();
        setupKeyboardHandling();
        configureStage();

        primaryStage.show();
        mainLayout.requestFocus();
        updateDisplayFromController();
    }

    public void close() {
        if (keyboardHandler != null) {
            keyboardHandler.detachFromScene();
        }
        if (primaryStage != null) {
            primaryStage.close();
        }
    }

    /**
     * Creates ultra-clean main layout.
     */
    private void createMainLayout() {
        mainLayout = new BorderPane();
        mainLayout.setStyle(String.format("-fx-background-color: %s;", BACKGROUND_COLOR));
        mainLayout.setPadding(new Insets(MAIN_PADDING));

        // Add components with clean spacing
        mainLayout.setTop(display.getDisplayContainer());
        mainLayout.setCenter(buttonPanel.getButtonPanel());
    }

    private void createScene() {
        scene = new Scene(mainLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void setupKeyboardHandling() {
        keyboardHandler.attachToScene(scene);
    }

    /**
     * Configure clean, modern window appearance.
     */
    private void configureStage() {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        // Clean window constraints
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);

        primaryStage.setOnCloseRequest(e -> close());
    }

    private void updateDisplayFromController() {
        String displayText = controller.getCurrentDisplay();

        if (controller.hasError()) {
            display.showError(displayText);
        } else {
            display.updateText(displayText);
        }
    }

    // ButtonClickHandler implementation

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

    // KeyboardInputHandler implementation

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

    /**
     * Updates display with clean error handling.
     */
    private void updateDisplayAfterOperation(String result) {
        if (controller.hasError()) {
            display.showError(result);
        } else {
            display.updateText(result);
        }
    }

    // Getters for testing and debugging

    public CalculatorDisplay getDisplay() {
        return display;
    }

    public CalculatorButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public CalculatorKeyboardHandler getKeyboardHandler() {
        return keyboardHandler;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public boolean isShowing() {
        return primaryStage != null && primaryStage.isShowing();
    }
}