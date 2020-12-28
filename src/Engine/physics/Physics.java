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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.List;

/**
 *
 * @author rob
 */
public class Physics extends GhostControl implements PhysicsTickListener{
    private BulletAppState bullet;
    private Entity person;
    private Spatial spatial;
    private CollisionShape collisionShape;
    
    public Physics(final Entity person, final BulletAppState bullet){
        this.bullet = bullet;
        this.person = person;
        
        this.spatial = person.getSpatial();
        collisionShape = new CollisionShapeFactory().createMeshShape(spatial);
        this.setCollisionShape(collisionShape);
        add(this);
    }
    
    public void check(){
        if(this.getOverlappingCount() != 0){
            for(var person : this.getOverlappingObjects()){
                this.person.collision(((Physics)person).getEntity());
            }
        }
    }
    
    public Entity getEntity(){
        return this.person;
    }
    
    public void add(final GhostControl ghost){
        bullet.getPhysicsSpace().add(ghost);
    }
    
    public void add(final List<GhostControl> ghosts){
        for(var g : ghosts){
            bullet.getPhysicsSpace().add(e);
        }
    }

    @Override
    public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
        
    }

    @Override
    public void physicsTick(PhysicsSpace arg0, float arg1) {
        if((Person)person.isInfected()){
            check();
        }
    }
}
