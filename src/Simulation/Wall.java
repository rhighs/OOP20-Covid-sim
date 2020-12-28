
package Simulation;

import Engine.graphics.GraphicsComponent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

class Wall implements Entity {
    Vector3f pos;
    GraphicsComponent gfx;
    physicsComponent phyc;
    

    public Wall(Vector3f pos) {
        this.pos = pos;
        phyc.move(pos);
    }
    
    public void update() {
        // no operations
    }

    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    public Identificator getIdentificator() {
        return Identificator.WALL;
    }
    
    // lol i dunno what to put here
    public void collision(Entity e) {
        switch (e.getIdentificator()) {
        case PERSON: break;
        case WALL: break;
        case UNKNOWN: break;
        default: break;
        }
    }
}