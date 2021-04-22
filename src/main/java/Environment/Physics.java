package Environment;

import java.util.Collection;
import com.jme3.scene.Spatial;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.objects.PhysicsRigidBody;

/**
 *
 * @author rob
 */
public class Physics {
    private BulletAppState bullet;
    private PhysicsSpace phycSpace;
    
    public Physics(BulletAppState bullet){
        this.bullet = bullet;
        this.phycSpace = bullet.getPhysicsSpace();
    }
    
    public void addToSpace(final Object obj){
        phycSpace.add(obj);
    }
    
    public void removeFromSpace(final Object obj){
        phycSpace.remove(obj);
    }
    
    public void removeFromSpace(final Spatial spatial){
        phycSpace.removeAll(spatial);
    }
    
    public Collection<PhysicsRigidBody> getRBodies(){
        return phycSpace.getRigidBodyList();
    }
    
}