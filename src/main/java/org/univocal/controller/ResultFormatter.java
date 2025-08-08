package org.univocal.controller;

import java.text.DecimalFormat;

/**
 * Formats calculator results for display
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class ResultFormatter {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##########");

    /**
     * Formats the calculation result for display
     */
    public String formatResult(double result) {
        // Show whole numbers without decimal places
        if (result == (long) result) {
            return String.format("Result: %.0f", result);
        } else {
            return String.format("Result: %.2f", result);
        }
    }

    /**
     * Formats calculator display result (without "Result:" prefix)
     */
    public String formatCalculatorResult(double result) {
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            return "Error";
        }

        // Handle very large or very small numbers with scientific notation
        if (Math.abs(result) >= 1e10 || (Math.abs(result) < 1e-4 && result != 0)) {
            return String.format("%.4e", result);
        }

        // Format normal numbers
        String formatted = DECIMAL_FORMAT.format(result);

        // Ensure we don't show trailing zeros after decimal point
        if (formatted.contains(".")) {
            formatted = formatted.replaceAll("0+$", "").replaceAll("\\.$", "");
        }

        return formatted.isEmpty() ? "0" : formatted;
    }

    /**
     * Formats an error message for display
     */
    public String formatError(String errorMessage) {
        return "Result: " + errorMessage;
    }
}