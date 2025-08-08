package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Ultra-clean calculator display with minimalist design.
 * Features a pristine white background with subtle shadows and clean typography.
 *
 * @author Univocal Team
 * @version 2.0
 */
public class CalculatorDisplay {

    // Display component
    private final Label displayLabel;
    private final VBox displayContainer;

    // Clean minimalist design constants
    private static final int DISPLAY_HEIGHT = 100;
    private static final String DISPLAY_BG_COLOR = "#ffffff";       // Pure white
    private static final String DISPLAY_TEXT_COLOR = "#2c3e50";     // Dark blue-gray
    private static final String ERROR_TEXT_COLOR = "#e74c3c";       // Clean red
    private static final String FONT_FAMILY = "SF Pro Display, Segoe UI, system-ui";
    private static final String DISPLAY_FONT_SIZE = "36px";
    private static final String DEFAULT_DISPLAY_TEXT = "0";

    /**
     * Creates a new ultra-clean calculator display.
     */
    public CalculatorDisplay() {
        this.displayLabel = createDisplayLabel();
        this.displayContainer = createDisplayContainer();
    }

    /**
     * Gets the display container for adding to layouts.
     */
    public VBox getDisplayContainer() {
        return displayContainer;
    }

    /**
     * Updates display with clean styling.
     */
    public void updateText(String text) {
        if (text == null || text.trim().isEmpty()) {
            text = DEFAULT_DISPLAY_TEXT;
        }
        displayLabel.setText(text);
        applyNormalStyling();
    }

    /**
     * Shows error with subtle red styling.
     */
    public void showError(String errorText) {
        if (errorText == null || errorText.trim().isEmpty()) {
            errorText = "Error";
        }
        displayLabel.setText(errorText);
        applyErrorStyling();
    }

    /**
     * Gets current display text.
     */
    public String getCurrentText() {
        return displayLabel.getText();
    }

    /**
     * Resets to clean initial state.
     */
    public void reset() {
        updateText(DEFAULT_DISPLAY_TEXT);
    }

    /**
     * Creates the clean display label.
     */
    private Label createDisplayLabel() {
        Label label = new Label(DEFAULT_DISPLAY_TEXT);
        label.setStyle(createNormalDisplayStyle());
        label.setPrefHeight(DISPLAY_HEIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER_RIGHT);
        return label;
    }

    /**
     * Creates minimalist container.
     */
    private VBox createDisplayContainer() {
        VBox container = new VBox();
        container.setPadding(new Insets(0, 0, 25, 0));
        container.setAlignment(Pos.CENTER_RIGHT);
        container.getChildren().add(displayLabel);
        return container;
    }

    /**
     * Clean normal display styling.
     */
    private String createNormalDisplayStyle() {
        return createDisplayStyleWithTextColor(DISPLAY_TEXT_COLOR);
    }

    /**
     * Clean error display styling.
     */
    private String createErrorDisplayStyle() {
        return createDisplayStyleWithTextColor(ERROR_TEXT_COLOR);
    }

    /**
     * Ultra-clean display style with subtle shadow.
     */
    private String createDisplayStyleWithTextColor(String textColor) {
        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-family: '%s'; " +
                        "-fx-font-size: %s; " +
                        "-fx-font-weight: 300; " +
                        "-fx-padding: 25px; " +
                        "-fx-border-color: #ecf0f1; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 2);",
                DISPLAY_BG_COLOR, textColor, FONT_FAMILY, DISPLAY_FONT_SIZE
        );
    }

    /**
     * Apply clean normal styling.
     */
    private void applyNormalStyling() {
        displayLabel.setStyle(createNormalDisplayStyle());
    }

    /**
     * Apply clean error styling.
     */
    private void applyErrorStyling() {
        displayLabel.setStyle(createErrorDisplayStyle());
    }
}