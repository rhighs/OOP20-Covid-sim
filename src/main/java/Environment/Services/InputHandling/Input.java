package Environment.Services.InputHandling;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.Trigger;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rob
 */
public class Input implements ActionListener {
    private final InputManager input;
    private final Map<String, InputAction> actions;
    private Node guiNode;

    public Input(final InputManager input, final Node guiNode) {
        this.input = input;
        this.actions = new HashMap<>();
        input.addListener(this);
    }

    @Override
    public void onAction(String actionName, boolean isPressed, float arg2) {
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
