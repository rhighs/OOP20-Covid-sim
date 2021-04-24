package Simulation;

import Components.Graphics.CubeGraphicsComponent;
import Components.Graphics.GraphicsComponent;
import Components.Movement.MovementComponentContext;
import Components.Movement.MovementHandler;
import Components.Physics.PhysicsComponent;
import Environment.Locator;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Person implements Entity, Savable {
    private final GraphicsComponent gfx;
    private final PhysicsComponent phyc;
    private final MovementHandler mov;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;
    public Person(final Locator world, Mask.Protection protection, final Vector3f spawnPoint) {
        this.gfx = new CubeGraphicsComponent(world.getGraphics(), this);
        this.getSpatial().setLocalTranslation(spawnPoint);
        this.phyc = new PhysicsComponent(world.getPhysics(), this);
        this.mov = new MovementHandler(world.getMap(), this.getSpatial());
        this.phyc.initProximityBox(2);
        this.mask = new Mask(protection, Mask.Status.UP);
    }

    @Override
    public void update() {
        mov.update();
        phyc.update();
    }

    public void infect() {
        infected = true;
        gfx.changeColor(ColorRGBA.Red);
    }

    public boolean isInfected() {
        return infected;
    }

    public void heal() {
        infected = false;
        gfx.changeColor(ColorRGBA.Blue);
    }

    public Mask getMask() {
        return mask;
    }

    public void wearMask(Mask m) {
        this.mask = m;
    }

    public void maskDown() {
        mask.status = Mask.Status.DOWN;
    }

    public void switchMaskState() {
        mask.status = mask.status == Mask.Status.UP ? Mask.Status.DOWN : Mask.Status.UP;
    }

    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    @Override
    public Identificator getIdentificator() {
        return Identificator.PERSON;
    }

    //from here till eof, its all physics info related methods
    public Set<Person> getNearPeople() {
        return getNearEntities()
                .stream()
                .filter(e -> e.getIdentificator() == Identificator.PERSON)
                .map(e -> (Person) e)
                .collect(Collectors.toSet());
    }

    @Override
    public Vector3f getPosition() {
        return phyc.getPosition();
    }

    @Override
    public void setPosition(final Vector3f point) {
        phyc.setPosition(point);
    }

    public Set<Person> getLastNear() {
        return lastNearPeople;
    }

    public void setLastNear(Set<Person> people) {
        lastNearPeople = new HashSet<Person>(people);
    }

    public Set<Entity> getNearEntities() {
        return phyc.getNearEntities();
    }

    /*
     *   Unfortunately we don't need these methods, but its required to "implement" them because
     *   the Savable interface allows us to make an entity object *savable* into jme3 spatial userdata.
     *   ( see PersonPicker.pickPerson() )
     */
    @Override
    public void write(JmeExporter arg0) throws IOException {
    }

    @Override
    public void read(JmeImporter arg0) throws IOException {
    }

    public static class Mask {
        private final Protection protection;
        private Status status;

        public Mask(Protection protection, Status status) {
            this.status = status;
            this.protection = protection;
        }

        public Status getStatus() {
            return status;
        }

        public Protection getProtection() {
            return protection;
        }

        public enum Status {
            UP,
            DOWN
        }

        public enum Protection {
            FP1,
            FP2,
            FP3,
        }
    }
}
