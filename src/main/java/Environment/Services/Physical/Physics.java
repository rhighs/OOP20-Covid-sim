package Environment.Services.Physical;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;

/**
 * @author rob
 */
public class Physics {
    private final PhysicsSpace phycSpace;

    public Physics(BulletAppState bullet) {
        this.phycSpace = bullet.getPhysicsSpace();
    }

    public void addToSpace(final Object obj) {
        phycSpace.add(obj);
    }
}
