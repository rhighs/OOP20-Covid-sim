package Simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import Components.MovementComponent;
import Components.GraphicsComponent;
import Components.PhysicsComponent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.ColorRGBA;
import com.jme3.export.Savable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Person implements Entity, Savable {

    final private GraphicsComponent gfx;
    final private PhysicsComponent phyc;
    final private MovementComponent mov;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;
    Vector3f pos;

    //public Person(final Vector3f spawnPoint, BulletAppState bState, Node rootNode, PathCalculator pathCalc, AssetManager assetManager) {
    public Person(/*final Spatial scene,*/Mask.MaskProtection protection, final Vector3f spawnPoint) {
        gfx = new GraphicsComponent(this);
        this.getSpatial().setLocalTranslation(spawnPoint);
        phyc = new PhysicsComponent(this);
        mov = new MovementComponent(getSpatial());
        phyc.initProximityBox(2);
        //default
        this.wearMask(new Mask(protection, Mask.MaskStatus.UP));
    }
       
    public CollisionShape getCollisionShape() {
        return null;
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

    public Mask getMask() {
        return mask;
    }
    
    public void wearMask(Mask m) {
        this.mask = m;
    }
    
    public boolean isInfected() {
        return infected;
    }

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

    @Override
    public void write(JmeExporter arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void read(JmeImporter arg0) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
