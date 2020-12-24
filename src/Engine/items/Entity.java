package Engine.items;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob, chris
 */
public class Entity extends GhostControl implements PhysicsCollisionListener {
    protected int id;
    protected String name;
    protected Spatial spatial;
    protected Material material;
    protected Vector3f position;
    
    public Entity(final int id, final String name, final Spatial spatial, final Material material){
        this.name     = name;
        this.spatial  = spatial;
        this.material = material;
        this.id       = id;
        super.setCollisionShape(new CollisionShapeFactory().createMeshShape(this.spatial.clone().scale(2)));
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(final Vector3f pos) {
        position = pos;
        spatial.setLocalTranslation(this.position);
    }

    public void setParent(final Node parent) {
        parent.attachChild(spatial);
    }

    public void move(final float x, final float y, final float z) {
        position.x += x;
        position.y += y;
        position.z += z;
        spatial.setLocalTranslation(position);
    }
    
    public void moveOnPlane(final float x, final float y) {
        this.move(x, position.y, y);
    }

    public void rotate(final float x, final float y, final float z) {
        spatial.rotate(x, y, z);
    }
    
    public void collision(PhysicsCollisionEvent event){
    }
}
