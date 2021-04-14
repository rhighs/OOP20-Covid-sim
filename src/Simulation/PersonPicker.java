package Simulation;

import Environment.Input;
import com.jme3.math.Ray;
import java.util.Optional;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import Environment.InputAction;
import Environment.Locator;
import com.jme3.input.MouseInput;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author rob
 */
public class PersonPicker implements ActionListener {

    //private InputManager input;
    private CollisionResults results;
    private SimpleApplication app;
    private Input input;
    private SimulationCamera cam;
    
    private Node rootNode;

    public PersonPicker(SimpleApplication app, final Input input) {
        /*
        input = app.getInputManager();
        input.addMapping("attachToPerson", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        input.addMapping("detachFromPerson", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        input.setCursorVisible(true);
        
        rootNode = app.getRootNode();
        input.addListener(this, "attachToPerson");
        */
        
        this.input = input;
        InputAction attachCam = () -> this.attachCamToPerson();
        InputAction detachCam = () -> cam.detachEntity();
        input.addAction("attachToPerson", attachCam, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        input.addAction("detachFromPerson", detachCam, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        this.app = app;
    }

    public Optional<Person> pickPerson() {
        results = new CollisionResults();
        Spatial node;

        Ray ray = new Ray(cam.getLocation(), cam.getDirection());

        rootNode.collideWith(ray, results);

        var found = results.getClosestCollision();

        try{
            node = found.getGeometry();
        }catch (Exception ex){
            var err = ex.toString();
            return Optional.empty();
        }

        if (node == null) {
            return Optional.empty();
        }

        var userData = node.getUserData("entity");

        if (userData == null) {
            return Optional.empty();
        }

        return Optional.of((Person) userData);
    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        switch(name){
            case "attachToPerson":
                if(!keyPressed){
                    attachCamToPerson();
                }
                break;
                
            case "detachFromPerson":
                if(!keyPressed){
                    cam.detachEntity();
                }
                break;
            default:
                break;
        }
    }

    private void attachCamToPerson(){
        Person p = null;

        try{
            p = (Person) pickPerson().get();
            cam.attachToEntity(p);
        }catch(Exception ex){
            var err = ex.toString();
        }
    }

}
