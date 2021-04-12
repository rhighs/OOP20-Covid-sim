package Environment;

import java.util.Map;
import java.util.HashMap;
import com.jme3.input.InputManager;
import com.jme3.input.controls.Trigger;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author rob
 */
public class Input implements ActionListener {
    private InputManager input;
    private Map<String, InputAction> actions;
    
    public Input(InputManager input){
        this.input = input;
        input.addListener(this);
        this.actions = new HashMap<>(); 
    }

    @Override
    public void onAction(String actionName, boolean isPressed, float arg2) {
        actions.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(actionName) && !isPressed)
                .forEach(e -> e.getValue().run());
    }
    
    public void addAction(String actionName, InputAction action, Trigger inputTrigger){
        input.addMapping(actionName, inputTrigger);
        actions.put(actionName, action);
    }
}
