package Environment.Services.Physical;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;

/**
 * @author rob
 */
public class PhysicsImpl implements Physics {
    private final PhysicsSpace phycSpace;

    public PhysicsImpl(BulletAppState bullet) {
        this.phycSpace = bullet.getPhysicsSpace();
    }

    public void addToSpace(final Object obj) {
        phycSpace.add(obj);
    }
}
