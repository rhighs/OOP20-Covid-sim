package Components;

import Simulation.Entity;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author rob
 */
public class PhysicsComponent implements PhysicsCollisionListener {

    private final BulletAppState bullet;
    private final Vector3f spatialScale;
    private final Entity entity;
    private final Spatial spatial;
    private final BetterCharacterControl spatialControl;
    private Vector3f position;
    private SimpleApplication app;
    private GhostControl proximityBox;
    private Spatial touched;

    private static float DIRECTION_LENGTH = 20;

    public PhysicsComponent(Entity entity, BulletAppState bull) {
        this.app = app;
        this.entity = entity;
        this.spatial = entity.getSpatial();
        this.spatialScale = spatial.getLocalTransform().getScale();
        bullet = bull;
        this.position = spatial.getLocalTranslation();

        spatialControl = new BetterCharacterControl(0.1f, 9f, 5);
        setControlEnabled(true);
    }

    public void setControlEnabled(final boolean value) {

        if (value) {

            spatial.addControl(spatialControl);
            spatialControl.setGravity(new Vector3f(0, -40, 0));
            spatialControl.setJumpForce(new Vector3f(0, 1, 0));
            bullet.getPhysicsSpace().add(spatialControl);
            bullet.getPhysicsSpace().add(spatial);

            //this triggers a call to this.collision whenever we hit something phisical
            spatialControl.getPhysicsSpace().addCollisionListener(this);

            //bullet.getPhysicsSpace().addCollisionListener(this);
        } else if (spatial.getControl(BetterCharacterControl.class) != null && bullet.getPhysicsSpace().getRigidBodyList().size() != 0) {

            spatial.removeControl(spatialControl);
            spatialControl.getPhysicsSpace().removeCollisionListener(this);
            bullet.getPhysicsSpace().remove(spatialControl);
            bullet.getPhysicsSpace().removeAll(spatial);

        }
    }

    public void collision(PhysicsCollisionEvent collisionEvent) {
        /*boolean isNodeA = collisionEvent.getNodeA().equals(spatial);
        
        if(isNodeA){
            touched = collisionEvent.getNodeB();
        }else{
            touched = collisionEvent.getNodeA();
        }
        
        //System.out.println(spatial + " touched: " + touched);*/
    }

    public BetterCharacterControl getControl() {
        return spatialControl;
    }

    public void initProximityBox(final float size) {
        // box size represented as a Vector3f
        var boxSize = new Vector3f(size, size, size);
        var boxCollShape = new BoxCollisionShape(boxSize);

        proximityBox = new GhostControl(boxCollShape);

        spatial.addControl(proximityBox);
        proximityBox.setUserObject(entity);
        bullet.getPhysicsSpace().add(proximityBox);
    }

    public Set<Entity> getNearEntities() {
        int nNear = proximityBox.getOverlappingCount();

        if (nNear != 0) {
            var a = proximityBox.getOverlappingObjects()
                    .stream()
                    //.filter(o -> (o.getUserObject() instanceof Entity))
                    .filter(o -> o instanceof GhostControl)
                    .map(o -> (Entity) o.getUserObject())
                    .collect(Collectors.toSet());

            System.out.println("trovati: " + a.size());
            return a;
        }

        return Collections.EMPTY_SET;
    }

    public Optional<Float> getNearDistance(final Entity guest) {

        boolean isNear = getNearEntities().contains(guest);
        var guestPos = guest.getPosition();

        if (isNear) {
            float distance = position.distance(guestPos);

            return Optional.of(distance);
        }

        return Optional.empty();
    }

    public void update() {
        position = spatial.getLocalTranslation();
    }
}
