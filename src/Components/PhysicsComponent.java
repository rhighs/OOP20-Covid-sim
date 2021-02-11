package Components;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob
 */
public class PhysicsComponent implements PhysicsCollisionListener {

    private final BulletAppState bullet;
    private final Vector3f spatialScale;
    private final Spatial spatial;
    private final Vector3f position;
    private final BetterCharacterControl spatialControl;
    private static float DIRECTION_LENGTH = 20;
    private SimpleApplication app;

    public PhysicsComponent(Spatial spatial, BulletAppState bull) {
        this.app = app;
        this.spatial = spatial;
        this.spatialScale = spatial.getLocalTransform().getScale();
        bullet = bull;
        this.position = spatial.getLocalTranslation();

        spatialControl =  new BetterCharacterControl(0.5f, 9f, 15);;
        setControlEnabled(true);
    }

    public void setControlEnabled(final boolean value) {
        if (value) {
            spatial.addControl(spatialControl);

            spatialControl.setGravity(new Vector3f(0, -10, 0));

            spatialControl.setJumpForce(new Vector3f(0, 30, 0));

            bullet.getPhysicsSpace().add(spatialControl);
            bullet.getPhysicsSpace().add(spatial);            
            //bullet.getPhysicsSpace().addCollisionListener(this);

        } else if (spatial.getControl(BetterCharacterControl.class) != null && bullet.getPhysicsSpace().getRigidBodyList().size() != 0) {
            spatial.removeControl(spatialControl);

            spatialControl.getPhysicsSpace().removeCollisionListener(this);
            bullet.getPhysicsSpace().remove(spatialControl);
            bullet.getPhysicsSpace().removeAll(spatial);
        }
    }

    @Override
    public void collision(PhysicsCollisionEvent collisionEvent) {
        //collisionChecking
    }

    public BetterCharacterControl getControl() {
        return this.spatialControl;
    }

}
