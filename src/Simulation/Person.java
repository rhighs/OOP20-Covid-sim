package Simulation;

<<<<<<< HEAD
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
import java.util.function.*;

public class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private boolean infected;
    private Mask mask;
    private Vector3f oldPos, pos;

    // we don't seriously need *more* interfaces...
    // Vector3f f(Vector3f);
    private Function<Vector3f, Vector3f> movementAlg;
    // void f(Person);
    private Function<Person, Boolean> infectionAlg;

    //final AssetManager assetManager, String matName,
    public Person(Node parent, AssetManager assetManager, BulletAppState bState) {
        gfx = new GraphicsComponent(this, assetManager.loadModel("Models/Ninja/Ninja.mesh.xml"), parent);
        gfx.scale(0.05f, 0.05f, 0.05f);
        gfx.rotate(0.0f, -3.0f, 0.0f);
        phyc = new PhysicsComponent(this, bState);
    }

    @Override
    public void update() {
        Vector3f newPos = movementAlg.apply(pos);
        oldPos = pos;
        pos = newPos;
        phyc.move(newPos);
        //gfx.move(newPos);
    }
    
    // i need this for debugging
    public void move(Vector3f pos) {
        phyc.move(pos);
    }
    
    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
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

    void setAlgorithms(Function<Vector3f, Vector3f> mAlg, Function<Person, Boolean> infAlg) {
        this.movementAlg = mAlg;
        this.infectionAlg = infAlg;
    }
}
