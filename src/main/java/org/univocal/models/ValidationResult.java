package org.univocal.models;

/**
 * Represents the result of input validation
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class ValidationResult {
    private final boolean valid;
    private final String errorMessage;
    private final double value;

    private ValidationResult(boolean valid, String errorMessage, double value) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.value = value;
    }

    /**
     * Creates a successful validation result
     */
    public static ValidationResult success(double value) {
        return new ValidationResult(true, null, value);
    }

    /**
     * Creates an error validation result
     */
    public static ValidationResult error(String message) {
        return new ValidationResult(false, message, 0);
    }

    /**
     * Returns true if validation was successful
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the error message if validation failed
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the parsed value if validation was successful
     */
    public double getValue() {
        return value;
    }
}
