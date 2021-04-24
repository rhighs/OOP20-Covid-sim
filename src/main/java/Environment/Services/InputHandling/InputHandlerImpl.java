package Environment.Services.InputHandling;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.Trigger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rob
 */
public class InputHandlerImpl implements InputHandler, ActionListener {

    private final InputManager input;

    private final Map<String, InputAction> actions;

    public InputHandlerImpl(final InputManager input) {
        this.input = input;
        this.actions = new HashMap<>();
        input.addListener(this);
    }

    @Override
    public void onAction(String actionName, boolean isPressed, float tpf) {
        actions.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(actionName) && !isPressed)
                .forEach(e -> {
                    try {
                        e.getValue().run();
                    } catch (Exception ex) {
                        return;
                    }
                });
    }

    public void addAction(String actionName, InputAction action, Trigger inputTrigger) {
        input.addListener(this, actionName);
        input.addMapping(actionName, inputTrigger);
        actions.put(actionName, action);
    }
}
