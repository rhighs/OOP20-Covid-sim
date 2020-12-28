/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.physics;

import Engine.items.Entityp;
import Simulation.Entity;
import Simulation.Person;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.List;

/**
 *
 * @author rob
 */
public class PhysicsComponent extends GhostControl implements PhysicsTickListener {

    private BulletAppState bullet;
    private Entity entity;
    private Spatial spatial;
    private CollisionShape collisionShape;
    private boolean isCollisionEnabled;

    public PhysicsComponent(final Entity entity, final BulletAppState bullet) {
        this.bullet = bullet;
        this.entity = entity;
        this.spatial = entity.getSpatial();
        this.collisionShape = new CollisionShapeFactory().createMeshShape(spatial);
        this.isCollisionEnabled = true;

        this.setCollisionShape(collisionShape);
        add(this);
    }

    public void check() {
        
        if (this.getOverlappingCount() != 0) {
            Entity e;
            float distance;
            Vector3f v1 = entity.getSpatial().getLocalTranslation();
            Vector3f v2;
            
            for (var collidingEntity : this.getOverlappingObjects()) {
                e = ((PhysicsComponent) collidingEntity).getEntity();
                v2 = e.getSpatial().getLocalTranslation();
                distance = v1.distance(v2);
                entity.collision(e, distance);
            }
        }
        
    }
    
    public void setCollisionEnabled(boolean enabled){
        isCollisionEnabled = enabled;
    }

    public void move(final Vector3f offset) {
        spatial.move(offset);
    }

    public void setPosition(final Vector3f position) {
        spatial.setLocalTranslation(position);
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void add(final GhostControl ghost) {
        bullet.getPhysicsSpace().add(ghost);
    }

    @Override
    public void prePhysicsTick(PhysicsSpace arg0, float arg1) {

    }

    @Override
    public void physicsTick(PhysicsSpace arg0, float arg1) {
        if(isCollisionEnabled){
            check();
        }
    }
}
