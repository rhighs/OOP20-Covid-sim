/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.physics;

import Engine.items.Entity;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import java.util.List;

/**
 *
 * @author rob
 */
public class Physics implements PhysicsTickListener{
    private BulletAppState bullet;
    
    public Physics(BulletAppState bullet){
        this.bullet = bullet;
    }
    
    public void add(final List<Entity> entities){
        
    }

    @Override
    public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void physicsTick(PhysicsSpace arg0, float arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
