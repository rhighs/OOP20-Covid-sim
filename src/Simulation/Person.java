package Simulation;

import Engine.movement.MovementComponent;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import Engine.items.Entity;
import Engine.graphics.GraphicsComponent;
import Engine.physics.PhysicsComponent;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.Map;
import java.util.function.*;

public class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private MovementComponent mov;
    private boolean infected;
    private Mask mask;
    // we don't seriously need *more* interfaces...
    // Vector3f f(Vector3f);
    private Function<Vector3f, Vector3f> movementAlg = null;
    // boolean f(Person);
    private Function<Person, Boolean> infectionAlg = null;
    Vector3f pos;

    public Person(Node parent, AssetManager assetManager, BulletAppState bState) {
        final String model = "Models/Ninja/Ninja.mesh.xml";
        gfx = new GraphicsComponent(this, assetManager.loadModel(model), parent);
        gfx.scale(0.3f, 0.3f, 0.3f);
        phyc = new PhysicsComponent(this, bState);
        phyc.setPosition(new Vector3f(1, -10, 1));
        
        mov = new MovementComponent(this.getSpatial(), phyc.getPhysicsLocation(), new Rectangle(20, 20));
        mov.randomMove(100);
        mov.getPath().enableDebugShape(assetManager, parent);
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

    void setAlgorithms(Function<Vector3f, Vector3f> mAlg, Function<Person, Boolean> infAlg) {
        this.movementAlg = mAlg;
        this.infectionAlg = infAlg;
    }

    /* *** Actual member functions *** */
    @Override
    public void update(float tpf) {
        // Vector3f newPos = movementAlg.apply(phyc.getPosition());
        // phyc.setPosition(movementAlg.apply(phyc.getPosition()));
        phyc.move(new Vector3f(0, 10*tpf, 0));
    }

    public void collision() {
        // only call getCollidingEntities once
        Map<Entity, Float> colliding = phyc.getCollidingEntities();
        if (colliding.equals(Collections.EMPTY_MAP)) {
            return;
        }
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

    @Override
    public void maskDown(){
        this.mask.maskDown();
    }

    @Override
    public void setPosition(Vector3f pos) {
        phyc.setPosition(pos);
    }
}

