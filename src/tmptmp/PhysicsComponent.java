package tmptmp;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import tmptmp.Entity;

/**
 *
 * @author rob
 */
public class PhysicsComponent {
    private final Entity entity;
    private final BulletAppState bState;
    private final Spatial spatial;
    private Vector3f position;
    private GhostControl proximityBox;

    public PhysicsComponent(Entity entity, BulletAppState bState) {
        this.entity = entity;
        this.spatial = entity.getSpatial();
        this.bState = bState;
        this.position = spatial.getLocalTranslation();
    }

    /*
    public void setControlEnabled(final boolean value) {
        if (value) {
            spatial.addControl(spatialControl);
            spatialControl.setGravity(new Vector3f(0, -40, 0));
            spatialControl.setJumpForce(new Vector3f(0, 1, 0));
            bullet.getPhysicsSpace().add(spatialControl);
            bullet.getPhysicsSpace().add(spatial);
            //this triggers a call to this.collision whenever we hit something phisical
            //spatialControl.getPhysicsSpace().addCollisionListener(this);
            //bullet.getPhysicsSpace().addCollisionListener(this);
        } else if (spatial.getControl(BetterCharacterControl.class) != null && bullet.getPhysicsSpace().getRigidBodyList().size() != 0) {
            spatial.removeControl(spatialControl);
            //spatialControl.getPhysicsSpace().removeCollisionListener(this);
            bullet.getPhysicsSpace().remove(spatialControl);
            bullet.getPhysicsSpace().removeAll(spatial);
        }
    }
    */

    /*
    public BetterCharacterControl getControl() {
        return spatialControl;
    }
    */

    public void initProximityBox(final float size) {
        // box size represented as a Vector3f
        var boxSize = new Vector3f(size, size, size);
        var boxCollShape = new BoxCollisionShape(boxSize);
        proximityBox = new GhostControl(boxCollShape);
        spatial.addControl(proximityBox);
        proximityBox.setUserObject(entity);
        bState.getPhysicsSpace().add(proximityBox);
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
            return a;
        }
        return Collections.EMPTY_SET;
    }

    public Optional<Float> getNearDistance(final Entity guest) {
        boolean isNear = getNearEntities().contains(guest);
        Vector3f guestPos = guest.getPos();
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
