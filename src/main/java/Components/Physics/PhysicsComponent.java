package Components.Physics;

import Environment.Services.Physical.Physics;
import Simulation.Entity;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rob
 */
public class PhysicsComponent {

    private final Entity entity;

    private final Spatial spatial;

    private final BetterCharacterControl spatialControl;

    private final Physics physics;

    private Vector3f position;

    private GhostControl proximityBox;

    private final String USER_DATA_KEY = "entity";

    public PhysicsComponent(final Physics physics, Entity entity) {
        this.entity = entity;
        this.physics = physics;
        this.spatial = entity.getSpatial();
        this.position = spatial.getLocalTranslation();

        var randomMass = new Random();
        spatialControl = new BetterCharacterControl(.1f, .3f, (randomMass.nextInt(10) + 1));

        spatial.setUserData(USER_DATA_KEY, entity);
        setControlEnabled();
    }

    public void setControlEnabled() {
        spatial.addControl(spatialControl);
        spatialControl.setGravity(new Vector3f(0, -40, 0));
        spatialControl.setJumpForce(new Vector3f(0, 1, 0));
        physics.addToSpace(spatialControl);
        physics.addToSpace(spatial);
    }

    public void initProximityBox(final float size) {
        var boxSize = new Vector3f(size, size, size);
        var boxCollShape = new BoxCollisionShape(boxSize);

        proximityBox = new GhostControl(boxCollShape);
        spatial.addControl(proximityBox);
        proximityBox.setUserObject(entity);
        physics.addToSpace(proximityBox);
    }

    public Set<Entity> getNearEntities() {
        return proximityBox.getOverlappingObjects()
                .stream()
                .filter(o -> o instanceof GhostControl)
                .map(o -> (Entity) o.getUserObject())
                .collect(Collectors.toSet());
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(final Vector3f point) {
        this.spatial.setLocalTranslation(point);
    }

    public void update() {
        position = spatial.getLocalTranslation();
    }
}
