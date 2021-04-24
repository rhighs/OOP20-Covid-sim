package Simulation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import Components.Graphics.CubeGraphicsComponent;
import Components.Graphics.GraphicsComponent;
import Components.Movement.MovementHandler;
import Components.Movement.MovementHandlerImpl;
import Components.Physics.PhysicsComponent;
import Components.Physics.PhysicsComponentImpl;
import Environment.Locator;

/**
 * The person class represent a person inside the simulation.
 * A person doesn't hold too much data: Almost all of it is inside
 * person components, which implement the real logic instead.
 * A person also holds the kind of mask it is wearing and an infected flag.
 * It also hold a set of the people who are near him.
 */
public class PersonImpl implements Person, Savable {
    private final GraphicsComponent gfx;
    private final PhysicsComponent phyc;
    private final MovementHandler mov;
    private Set<Person> lastNearPeople;
    private boolean infected;
    private Mask mask;

    /**
     * @param world the world this person is in
     * @param protection the kind of protection this person will have for his mask
     * @param spawnPoint the point the person will spawn inside the world.
     */
    public PersonImpl(final Locator world, Mask.Protection protection, final Vector3f spawnPoint) {
        this.gfx = new CubeGraphicsComponent(world.getGraphics(), this);
        this.getSpatial().setLocalTranslation(spawnPoint);
        this.phyc = new PhysicsComponentImpl(world.getPhysics(), this);
        this.mov = new MovementHandlerImpl(world.getMap(), this.getSpatial());
        this.phyc.initProximityBox(2);
        this.mask = new Mask(protection, Mask.Status.UP);
    }

    @Override
    public void update() {
        mov.update();
        phyc.update();
    }

    @Override
    public Spatial getSpatial() {
        return gfx.getSpatial();
    }

    @Override
    public Vector3f getPosition() {
        return phyc.getPosition();
    }

    @Override
    public void setPosition(final Vector3f point) {
        phyc.setPosition(point);
    }

    @Override
    public ID getID() {
        return ID.PERSON;
    }

    @Override
    public void infect() {
        infected = true;
        gfx.changeColor(ColorRGBA.Red);
    }

    @Override
    public boolean isInfected() {
        return infected;
    }

    @Override
    public void heal() {
        infected = false;
        gfx.changeColor(ColorRGBA.Blue);
    }

    @Override
    public Mask getMask() {
        return mask;
    }

    @Override
    public void maskDown() {
        mask.setStatus(Mask.Status.DOWN);
    }

    @Override
    public void switchMaskState() {
        mask.setStatus(mask.getStatus() == Mask.Status.UP ? Mask.Status.DOWN : Mask.Status.UP);
    }

    @Override
    public Set<Person> getAdjacentPeople() {
        return phyc.getNearEntities()
                .stream()
                .filter(e -> e.getID() == ID.PERSON)
                .map(e -> (Person) e)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Person> getLastAdjacentPeople() {
        return lastNearPeople;
    }

    @Override
    public void setLastAdjacentPeople(Set<Person> people) {
        lastNearPeople = new HashSet<Person>(people);
    }

    /*
     * Unfortunately we don't need these methods, but its required to "implement" them because
     * the Savable interface allows us to make an entity object *savable* into jme3 spatial userdata.
     * ( see PersonPicker.pickPerson() )
     */
    @Override
    public void write(JmeExporter arg0) throws IOException {
    }

    @Override
    public void read(JmeImporter arg0) throws IOException {
    }
}
