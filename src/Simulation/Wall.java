package Simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.bullet.BulletAppState;
import Engine.items.Entity;
import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import Engine.graphics.GraphicsMeshComponent;

class Wall implements Entity {
    GraphicsMeshComponent gfx;
    PhysicsComponent phyc;

    public Wall(Vector3f pos, BulletAppState bState) {
        // we must create a graphicscomponent somehow!
        // and this must be done before the creation of the physicsComponent
        phyc = new PhysicsComponent(this, bState);
        setPosition(pos);
    }
    
    public void update(float tpf) {
        // no operations
    }

    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    public Identificator getIdentificator() {
        return Identificator.WALL;
    }

    public void collision() {
        // lol i dunno what to put here
    }

    @Override
    public void setPosition(Vector3f pos) {
        phyc.setPosition(pos);
    }
}
