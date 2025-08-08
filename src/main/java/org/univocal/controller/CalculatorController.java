package org.univocal.controller;

import org.univocal.logic.Calculator;
import org.univocal.utils.Operation;

/**
 * Controller class that coordinates between the view and business logic
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class CalculatorController {

    private final Calculator calculator;
    private final InputValidator validator;
    private final ResultFormatter formatter;
    private final CalculatorState state;

    public CalculatorController() {
        this.calculator = new Calculator();
        this.validator = new InputValidator();
        this.formatter = new ResultFormatter();
        this.state = new CalculatorState();
    }

    /**
     * Handles number input
     */
    public String handleNumberInput(String digit) {
        if (state.hasError()) {
            return state.getDisplay();
        }

        if (state.isWaitingForOperand()) {
            state.setDisplay(digit.equals("0") && state.getDisplay().equals("0") ? "0" : digit);
            state.setWaitingForOperand(false);
        } else {
            // Prevent multiple leading zeros
            if (state.getDisplay().equals("0") && !digit.equals("0")) {
                state.setDisplay(digit);
            } else if (!state.getDisplay().equals("0")) {
                state.setDisplay(state.getDisplay() + digit);
            }
        }

        return state.getDisplay();
    }

    /**
     * Handles decimal point input
     */
    public String handleDecimalInput() {
        if (state.hasError()) {
            return state.getDisplay();
        }

        if (state.isWaitingForOperand()) {
            state.setDisplay("0.");
            state.setWaitingForOperand(false);
        } else if (!state.getDisplay().contains(".")) {
            state.setDisplay(state.getDisplay() + ".");
        }

        return state.getDisplay();
    }

    /**
     * Handles operation input
     */
    public String handleOperation(Operation operation) {
        if (state.hasError() && operation != Operation.CLEAR && operation != Operation.CLEAR_ENTRY) {
            return state.getDisplay();
        }

        try {
            switch (operation) {
                case CLEAR:
                    state.reset();
                    return state.getDisplay();

                case CLEAR_ENTRY:
                    state.clearEntry();
                    return state.getDisplay();

                case BACKSPACE:
                    return handleBackspace();

                case EQUALS:
                    return handleEquals();

                default:
                    if (operation.isUnaryOperation()) {
                        return handleUnaryOperation(operation);
                    } else if (operation.isBinaryOperation()) {
                        return handleBinaryOperation(operation);
                    }
                    return state.getDisplay();
            }
        } catch (Exception e) {
            state.setError(e.getMessage());
            return state.getDisplay();
        }
    }

    private String handleBackspace() {
        if (state.isWaitingForOperand()) {
            return state.getDisplay();
        }

        String display = state.getDisplay();
        if (display.length() > 1) {
            display = display.substring(0, display.length() - 1);
            // Handle case where we backspace to just a minus sign
            if (display.equals("-")) {
                display = "0";
                state.setWaitingForOperand(true);
            }
        } else {
            display = "0";
            state.setWaitingForOperand(true);
        }

        state.setDisplay(display);
        return state.getDisplay();
    }

    private String handleEquals() {
        if (state.getPendingOperation() == null || state.isWaitingForOperand()) {
            return state.getDisplay();
        }

        double currentValue = parseDisplayValue();
        double result = performBinaryOperation(state.getPreviousValue(), currentValue, state.getPendingOperation());

        state.setCurrentValue(result);
        state.setDisplay(formatter.formatCalculatorResult(result));
        state.setPendingOperation(null);
        state.setWaitingForOperand(true);

        return state.getDisplay();
    }

    private String handleUnaryOperation(Operation operation) {
        double currentValue = parseDisplayValue();
        double result;

        switch (operation) {
            case PERCENTAGE:
                result = calculator.percentage(currentValue);
                break;
            case SQUARE_ROOT:
                result = calculator.squareRoot(currentValue);
                break;
            case RECIPROCAL:
                result = calculator.reciprocal(currentValue);
                break;
            case FACTORIAL:
                result = calculator.factorial(currentValue);
                break;
            default:
                throw new IllegalArgumentException("Unknown unary operation: " + operation);
        }

        state.setCurrentValue(result);
        state.setDisplay(formatter.formatCalculatorResult(result));
        state.setWaitingForOperand(true);

        return state.getDisplay();
    }

    private String handleBinaryOperation(Operation operation) {
        double currentValue = parseDisplayValue();

        if (state.getPendingOperation() != null && !state.isWaitingForOperand()) {
            // Chain operations: perform pending operation first
            double result = performBinaryOperation(state.getPreviousValue(), currentValue, state.getPendingOperation());
            state.setCurrentValue(result);
            state.setDisplay(formatter.formatCalculatorResult(result));
        } else {
            state.setCurrentValue(currentValue);
        }

        state.setPreviousValue(state.getCurrentValue());
        state.setPendingOperation(operation);
        state.setWaitingForOperand(true);

        return state.getDisplay();
    }

    private double performBinaryOperation(double left, double right, Operation operation) {
        switch (operation) {
            case ADD:
                return calculator.add(left, right);
            case SUBTRACT:
                return calculator.subtract(left, right);
            case MULTIPLY:
                return calculator.multiply(left, right);
            case DIVIDE:
                return calculator.divide(left, right);
            case POWER:
                return calculator.power(left, right);
            default:
                throw new IllegalArgumentException("Unknown binary operation: " + operation);
        }
    }

    private double parseDisplayValue() {
        try {
            return Double.parseDouble(state.getDisplay());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid display value: " + state.getDisplay());
        }
    }

    /**
     * Gets the current display value
     */
    public String getCurrentDisplay() {
        return state.getDisplay();
    }

    /**
     * Checks if calculator has an error state
     */
    public boolean hasError() {
        return state.hasError();
    }

    /**
     * Gets the current calculator state (for testing/debugging)
     */
    public CalculatorState getState() {
        return state;
    }
}