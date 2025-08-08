package org.univocal.ui;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.univocal.utils.Operation;

/**
 * Handles keyboard input for the calculator application.
 * This class is responsible only for capturing keyboard events,
 * mapping them to calculator operations, and delegating to the appropriate handlers.
 *
 * Features:
 * - Complete keyboard support for all calculator operations
 * - Number keys and numpad support
 * - Standard operator key mappings
 * - Special function keys (Escape, Backspace, etc.)
 * - Clean separation from UI components
 *
 * Supported Keys:
 * - Numbers: 0-9 (both main keyboard and numpad)
 * - Operators: +, -, *, /, = (Enter)
 * - Functions: Escape (clear), Backspace, Delete (clear entry)
 * - Decimal: . (period) and numpad decimal
 *
 * @author Univocal Team
 * @version 2.0
 */
public class CalculatorKeyboardHandler {

    private final KeyboardInputHandler inputHandler;
    private Scene scene;

    /**
     * Interface for handling keyboard input events.
     * This allows the keyboard handler to communicate with the controller
     * without knowing about the specific controller implementation.
     */
    public interface KeyboardInputHandler {
        void onNumberInput(String number);
        void onDecimalInput();
        void onOperatorInput(Operation operation);
        void onClearInput();
        void onBackspaceInput();
        void onClearEntryInput();
    }

    /**
     * Creates a new keyboard handler.
     *
     * @param inputHandler the handler for keyboard input events
     * @throws IllegalArgumentException if inputHandler is null
     */
    public CalculatorKeyboardHandler(KeyboardInputHandler inputHandler) {
        if (inputHandler == null) {
            throw new IllegalArgumentException("Keyboard input handler cannot be null");
        }
        this.inputHandler = inputHandler;
    }

    /**
     * Attaches keyboard handling to the specified scene.
     * Once attached, all keyboard events on this scene will be processed.
     *
     * @param scene the scene to attach keyboard handling to
     * @throws IllegalArgumentException if scene is null
     */
    public void attachToScene(Scene scene) {
        if (scene == null) {
            throw new IllegalArgumentException("Scene cannot be null");
        }

        // Detach from previous scene if any
        if (this.scene != null) {
            detachFromScene();
        }

        this.scene = scene;
        scene.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Detaches keyboard handling from the current scene.
     * After detaching, keyboard events will no longer be processed.
     */
    public void detachFromScene() {
        if (scene != null) {
            scene.setOnKeyPressed(null);
            scene = null;
        }
    }

    /**
     * Processes keyboard events and delegates to appropriate handlers.
     * This is the main method that maps keyboard input to calculator operations.
     *
     * @param event the keyboard event to process
     */
    private void handleKeyPress(KeyEvent event) {
        if (event == null) {
            return;
        }

        KeyCode keyCode = event.getCode();

        // Handle digit keys from main keyboard
        if (handleMainKeyboardDigits(keyCode)) {
            return;
        }

        // Handle numpad digits
        if (handleNumpadDigits(keyCode)) {
            return;
        }

        // Handle operator and special keys
        handleOperatorAndSpecialKeys(keyCode);
    }

    /**
     * Handles digit keys from the main keyboard (DIGIT0 through DIGIT9).
     *
     * @param keyCode the key code to check
     * @return true if a digit key was handled, false otherwise
     */
    private boolean handleMainKeyboardDigits(KeyCode keyCode) {
        if (!keyCode.isDigitKey()) {
            return false;
        }

        // Extract the digit character from the key code
        String keyName = keyCode.toString();
        if (keyName.startsWith("DIGIT")) {
            String digit = keyName.substring(5); // Remove "DIGIT" prefix
            if (digit.matches("[0-9]")) {
                inputHandler.onNumberInput(digit);
                return true;
            }
        }

        return false;
    }

    /**
     * Handles numpad digit keys (NUMPAD0 through NUMPAD9).
     *
     * @param keyCode the key code to check
     * @return true if a numpad digit was handled, false otherwise
     */
    private boolean handleNumpadDigits(KeyCode keyCode) {
        switch (keyCode) {
            case NUMPAD0:
                inputHandler.onNumberInput("0");
                return true;
            case NUMPAD1:
                inputHandler.onNumberInput("1");
                return true;
            case NUMPAD2:
                inputHandler.onNumberInput("2");
                return true;
            case NUMPAD3:
                inputHandler.onNumberInput("3");
                return true;
            case NUMPAD4:
                inputHandler.onNumberInput("4");
                return true;
            case NUMPAD5:
                inputHandler.onNumberInput("5");
                return true;
            case NUMPAD6:
                inputHandler.onNumberInput("6");
                return true;
            case NUMPAD7:
                inputHandler.onNumberInput("7");
                return true;
            case NUMPAD8:
                inputHandler.onNumberInput("8");
                return true;
            case NUMPAD9:
                inputHandler.onNumberInput("9");
                return true;
            default:
                return false;
        }
    }

    /**
     * Handles operator keys and special function keys.
     * Maps keyboard keys to calculator operations.
     *
     * @param keyCode the key code to process
     */
    private void handleOperatorAndSpecialKeys(KeyCode keyCode) {
        switch (keyCode) {
            // Decimal point
            case PERIOD:
            case DECIMAL:
                inputHandler.onDecimalInput();
                break;

            // Basic arithmetic operators
            case PLUS:
            case ADD:
                inputHandler.onOperatorInput(Operation.ADD);
                break;

            case MINUS:
            case SUBTRACT:
                inputHandler.onOperatorInput(Operation.SUBTRACT);
                break;

            case MULTIPLY:
                inputHandler.onOperatorInput(Operation.MULTIPLY);
                break;

            case DIVIDE:
            case SLASH:
                inputHandler.onOperatorInput(Operation.DIVIDE);
                break;

            // Equals operation
            case ENTER:
            case EQUALS:
                inputHandler.onOperatorInput(Operation.EQUALS);
                break;

            // Clear operations
            case ESCAPE:
                inputHandler.onClearInput();
                break;

            case BACK_SPACE:
                inputHandler.onBackspaceInput();
                break;

            case DELETE:
                inputHandler.onClearEntryInput();
                break;

            // Ignore other keys
            default:
                // Key not handled - this is normal for unsupported keys
                break;
        }
    }

    /**
     * Checks if the handler is currently attached to a scene.
     *
     * @return true if attached to a scene, false otherwise
     */
    public boolean isAttached() {
        return scene != null;
    }

    /**
     * Gets the currently attached scene.
     *
     * @return the attached scene, or null if not attached
     */
    public Scene getAttachedScene() {
        return scene;
    }

    /**
     * Creates a summary of supported keyboard shortcuts.
     * Useful for documentation or help systems.
     *
     * @return formatted string describing keyboard shortcuts
     */
    public String getKeyboardShortcuts() {
        StringBuilder shortcuts = new StringBuilder();
        shortcuts.append("Calculator Keyboard Shortcuts:\n\n");
        shortcuts.append("Numbers: 0-9 (main keyboard or numpad)\n");
        shortcuts.append("Decimal: . (period) or numpad decimal\n\n");
        shortcuts.append("Operations:\n");
        shortcuts.append("+ or numpad +     Addition\n");
        shortcuts.append("- or numpad -     Subtraction\n");
        shortcuts.append("* or numpad *     Multiplication\n");
        shortcuts.append("/ or numpad /     Division\n");
        shortcuts.append("= or Enter        Equals/Calculate\n\n");
        shortcuts.append("Functions:\n");
        shortcuts.append("Escape           Clear All\n");
        shortcuts.append("Backspace        Delete last digit\n");
        shortcuts.append("Delete           Clear Entry\n");
        return shortcuts.toString();
    }
}
