package Simulation;

import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.app.SimpleApplication;
import Components.MovementComponent;
import Components.GraphicsComponent;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.PhysicsComponent;
import com.jme3.math.ColorRGBA;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Person implements Entity, IPerson {

    private GraphicsComponent gfx;
    private PhysicsComponent phyc;
    private MovementComponent mov;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;
    Vector3f pos;

    public Person(final Spatial scene, final Vector3f spawnPoint, SimpleApplication app, PathCalculator pathCalc) {
        var bState = app.getStateManager().getState(BulletAppState.class);
        var parent = app.getRootNode();

        gfx = new GraphicsComponent(this, parent);
        getSpatial().setLocalTranslation(spawnPoint);
        phyc = new PhysicsComponent(this, bState);
        mov = new MovementComponent(getSpatial(), scene, pathCalc);

        phyc.initProximityBox(2);

        wearMask(new MaskImpl(Mask.MaskProtection.FFP3, Mask.MaskStatus.UP));
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
    public void wearMask(Mask m) {
        this.mask = m;
    }

    @Override
    public boolean isInfected() {
        return infected;
    }

    @Override
    public void infect() {
        infected = true;
        gfx.changeColor(ColorRGBA.Red);
    }

    public void setLastNear(Set<Person> people) {
        lastNearPeople = new HashSet<Person>(people);
    }

    public Set<Person> getLastNear() {
        return lastNearPeople;
    }

    public void setInfectionDistance(float distance) {
        phyc.initProximityBox(distance);
    }

    /* *** Actual member functions *** */
    @Override
    public void update(float tpf) {
        mov.update(tpf);
        phyc.update();
    }

    @Override
    public void maskDown() {
        mask.maskDown();
    }

    @Override
    public void setPosition(Vector3f pos) {
    }

    @Override
    public Vector3f getPosition() {
        return getSpatial().getLocalTranslation();
    }

    public Set<Entity> getNearEntities() {
        return phyc.getNearEntities();
    }

    public Set<Person> getNearPeople() {
        return getNearEntities()
                .stream()
                .filter(e -> e.getIdentificator() == Entity.Identificator.PERSON)
                .map(e -> (Person) e)
                .collect(Collectors.toSet());
    }

    @Override
    public void collision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
