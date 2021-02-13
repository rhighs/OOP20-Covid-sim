package Simulation;

import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.app.SimpleApplication;
import Components.MovementComponent;
import Components.GraphicsComponent;
import Components.PhysicsComponent;
import java.util.List;

public class Person implements Entity, IPerson {
    private GraphicsComponent gfx;
    private PhysicsComponent  phyc;
    private MovementComponent mov;
    private boolean infected;
    private Mask mask;
    Vector3f pos;

    public Person(final Spatial scene, final Vector3f spawnPoint, SimpleApplication app) {
        var bState = app.getStateManager().getState(BulletAppState.class);
        var parent = app.getRootNode();

        gfx = new GraphicsComponent(this, Assets.PERSON_MODEL.clone(), parent);
        getSpatial().setLocalTranslation(spawnPoint);
        phyc = new PhysicsComponent(this, bState);
        mov = new MovementComponent(
                getSpatial(),
                scene
                // getSpatial().getLocalTranslation()
        );
        mov.startPathFollower();
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
    
    public void setInfectionDistance(float distance){
        phyc.initProximityBox(distance);
    }

    /* *** Actual member functions *** */
    @Override
    public void update(float tpf) {
        mov.update(tpf);
    }

    public void collision() {
    }

    @Override
    public void maskDown(){
        mask.maskDown();
    }

    @Override
    public void setPosition(Vector3f pos) {
    }

    @Override
    public Vector3f getPosition() {
        return getSpatial().getLocalTranslation();
    }
    
    public List<Entity> getNearEntities(){
        return phyc.getNearEntities();
    }
}

