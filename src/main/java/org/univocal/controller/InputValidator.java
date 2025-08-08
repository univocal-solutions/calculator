package org.univocal.controller;

import org.univocal.models.ValidationResult;

/**
 * Handles input validation and parsing
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class InputValidator {

    /**
     * Validates and parses input text to a number
     */
    public ValidationResult validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return ValidationResult.error("Please enter a number");
        }

        try {
            double value = Double.parseDouble(input.trim());
            return ValidationResult.success(value);
        } catch (NumberFormatException e) {
            return ValidationResult.error("Please enter a valid number");
        }
    }
}