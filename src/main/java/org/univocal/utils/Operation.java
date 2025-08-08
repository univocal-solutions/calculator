package org.univocal.utils;

/**
 * Enumeration for calculator operations
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    PERCENTAGE("%"),
    SQUARE_ROOT("√"),
    POWER("^"),
    RECIPROCAL("1/x"),
    FACTORIAL("x!"),
    EQUALS("="),
    CLEAR("C"),
    CLEAR_ENTRY("CE"),
    BACKSPACE("⌫");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isUnaryOperation() {
        return this == PERCENTAGE || this == SQUARE_ROOT || this == RECIPROCAL || this == FACTORIAL;
    }

    public boolean isBinaryOperation() {
        return this == ADD || this == SUBTRACT || this == MULTIPLY || this == DIVIDE || this == POWER;
    }
}