package Simulation;

import Engine.movement.MovementComponent;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import Engine.items.Entity;
import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import java.util.Collections;
import java.util.Map;
import java.util.function.*;
import Engine.Assets;
import Engine.physics.PhysicsComponent;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.app.SimpleApplication;
import java.util.List;

public class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private MovementComponent mov;
    private boolean infected;
    private Mask mask;
    // we don't seriously need *more* interfaces...
    
    Vector3f pos;

    public Person(final Spatial scene, final Vector3f spawnPoint, SimpleApplication app) {
        var bState = app.getStateManager().getState(BulletAppState.class);
        var parent = app.getRootNode();
        
        gfx = new GraphicsComponent(this, Assets.PERSON_MODEL.clone(), parent);
        getSpatial().setLocalTranslation(spawnPoint);
        phyc = new PhysicsComponent(this.getSpatial(), bState);
        mov = new MovementComponent(getSpatial(), scene, /*position*/getSpatial().getLocalTranslation());
    }
    
    public MovementComponent getMovComponents(){
        return this.mov;
    }

    /* *** Getters and setters *** */
    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    @Override
    public Identificator getIdentificator() {
        return Identificator.PERSON;
    }

    @Override
    public Mask getMask() {
        return mask;
    }

    @Override
    public void wearMask(Mask m){
        this.mask = m;
    }

    @Override
    public boolean isInfected() {
        return infected;
    }
    
    @Override
    public void infect() {
        infected = true;
    }

    public void setAlgorithms(Function<Vector3f, Vector3f> mAlg, Function<Person, Boolean> infAlg) {
    }

    /* *** Actual member functions *** */
    @Override
    public void update(float tpf) {
        //nothing lol
    }

    public void collision() {
        // only call getCollidingEntities once
    }

    @Override
    public void maskDown(){
        this.mask.maskDown();
    }

    @Override
    public void setPosition(Vector3f pos) {
        //phyc.setPosition(pos);
    }

    @Override
    public Vector3f getPosition() {
        return getSpatial().getLocalTranslation();
    }
}

