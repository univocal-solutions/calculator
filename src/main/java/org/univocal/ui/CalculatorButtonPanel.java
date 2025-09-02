package org.univocal.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.univocal.utils.Operation;

/**
 * Ultra-clean calculator button panel with minimalist design.
 * Features clean white buttons with subtle colors and perfect spacing.
 *
 * @author Calculator Team
 * @version 2.0
 */
public class CalculatorButtonPanel {

    private final GridPane buttonGrid;
    private final ButtonClickHandler clickHandler;

    // Clean layout constants
    private static final int BUTTON_SIZE = 70;
    private static final int GRID_GAP = 12;
    private static final int GRID_COLUMNS = 5; // Changed from 4 to 5 for functions column
    private static final int GRID_ROWS = 5;    // Changed from 6 to 5 rows

    // Clean minimalist color scheme
    private static final String NUMBER_BG = "#ffffff";              // Pure white
    private static final String NUMBER_TEXT = "#2c3e50";            // Dark blue-gray
    private static final String OPERATOR_BG = "#3498db";            // Clean blue
    private static final String OPERATOR_TEXT = "#ffffff";          // White
    private static final String FUNCTION_BG = "#ecf0f1";            // Very light gray
    private static final String FUNCTION_TEXT = "#34495e";          // Darker gray
    private static final String SPECIAL_BG = "#e74c3c";             // Clean red
    private static final String SPECIAL_TEXT = "#ffffff";           // White

    // Clean typography
    private static final String FONT_FAMILY = "SF Pro Display, Segoe UI, system-ui";
    private static final String BUTTON_FONT_SIZE = "16px";
    private static final String SMALL_BUTTON_FONT_SIZE = "13px";

    /**
     * Interface for handling button click events.
     */
    public interface ButtonClickHandler {
        void onNumberClick(String number);
        void onDecimalClick();
        void onOperatorClick(Operation operation);
        void onClearClick();
        void onBackspaceClick();
        void onSquareClick();
    }

    /**
     * Creates a new ultra-clean calculator button panel.
     */
    public CalculatorButtonPanel(ButtonClickHandler clickHandler) {
        if (clickHandler == null) {
            throw new IllegalArgumentException("Button click handler cannot be null");
        }

        this.clickHandler = clickHandler;
        this.buttonGrid = createButtonGrid();
        setupGridConstraints();
        addAllButtons();
    }

    /**
     * Gets the button grid panel.
     */
    public GridPane getButtonPanel() {
        return buttonGrid;
    }

    /**
     * Creates the clean button grid.
     */
    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(GRID_GAP);
        grid.setVgap(GRID_GAP);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    /**
     * Sets up responsive grid constraints.
     */
    private void setupGridConstraints() {
        // Configure columns
        for (int i = 0; i < GRID_COLUMNS; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.ALWAYS);
            column.setFillWidth(true);
            column.setPrefWidth(BUTTON_SIZE);
            buttonGrid.getColumnConstraints().add(column);
        }

        // Configure rows
        for (int i = 0; i < GRID_ROWS; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setFillHeight(true);
            row.setPrefHeight(BUTTON_SIZE);
            buttonGrid.getRowConstraints().add(row);
        }
    }

    /**
     * Adds all buttons in clean calculator layout with functions on the right.
     */
    private void addAllButtons() {
        // Row 0: Clear, backspace, divide, and square root
        addSpecialButton("C", 0, 0, 2, 1, clickHandler::onClearClick);
        addFunctionButton("⌫", 2, 0, clickHandler::onBackspaceClick);
        addOperatorButton("÷", 3, 0, () -> clickHandler.onOperatorClick(Operation.DIVIDE));
        addFunctionButton("√", 4, 0, () -> clickHandler.onOperatorClick(Operation.SQUARE_ROOT));

        // Row 1: 7, 8, 9, multiply, square
        addNumberButton("7", 0, 1, () -> clickHandler.onNumberClick("7"));
        addNumberButton("8", 1, 1, () -> clickHandler.onNumberClick("8"));
        addNumberButton("9", 2, 1, () -> clickHandler.onNumberClick("9"));
        addOperatorButton("×", 3, 1, () -> clickHandler.onOperatorClick(Operation.MULTIPLY));
        addFunctionButton("x²", 4, 1, clickHandler::onSquareClick);

        // Row 2: 4, 5, 6, subtract, reciprocal
        addNumberButton("4", 0, 2, () -> clickHandler.onNumberClick("4"));
        addNumberButton("5", 1, 2, () -> clickHandler.onNumberClick("5"));
        addNumberButton("6", 2, 2, () -> clickHandler.onNumberClick("6"));
        addOperatorButton("−", 3, 2, () -> clickHandler.onOperatorClick(Operation.SUBTRACT));
        addFunctionButton("1/x", 4, 2, () -> clickHandler.onOperatorClick(Operation.RECIPROCAL));

        // Row 3: 1, 2, 3, add, percentage
        addNumberButton("1", 0, 3, () -> clickHandler.onNumberClick("1"));
        addNumberButton("2", 1, 3, () -> clickHandler.onNumberClick("2"));
        addNumberButton("3", 2, 3, () -> clickHandler.onNumberClick("3"));
        addOperatorButton("+", 3, 3, () -> clickHandler.onOperatorClick(Operation.ADD));
        addFunctionButton("%", 4, 3, () -> clickHandler.onOperatorClick(Operation.PERCENTAGE));

        // Row 4: 0 (wide), decimal, equals, factorial
        addNumberButton("0", 0, 4, 2, 1, () -> clickHandler.onNumberClick("0"));
        addNumberButton(".", 2, 4, clickHandler::onDecimalClick);
        addOperatorButton("=", 3, 4, () -> clickHandler.onOperatorClick(Operation.EQUALS));
        addFunctionButton("x!", 4, 4, () -> clickHandler.onOperatorClick(Operation.FACTORIAL));
    }

    // Button creation methods

    private void addNumberButton(String text, int col, int row, Runnable action) {
        addNumberButton(text, col, row, 1, 1, action);
    }

    private void addNumberButton(String text, int col, int row, int colSpan, int rowSpan, Runnable action) {
        Button button = createCleanButton(text, currentNumberBg, currentNumberText, BUTTON_FONT_SIZE, action);
        buttonGrid.add(button, col, row, colSpan, rowSpan);
    }

    private void addOperatorButton(String text, int col, int row, Runnable action) {
        Button button = createCleanButton(text, currentOperatorBg, currentOperatorText, BUTTON_FONT_SIZE, action);
        button.setStyle(button.getStyle() + " -fx-font-weight: 500;");
        buttonGrid.add(button, col, row);
    }

    private void addFunctionButton(String text, int col, int row, Runnable action) {
        Button button = createCleanButton(text, currentFunctionBg, currentFunctionText, SMALL_BUTTON_FONT_SIZE, action);
        buttonGrid.add(button, col, row);
    }

    private void addSpecialButton(String text, int col, int row, int colSpan, int rowSpan, Runnable action) {
        Button button = createCleanButton(text, currentSpecialBg, currentSpecialText, BUTTON_FONT_SIZE, action);
        button.setStyle(button.getStyle() + " -fx-font-weight: 500;");
        buttonGrid.add(button, col, row, colSpan, rowSpan);
    }

    /**
     * Creates ultra-clean button with minimal shadows and perfect spacing.
     */
    private Button createCleanButton(String text, String bgColor, String textColor, String fontSize, Runnable action) {
        Button button = new Button(text);

        String baseStyle = createCleanButtonStyle(bgColor, textColor, fontSize);
        button.setStyle(baseStyle);

        // Perfect sizing
        button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Subtle, clean interactions
        setupCleanButtonEffects(button, baseStyle);

        button.setOnAction(e -> {
            action.run();
            button.getScene().getRoot().requestFocus();
        });

        return button;
    }

    /**
     * Ultra-clean button styling with subtle shadows.
     */
    private String createCleanButtonStyle(String bgColor, String textColor, String fontSize) {
        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-family: '%s'; " +
                        "-fx-font-size: %s; " +
                        "-fx-font-weight: 400; " +
                        "-fx-border-color: #bdc3c7; " +
                        "-fx-border-width: 0.5px; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 1);",
                bgColor, textColor, FONT_FAMILY, fontSize
        );
    }

    // Current theme-aware colors
    private String currentNumberBg = "#ffffff";
    private String currentNumberText = "#2c3e50";
    private String currentOperatorBg = "#3498db";
    private String currentOperatorText = "#ffffff";
    private String currentFunctionBg = "#ecf0f1";
    private String currentFunctionText = "#34495e";
    private String currentSpecialBg = "#e74c3c";
    private String currentSpecialText = "#ffffff";

    /**
     * Apply theme to all buttons.
     */
    public void applyTheme(org.univocal.models.CalculatorSettings settings) {
        if (settings.isDarkTheme()) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }

        // Refresh all button styles
        refreshButtonStyles();
    }

    /**
     * Apply light theme colors.
     */
    private void applyLightTheme() {
        currentNumberBg = "#ffffff";
        currentNumberText = "#2c3e50";
        currentOperatorBg = "#3498db";
        currentOperatorText = "#ffffff";
        currentFunctionBg = "#ecf0f1";
        currentFunctionText = "#34495e";
        currentSpecialBg = "#e74c3c";
        currentSpecialText = "#ffffff";
    }

    /**
     * Apply dark theme colors.
     */
    private void applyDarkTheme() {
        currentNumberBg = "#34495e";
        currentNumberText = "#ecf0f1";
        currentOperatorBg = "#2980b9";
        currentOperatorText = "#ffffff";
        currentFunctionBg = "#2c3e50";
        currentFunctionText = "#bdc3c7";
        currentSpecialBg = "#c0392b";
        currentSpecialText = "#ffffff";
    }

    /**
     * Refresh styles of all existing buttons.
     */
    private void refreshButtonStyles() {
        buttonGrid.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                String text = button.getText();

                // Determine button type and apply appropriate style
                if (isNumberButton(text)) {
                    String style = createCleanButtonStyle(currentNumberBg, currentNumberText, BUTTON_FONT_SIZE);
                    button.setStyle(style);
                    setupCleanButtonEffects(button, style);
                } else if (isOperatorButton(text)) {
                    String style = createCleanButtonStyle(currentOperatorBg, currentOperatorText, BUTTON_FONT_SIZE);
                    style += " -fx-font-weight: 500;";
                    button.setStyle(style);
                    setupCleanButtonEffects(button, style);
                } else if (isFunctionButton(text)) {
                    String style = createCleanButtonStyle(currentFunctionBg, currentFunctionText, SMALL_BUTTON_FONT_SIZE);
                    button.setStyle(style);
                    setupCleanButtonEffects(button, style);
                } else if (isSpecialButton(text)) {
                    String style = createCleanButtonStyle(currentSpecialBg, currentSpecialText, BUTTON_FONT_SIZE);
                    style += " -fx-font-weight: 500;";
                    button.setStyle(style);
                    setupCleanButtonEffects(button, style);
                }
            }
        });
    }

    /**
     * Check if button is a number button.
     */
    private boolean isNumberButton(String text) {
        return text.matches("[0-9]") || text.equals(".");
    }

    /**
     * Check if button is an operator button.
     */
    private boolean isOperatorButton(String text) {
        return text.matches("[+−×÷=]");
    }

    /**
     * Check if button is a function button.
     */
    private boolean isFunctionButton(String text) {
        return text.matches("√|x²|1/x|%|x!");
    }

    /**
     * Check if button is a special button.
     */
    private boolean isSpecialButton(String text) {
        return text.equals("C") || text.equals("⌫");
    }

    /**
     * Subtle, clean interaction effects.
     */
    private void setupCleanButtonEffects(Button button, String baseStyle) {
        // Gentle hover
        button.setOnMouseEntered(e ->
                button.setStyle(baseStyle + " -fx-opacity: 0.9; -fx-scale-x: 1.01; -fx-scale-y: 1.01;")
        );

        button.setOnMouseExited(e ->
                button.setStyle(baseStyle)
        );

        // Subtle press
        button.setOnMousePressed(e ->
                button.setStyle(baseStyle + " -fx-opacity: 0.8; -fx-scale-x: 0.99; -fx-scale-y: 0.99;")
        );

        button.setOnMouseReleased(e ->
                button.setStyle(baseStyle + " -fx-opacity: 0.9;")
        );
    }
}