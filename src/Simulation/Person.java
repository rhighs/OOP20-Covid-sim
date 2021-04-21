package Simulation;

import Components.GraphicsComponent;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.io.IOException;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import Components.PhysicsComponent;
import Components.MovementComponent;
import Components.ModelGraphicsComponent;
import Environment.Locator;

public class Person implements Entity, Savable {
    public static class Mask {
        public enum Status {
            UP,
            DOWN
        }
        public enum Protection {
            FP1,
            FP2,
            FP3,
        }
        private Status status;
        private Protection protection;

        public Mask(Protection protection, Status status) {
            this.status = status;
            this.protection = protection;
        }
    }

    private GraphicsComponent gfx;
    final private PhysicsComponent phyc;
    final private MovementComponent mov;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;

    public Person(final Locator world, Mask.Protection protection, final Vector3f spawnPoint) {
        this.gfx = new ModelGraphicsComponent(world.getGraphics(), this);
        this.getSpatial().setLocalTranslation(spawnPoint);
        this.phyc = new PhysicsComponent(world.getPhysics(), this);
        this.mov = new MovementComponent(world.getMap(), this.getSpatial());
        this.phyc.initProximityBox(2);
        this.mask = new Mask(protection, Mask.Status.UP);
    }

    @Override
    public CollisionShape getCollisionShape() {
        return null;
    }

    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    @Override
    public Identificator getIdentificator() {
        return Identificator.PERSON;
    }

    @Override
    public void update(float tpf) {
        mov.update(tpf);
        phyc.update();
    }

    @Override
    public void setPosition(final Vector3f point){
        phyc.setPosition(point);
    }

    @Override
    public Vector3f getPosition() {
        return phyc.getPosition();
    }

    public Mask getMask() {
        return mask;
    }

    public void wearMask(Mask m) {
        this.mask = m;
    }

    public Mask.Status getMaskStatus() {
        return mask.status;
    }

    public Mask.Protection getMaskProtection() {
        return mask.protection;
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

    public void maskDown() {
        mask.status = Mask.Status.DOWN;
    }

    public void switchMaskState(){
        mask.status = mask.status == Mask.Status.UP ? Mask.Status.DOWN : Mask.Status.UP;
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
