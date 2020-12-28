package Simulation;

import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import com.jme3.scene.Spatial;

public class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private Vector3f oldPos, pos;
    private boolean infected;
    private Mask mask;

    
    public Person(Node node, AssetManager assetManager, BulletAppState bullet){       
        gfx = new GraphicsComponent(this, null, node, assetManager);
        phyc = new PhysicsComponent(this, bullet);
    }

    public Vector3f algoritmoMovimento() {
        throw new UnsupportedOperationException();
    }
    
    public void update() {
        Vector3f newPos = algoritmoMovimento();
        oldPos = pos;
        pos = newPos;
        phyc.move(newPos);
    }
    
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }
    
    public void collision(Entity e, float distance) {
        switch (e.getIdentificator()) {
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
    
    public Identificator getIdentificator() {
        return Identificator.PERSON;
    }
    
    public Mask getMask(){
        return mask;
    }
    
    public void maskDown(){
        mask = Mask.DOWN;
    }
    
    public boolean isInfected(){
        return infected;
    }
    
    public void infect(){
        infected = true;
    }
}