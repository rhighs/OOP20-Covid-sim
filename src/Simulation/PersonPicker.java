package Simulation;

import Environment.Ambient;
import Environment.SimulationCamera;
import Environment.Input;
import com.jme3.math.Ray;
import java.util.Optional;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import Environment.InputAction;
import com.jme3.input.MouseInput;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author rob
 */
public class PersonPicker {
    private Input input;
    private Node rootNode;
    private SimulationCamera cam;
    private CollisionResults results;
    private Ambient ambient;

    public PersonPicker(final Input input, final Ambient ambient, final SimulationCamera cam) {
        this.input = input;
        this.cam = cam;
        this.ambient = ambient;
        this.rootNode = ambient.getRootNode();
        InputAction attachCam = () -> this.attachCamToPerson();
        InputAction detachCam = () -> cam.detachEntity();   
        input.addAction("attachToPerson", attachCam, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        input.addAction("detachFromPerson", detachCam, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    }

    public Optional<Person> pickPerson() {
        Spatial node;
        results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());

        try{
            rootNode.collideWith(ray, results);
            var found = results.getClosestCollision();
            node = found.getGeometry();
        }catch (Exception ex){
            return Optional.empty();
        }

        if (node == null) {
            return Optional.empty();
        }
        
        var personAsUserData = node.getUserData("entity");

        if (personAsUserData == null) {
            return Optional.empty();
        }

        return Optional.of((Person) personAsUserData);
    }

    private void attachCamToPerson(){
        Person p = null;

        try{
            p = (Person) pickPerson().get();
            cam.attachToEntity(p);
        }catch(Exception ex){
            return;
        }
    }

}