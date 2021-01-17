package Engine.physics;

import Engine.items.Entity;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author rob, chris
 */
public class PhysicsComponent extends GhostControl implements PhysicsTickListener {

    private Entity entity;
    private Spatial spatial;
    private CollisionShape collisionShape;
    private boolean isCollisionEnabled = false;
    private Map<Entity, Float> collidingEntities = new HashMap<>();
    final MotionPath path;

    public PhysicsComponent(final Entity entity, final BulletAppState bullet) {
        this.entity = entity;
        spatial = entity.getSpatial();
        collisionShape = new CollisionShapeFactory().createMeshShape(spatial);
        setCollisionShape(collisionShape);
        // this must be at the end or it causes a null exception
        bullet.getPhysicsSpace().add(this);
        path = new MotionPath();
        spatial.addControl(this);
    }

    /* *** Getters and setters *** */
    public Entity getEntity() {
        return entity;
    }

    public Vector3f getPosition() {
        return spatial.getLocalTranslation();
    }

    public void setPosition(final Vector3f newpos) {
        spatial.setLocalTranslation(newpos);
    }

    public Map<Entity, Float> getCollidingEntities() {
        return collidingEntities;
    }

    public void enableCollision(boolean enabled) {
        isCollisionEnabled = enabled;
    }

    /* *** Actual member functions *** */
    public void move(final Vector3f offset) {
        spatial.move(offset);
    }

    @Override
    public void prePhysicsTick(PhysicsSpace arg0, float arg1) {

    }

    @Override
    public void physicsTick(PhysicsSpace arg0, float arg1) {
        if (!isCollisionEnabled) {
            return;
        }

        collidingEntities.clear();
        if (getOverlappingCount() == 0) {
            return;
        }

        Vector3f v1 = entity.getSpatial().getLocalTranslation();
        for (var collidingEntity : getOverlappingObjects()) {
            Entity e = ((PhysicsComponent) collidingEntity).getEntity();
            Vector3f v2 = e.getSpatial().getLocalTranslation();
            float distance = v1.distance(v2);
            collidingEntities.put(e, distance);
        }
    }
}
