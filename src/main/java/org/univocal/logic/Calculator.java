package org.univocal.logic;

/**
 * Calculator class containing pure mathematical operations.
 * This class is stateless and contains only business logic for calculations.
 * Each method performs a single mathematical operation and handles edge cases appropriately.
 *
 * @author Calculator Team
 * @version 1.0
 */
public class Calculator {

    // Mathematical constants for precision and validation
    private static final double EPSILON = 1e-10;
    private static final double MAX_SAFE_VALUE = 1e15;
    private static final double MIN_SAFE_VALUE = -1e15;

    /**
     * Adds two numbers together.
     *
     * @param a the first addend
     * @param b the second addend
     * @return the sum of a and b
     * @throws ArithmeticException if the result would overflow
     */
    public double add(double a, double b) {
        validateInputs(a, b);
        double result = a + b;
        validateResult(result, "Addition overflow");
        return result;
    }

    /**
     * Subtracts the second number from the first number.
     *
     * @param minuend the number from which another number is subtracted
     * @param subtrahend the number to be subtracted
     * @return the difference (minuend - subtrahend)
     * @throws ArithmeticException if the result would overflow
     */
    public double subtract(double minuend, double subtrahend) {
        validateInputs(minuend, subtrahend);
        double result = minuend - subtrahend;
        validateResult(result, "Subtraction overflow");
        return result;
    }

    /**
     * Multiplies two numbers together.
     *
     * @param multiplicand the first factor
     * @param multiplier the second factor
     * @return the product of multiplicand and multiplier
     * @throws ArithmeticException if the result would overflow
     */
    public double multiply(double multiplicand, double multiplier) {
        validateInputs(multiplicand, multiplier);
        double result = multiplicand * multiplier;
        validateResult(result, "Multiplication overflow");
        return result;
    }

    /**
     * Divides the first number by the second number.
     *
     * @param dividend the number to be divided
     * @param divisor the number by which to divide
     * @return the quotient of dividend divided by divisor
     * @throws ArithmeticException if divisor is zero or result would overflow
     */
    public double divide(double dividend, double divisor) {
        validateInputs(dividend, divisor);

        if (Math.abs(divisor) < EPSILON) {
            throw new ArithmeticException("Division by zero");
        }

        double result = dividend / divisor;
        validateResult(result, "Division overflow");
        return result;
    }

    /**
     * Converts a number to its percentage equivalent by dividing by 100.
     * For example: 50 becomes 0.5, 25 becomes 0.25
     *
     * @param number the number to convert to percentage
     * @return the percentage value (number / 100)
     * @throws ArithmeticException if input is invalid
     */
    public double percentage(double number) {
        validateInputs(number);
        return number / 100.0;
    }

    /**
     * Calculates the square root of a number.
     *
     * @param number the number to find the square root of (must be non-negative)
     * @return the square root of the number
     * @throws ArithmeticException if the number is negative
     */
    public double squareRoot(double number) {
        validateInputs(number);

        if (number < 0) {
            throw new ArithmeticException("Square root of negative number");
        }

        double result = Math.sqrt(number);
        validateResult(result, "Square root calculation error");
        return result;
    }

    /**
     * Raises the base to the power of the exponent.
     *
     * @param base the base number
     * @param exponent the exponent to raise the base to
     * @return base raised to the power of exponent
     * @throws ArithmeticException if the calculation results in overflow or invalid result
     */
    public double power(double base, double exponent) {
        validateInputs(base, exponent);

        // Handle special cases
        if (base == 0.0 && exponent < 0) {
            throw new ArithmeticException("Zero cannot be raised to a negative power");
        }

        double result = Math.pow(base, exponent);
        validateResult(result, "Power calculation overflow");
        return result;
    }

    /**
     * Calculates the reciprocal of a number (1/x).
     *
     * @param number the number to find the reciprocal of (must not be zero)
     * @return the reciprocal (1/number)
     * @throws ArithmeticException if the number is zero
     */
    public double reciprocal(double number) {
        validateInputs(number);

        if (Math.abs(number) < EPSILON) {
            throw new ArithmeticException("Reciprocal of zero");
        }

        double result = 1.0 / number;
        validateResult(result, "Reciprocal calculation overflow");
        return result;
    }

    /**
     * Calculates the square of a number (x²).
     *
     * @param number the number to square
     * @return the square of the number
     * @throws ArithmeticException if the result would overflow
     */
    public double square(double number) {
        validateInputs(number);
        double result = number * number;
        validateResult(result, "Square calculation overflow");
        return result;
    }

    /**
     * Returns the absolute value of a number.
     *
     * @param number the number to get the absolute value of
     * @return the absolute value of the number
     */
    public double absolute(double number) {
        validateInputs(number);
        return Math.abs(number);
    }

    /**
     * Changes the sign of a number (positive becomes negative and vice versa).
     *
     * @param number the number to negate
     * @return the negated number
     */
    public double negate(double number) {
        validateInputs(number);
        return -number;
    }

    /**
     * Calculates the factorial of a number (n!).
     * The factorial of a non-negative integer n is the product of all positive integers less than or equal to n.
     * For example: 5! = 5 × 4 × 3 × 2 × 1 = 120
     *
     * @param number the number to calculate factorial of (must be non-negative integer ≤ 20)
     * @return the factorial of the number
     * @throws ArithmeticException if number is negative, not an integer, or too large
     */
    public double factorial(double number) {
        validateInputs(number);

        // Check if number is a non-negative integer
        if (number < 0) {
            throw new ArithmeticException("Factorial of negative number is undefined");
        }

        if (number != Math.floor(number)) {
            throw new ArithmeticException("Factorial is only defined for integers");
        }

        // Limit factorial to prevent overflow (20! is about 2.4 × 10^18)
        if (number > 20) {
            throw new ArithmeticException("Factorial too large (maximum: 20!)");
        }

        // Calculate factorial iteratively
        long result = 1;
        for (int i = 1; i <= (int) number; i++) {
            result *= i;
        }

        return (double) result;
    }

    // Private helper methods for validation

    /**
     * Validates single input parameter.
     *
     * @param value the value to validate
     * @throws ArithmeticException if the value is invalid
     */
    private void validateInputs(double value) {
        if (Double.isNaN(value)) {
            throw new ArithmeticException("Input cannot be NaN");
        }
        if (Double.isInfinite(value)) {
            throw new ArithmeticException("Input cannot be infinite");
        }
    }

    /**
     * Validates two input parameters.
     *
     * @param a the first value to validate
     * @param b the second value to validate
     * @throws ArithmeticException if either value is invalid
     */
    private void validateInputs(double a, double b) {
        validateInputs(a);
        validateInputs(b);
    }

    /**
     * Validates the result of a calculation.
     *
     * @param result the result to validate
     * @param errorMessage the error message to use if validation fails
     * @throws ArithmeticException if the result is invalid
     */
    private void validateResult(double result, String errorMessage) {
        if (Double.isNaN(result)) {
            throw new ArithmeticException(errorMessage + " - result is NaN");
        }
        if (Double.isInfinite(result)) {
            throw new ArithmeticException(errorMessage + " - result is infinite");
        }
        if (Math.abs(result) > MAX_SAFE_VALUE) {
            throw new ArithmeticException(errorMessage + " - result too large");
        }
    }
}