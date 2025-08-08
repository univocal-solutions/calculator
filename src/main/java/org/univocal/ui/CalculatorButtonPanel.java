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
        Button button = createCleanButton(text, NUMBER_BG, NUMBER_TEXT, BUTTON_FONT_SIZE, action);
        buttonGrid.add(button, col, row, colSpan, rowSpan);
    }

    private void addOperatorButton(String text, int col, int row, Runnable action) {
        Button button = createCleanButton(text, OPERATOR_BG, OPERATOR_TEXT, BUTTON_FONT_SIZE, action);
        button.setStyle(button.getStyle() + " -fx-font-weight: 500;");
        buttonGrid.add(button, col, row);
    }

    private void addFunctionButton(String text, int col, int row, Runnable action) {
        Button button = createCleanButton(text, FUNCTION_BG, FUNCTION_TEXT, SMALL_BUTTON_FONT_SIZE, action);
        buttonGrid.add(button, col, row);
    }

    private void addSpecialButton(String text, int col, int row, int colSpan, int rowSpan, Runnable action) {
        Button button = createCleanButton(text, SPECIAL_BG, SPECIAL_TEXT, BUTTON_FONT_SIZE, action);
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