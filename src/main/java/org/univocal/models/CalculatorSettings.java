package org.univocal.models;

/**
 * Model class for calculator settings and preferences.
 * Handles theme selection and other user preferences.
 *
 * @author Calculator Team
 * @version 1.0
 */
public class CalculatorSettings {

    public enum Theme {
        LIGHT("Light Theme"),
        DARK("Dark Theme");

        private final String displayName;

        Theme(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default settings
    private Theme theme = Theme.LIGHT;

    /**
     * Gets the current theme.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the current theme.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Checks if current theme is dark.
     */
    public boolean isDarkTheme() {
        return theme == Theme.DARK;
    }

    /**
     * Checks if current theme is light.
     */
    public boolean isLightTheme() {
        return theme == Theme.LIGHT;
    }

    /**
     * Toggles between light and dark theme.
     */
    public void toggleTheme() {
        theme = (theme == Theme.LIGHT) ? Theme.DARK : Theme.LIGHT;
    }
}