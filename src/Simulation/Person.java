package Simulation;

import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import Engine.items.Entity;
import java.util.Collections;
import java.util.Map;

class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private boolean infected;
    private Mask mask;
    private Vector3f oldPos, pos;
    
    public Person(Node node, AssetManager assetManager, BulletAppState bullet) {       
        //gfx = new GraphicsComponent(this, null, node, assetManager);
        phyc = new PhysicsComponent(this, bullet);
    }

    public Vector3f algoritmoMovimento() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update() {
        
        Vector3f newPos = algoritmoMovimento();
        oldPos = pos;
        pos = newPos;
        phyc.move(newPos);
        //gfx.move(newPos);
    }
    
    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }
    
    public void collision() {
        if(!phyc.getCollidingEntities().equals(Collections.EMPTY_MAP)){
            Map<Entity, Float> colliding = phyc.getCollidingEntities();
            
            for(var e : colliding.entrySet()){
                switch (e.getKey().getIdentificator()) {
                    case PERSON:
                        // algoritmo infezione
                    break;
                    case WALL:
                        // move back
                    break;
                    case UNKNOWN:
                        throw new UnsupportedOperationException();
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        }
    }
    
    @Override
    public Identificator getIdentificator() {
        return Identificator.PERSON;
    }
    
    @Override
    public Mask getMask(){
        return mask;
    }
    
    @Override
    public void maskDown(){
        mask = Mask.DOWN;
    }
    
    @Override
    public boolean isInfected(){
        return infected;
    }
    
    @Override
    public void infect()
    {
        infected = true;
        phyc.setCollisionEnabled(true);
    }

}
