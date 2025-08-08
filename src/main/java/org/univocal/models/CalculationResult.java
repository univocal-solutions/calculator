package org.univocal.models;

/**
 * Represents the result of a calculation operation
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class CalculationResult {
    private final boolean success;
    private final String message;

    private CalculationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Creates a successful calculation result
     */
    public static CalculationResult success(String result) {
        return new CalculationResult(true, result);
    }

    /**
     * Creates an error calculation result
     */
    public static CalculationResult error(String errorMessage) {
        return new CalculationResult(false, errorMessage);
    }

    /**
     * Returns true if the calculation was successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the result message
     */
    public String getMessage() {
        return message;
    }
}
