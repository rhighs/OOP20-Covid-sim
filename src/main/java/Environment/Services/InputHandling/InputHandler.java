package Environment.Services.InputHandling;

import com.jme3.input.controls.Trigger;

public interface InputHandler {
    /**
     * Binds an InputAction and Trigger to a key string,
     * whenever a key mapped with a Keyboard or Mouse trigger is pressed
     * the InputAction associated with them is called.
     *
     * @param actionName key string for the trigger.
     * @param action runnable interface, contains a functions to execute.
     * @param inputTrigger keyboard or mouse event in which jme3 listens on.
     */
    void addAction(String actionName, InputAction action, Trigger inputTrigger);
}
