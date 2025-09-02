package org.univocal.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.univocal.models.CalculatorSettings;

/**
 * Calculator display component with clean design and theme support.
 * Handles the visual presentation of numbers, results, and error states.
 * Supports both light and dark themes with smooth transitions.
 *
 * Features:
 * - Clean, readable display with modern typography
 * - Error state visual feedback with color coding
 * - Right-aligned text like professional calculators
 * - Theme-aware styling (light/dark mode support)
 * - Responsive sizing and proper padding
 *
 * @author Calculator Team
 * @version 2.0
 */
public class CalculatorDisplay {

    // Display components
    private final Label displayLabel;
    private final VBox displayContainer;

    // Current theme reference
    private CalculatorSettings currentSettings;

    // Display configuration constants
    private static final int DISPLAY_HEIGHT = 100;
    private static final String FONT_FAMILY = "SF Pro Display, Segoe UI, system-ui";
    private static final String DISPLAY_FONT_SIZE = "36px";
    private static final String DEFAULT_DISPLAY_TEXT = "0";
    private static final int DISPLAY_PADDING = 25;
    private static final int CONTAINER_BOTTOM_PADDING = 25;

    // Light theme colors
    private static final String LIGHT_BG_COLOR = "#ffffff";
    private static final String LIGHT_TEXT_COLOR = "#2c3e50";
    private static final String LIGHT_ERROR_COLOR = "#e74c3c";
    private static final String LIGHT_BORDER_COLOR = "#ecf0f1";

    // Dark theme colors
    private static final String DARK_BG_COLOR = "#34495e";
    private static final String DARK_TEXT_COLOR = "#ecf0f1";
    private static final String DARK_ERROR_COLOR = "#ff6b6b";
    private static final String DARK_BORDER_COLOR = "#2c3e50";

    /**
     * Creates a new calculator display component.
     * Initializes with default light theme and zero display.
     */
    public CalculatorDisplay() {
        this.currentSettings = new CalculatorSettings(); // Default to light theme
        this.displayLabel = createDisplayLabel();
        this.displayContainer = createDisplayContainer();

        // Apply initial theme after displayLabel is created
        applyLightTheme();
    }

    /**
     * Gets the display container for adding to main layout.
     *
     * @return VBox containing the styled display label
     */
    public VBox getDisplayContainer() {
        return displayContainer;
    }

    /**
     * Updates the display with new text using normal styling.
     * Automatically handles null/empty text by showing "0".
     *
     * @param text the text to display (null or empty will show "0")
     */
    public void updateText(String text) {
        if (text == null || text.trim().isEmpty()) {
            text = DEFAULT_DISPLAY_TEXT;
        }

        displayLabel.setText(text);
        applyNormalStyling();
    }

    /**
     * Shows error text with error styling (red color).
     * Used to indicate calculation errors or invalid operations.
     *
     * @param errorText the error text to display
     */
    public void showError(String errorText) {
        if (errorText == null || errorText.trim().isEmpty()) {
            errorText = "Error";
        }

        displayLabel.setText(errorText);
        applyErrorStyling();
    }

    /**
     * Gets the current text displayed on the calculator.
     *
     * @return the current display text
     */
    public String getCurrentText() {
        return displayLabel.getText();
    }

    /**
     * Resets the display to its initial state (showing "0").
     * Uses normal styling with current theme.
     */
    public void reset() {
        updateText(DEFAULT_DISPLAY_TEXT);
    }

    /**
     * Applies the specified theme to the display.
     * Updates colors, borders, and effects based on theme selection.
     *
     * @param settings the calculator settings containing theme preference
     */
    public void applyTheme(CalculatorSettings settings) {
        if (settings == null) {
            return;
        }

        this.currentSettings = settings;

        if (settings.isDarkTheme()) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }

    /**
     * Creates the main display label with initial styling.
     *
     * @return configured Label for displaying calculator output
     */
    private Label createDisplayLabel() {
        Label label = new Label(DEFAULT_DISPLAY_TEXT);
        label.setPrefHeight(DISPLAY_HEIGHT);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER_RIGHT);

        // Note: Styling is applied after creation in constructor
        return label;
    }

    /**
     * Creates the container that holds the display label.
     *
     * @return VBox container with proper padding and alignment
     */
    private VBox createDisplayContainer() {
        VBox container = new VBox();
        container.setPadding(new Insets(0, 0, CONTAINER_BOTTOM_PADDING, 0));
        container.setAlignment(Pos.CENTER_RIGHT);
        container.getChildren().add(displayLabel);
        return container;
    }

    /**
     * Applies light theme styling to the display.
     * Uses white background with dark text and subtle shadows.
     */
    private void applyLightTheme() {
        String lightStyle = createDisplayStyle(
                LIGHT_BG_COLOR,
                LIGHT_TEXT_COLOR,
                LIGHT_BORDER_COLOR,
                "dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 2)"
        );
        displayLabel.setStyle(lightStyle);
    }

    /**
     * Applies dark theme styling to the display.
     * Uses dark background with light text and stronger shadows.
     */
    private void applyDarkTheme() {
        String darkStyle = createDisplayStyle(
                DARK_BG_COLOR,
                DARK_TEXT_COLOR,
                DARK_BORDER_COLOR,
                "dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2)"
        );
        displayLabel.setStyle(darkStyle);
    }

    /**
     * Applies normal styling based on current theme.
     */
    private void applyNormalStyling() {
        if (currentSettings.isDarkTheme()) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }

    /**
     * Applies error styling with red text color.
     */
    private void applyErrorStyling() {
        String errorColor = currentSettings.isDarkTheme() ? DARK_ERROR_COLOR : LIGHT_ERROR_COLOR;
        String bgColor = currentSettings.isDarkTheme() ? DARK_BG_COLOR : LIGHT_BG_COLOR;
        String borderColor = currentSettings.isDarkTheme() ? DARK_BORDER_COLOR : LIGHT_BORDER_COLOR;
        String shadow = currentSettings.isDarkTheme() ?
                "dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2)" :
                "dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 2)";

        String errorStyle = createDisplayStyle(bgColor, errorColor, borderColor, shadow);
        displayLabel.setStyle(errorStyle);
    }

    /**
     * Creates a complete display style string with the specified colors.
     *
     * @param backgroundColor the background color
     * @param textColor the text color
     * @param borderColor the border color
     * @param shadowEffect the shadow effect
     * @return complete CSS style string
     */
    private String createDisplayStyle(String backgroundColor, String textColor, String borderColor, String shadowEffect) {
        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-family: '%s'; " +
                        "-fx-font-size: %s; " +
                        "-fx-font-weight: 300; " +
                        "-fx-padding: %dpx; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: %s;",
                backgroundColor, textColor, FONT_FAMILY, DISPLAY_FONT_SIZE,
                DISPLAY_PADDING, borderColor, shadowEffect
        );
    }

    /**
     * Checks if the display is currently showing an error.
     *
     * @return true if displaying error text, false otherwise
     */
    public boolean isShowingError() {
        String currentText = displayLabel.getText();
        return currentText != null && currentText.toLowerCase().contains("error");
    }

    /**
     * Gets the current theme settings.
     *
     * @return the current calculator settings
     */
    public CalculatorSettings getCurrentSettings() {
        return currentSettings;
    }

    /**
     * Forces a refresh of the display styling.
     * Useful after theme changes or when styling needs to be reapplied.
     */
    public void refreshStyling() {
        applyNormalStyling();
    }
}