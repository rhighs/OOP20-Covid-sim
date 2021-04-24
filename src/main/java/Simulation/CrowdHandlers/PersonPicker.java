package Simulation.CrowdHandlers;

import java.util.Optional;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import Environment.Services.Graphical.Ambient;
import Environment.Services.InputHandling.InputHandler;
import Environment.Services.InputHandling.InputAction;
import Environment.Services.Graphical.SimulationCamera;
import Simulation.Person;

/**
 * @author rob
 */
public class PersonPicker implements PersonPickerInterface {
    private final InputHandler input;
    private final Node rootNode;
    private final SimulationCamera cam;
    private final Ambient ambient;
    private CollisionResults results;

    public PersonPicker(final InputHandler input, final Ambient ambient, final SimulationCamera cam) {
        this.input = input;
        this.cam = cam;
        this.ambient = ambient;
        this.rootNode = ambient.getRootNode();
        InputAction attachCam = () -> this.attachCamToPerson();
        InputAction detachCam = () -> cam.detachEntity();
        input.addAction("attachToPerson", attachCam, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        input.addAction("detachFromPerson", detachCam, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    }

    @Override
    public Optional<Person> pickPerson() {
        Spatial node;
        results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());

        try {
            rootNode.collideWith(ray, results);
            var found = results.getClosestCollision();
            node = found.getGeometry();
        } catch (Exception ex) {
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

    private void attachCamToPerson() {
        Person p = null;

        try {
            p = pickPerson().get();
            cam.attachToEntity(p);
        } catch (Exception ex) {
            return;
        }
    }
}
