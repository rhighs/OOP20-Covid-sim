package Simulation;

import com.jme3.math.Vector3f;

class Person implements Entity {
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
        gfx.move(newPos);
    }
    
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    public void collision(Entity e) {
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
}
