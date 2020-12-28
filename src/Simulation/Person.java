package Simulation;

import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import com.jme3.math.Vector3f;

public class Person implements Entity {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private boolean infected;
    private Vector3f oldPos, pos;

    public Person() {
        gfx = new GraphicsComponent(this);
        phyc = new PhysicsComponent(this);
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

    public void collision(final Entity e, final float distance) {
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
    
    public boolean isInfected(){
        return infected;
    }
}