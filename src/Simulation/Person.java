package Simulation;

import java.util.Set;
import java.util.HashSet;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import Components.PhysicsComponent;
import java.util.stream.Collectors;
import Components.MovementComponent;
import Components.GraphicsComponent;
import Environment.Locator;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

public class Person implements Entity, Savable {
    final private GraphicsComponent gfx;
    final private PhysicsComponent phyc;
    final private MovementComponent mov;
    final private Locator world;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Vector3f pos;
    private Mask mask;
    
    public Person(final Locator world, Mask.MaskProtection protection, final Vector3f spawnPoint) {
        this.world = world;
        gfx = new GraphicsComponent(world.getGraphics(), this);
        this.getSpatial().setLocalTranslation(spawnPoint);
        phyc = new PhysicsComponent(world.getPhysics(), this);
        mov = new MovementComponent(world.getMap(), this.getSpatial());
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
    
    public void heal(){
        infected = false;
        gfx.changeColor(ColorRGBA.Blue);
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
    
    public void switchMaskState(){
        if(this.mask.getStatus().equals(Mask.MaskStatus.UP)){
            mask.maskDown();
        }else{
            mask.maskUp();
        }
    }
    
    @Override
    public void setPosition(final Vector3f point){
        phyc.setPosition(point);
    }

    @Override
    public Vector3f getPosition() {
        return phyc.getPosition();
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
    public void write(JmeExporter arg0) throws IOException {
    }

    @Override
    public void read(JmeImporter arg0) throws IOException {
    }
}
