/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.items;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob
 */
public class Entity extends GhostControl implements PhysicsCollisionListener{
    protected Spatial spatial;
    protected Material material;
    protected Vector3f position;
    protected int id;
    
    public Entity(final Spatial spatial, final Material material, final int id){
        this.spatial = spatial;
        this.material = material;
        this.id = id;
        super.setCollisionShape(new CollisionShapeFactory().createMeshShape(this.spatial.clone().scale(2)));
    }

    public Spatial getGeometry() {
        return this.spatial;
    }

    public void rotate(final float x, final float y, final float z) {
        this.getGeometry().rotate(x, y, z);
    }

    public void setMaterial(final Material mat) {
        this.getGeometry().setMaterial(mat);
    }

    public void attachToNode(final Node n) {
        n.attachChild(this.getGeometry());
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(final Vector3f pos) {
        this.position = pos;

        this.getGeometry().setLocalTranslation(this.position);
    }

    public void move(final float x, final float y, final float z) {
        position.x += x;
        position.y += y;
        position.z += z;

        spatial.setLocalTranslation(position);
    }

    public void moveOnPlane(final float x, final float y) {
        this.move(x, this.position.y, y);
    }

    @Override
    public void collision(PhysicsCollisionEvent arg0) {
        //do something
    }
}
