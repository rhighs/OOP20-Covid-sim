package Simulation;

import com.jme3.scene.Spatial;

class Wall implements Entity {
    Vector3f pos;
    GraphicsComponent gfx;

    public Wall(Vector3f pos) {
        this.pos = pos;
        gfx.move(pos);
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
