package org.univocal.controller;

import org.univocal.utils.Operation;

/**
 * Manages the current state of the calculator
 *
 *  * @author Univocal Team
 *  * @version 2.0
 */
public class CalculatorState {
    private double currentValue;
    private double previousValue;
    private Operation pendingOperation;
    private boolean waitingForOperand;
    private boolean hasError;
    private String errorMessage;
    private String display;

    public CalculatorState() {
        reset();
    }

    public void reset() {
        currentValue = 0.0;
        previousValue = 0.0;
        pendingOperation = null;
        waitingForOperand = true;
        hasError = false;
        errorMessage = null;
        display = "0";
    }

    public void clearEntry() {
        currentValue = 0.0;
        display = "0";
        waitingForOperand = true;
        hasError = false;
        errorMessage = null;
    }

    // Getters and setters
    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(double previousValue) {
        this.previousValue = previousValue;
    }

    public Operation getPendingOperation() {
        return pendingOperation;
    }

    public void setPendingOperation(Operation pendingOperation) {
        this.pendingOperation = pendingOperation;
    }

    public boolean isWaitingForOperand() {
        return waitingForOperand;
    }

    public void setWaitingForOperand(boolean waitingForOperand) {
        this.waitingForOperand = waitingForOperand;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setError(String errorMessage) {
        this.hasError = true;
        this.errorMessage = errorMessage;
        this.display = "Error";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
