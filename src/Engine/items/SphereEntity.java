package Engine.items;

import Engine.items.Entity;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author rob
 */
public class SphereEntity extends Entity {
    private Sphere sphere;
    
    public SphereEntity(final Sphere sphere, final Material material, final int id){
        super(new Geometry(null, sphere), material, id);
        this.sphere = sphere;
    }
    
    public SphereEntity(final Sphere sphere, final Material material, final Vector3f position, final int id){
        super(new Geometry(null, sphere), material, id);
        super.setPosition(position);
    }
    
    public Sphere getSphere() {
        return sphere;
    }
}
