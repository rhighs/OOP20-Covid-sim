package Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jme3.scene.Node;
import Environment.Services.Map.PathFinder;
import Environment.Locator;
import Environment.Services.Map.MainMap;
import Environment.Services.Graphical.SimulationCamera;
import Simulation.CrowdHandlers.PersonPicker;
import Simulation.Virus.Virus;
import Simulation.CrowdHandlers.Spawner;

/**
 * This is the main Simulation class.
 * It holds properties such as:
 * - The people list
 * - The main map, which is a map of the Unibo,
 * - The virus, which manages infections,
 * - The picker, which lets the player "pick" a person and follow it.
 * The people list is used as a synchronized list since more than one thread operates
 * on it (for example, the virus operates on it via another thread).
 */
public class SimulationImpl implements Simulation {
    private final List<Person> crowd = Collections.synchronizedList(new ArrayList<>());
    private final Locator world;
    private final SimulationCamera cam;
    private MainMap map;
    private Virus virus;
    private PathFinder pg;
    private PersonPicker picker;
    private Lights lights;

    /**
     * Constructor for simulation.
     * @param world the world this simulation uses.
     * While the simulation can be constructed immediately, it is
     * an error to use any other method before calling start().
     */
    public SimulationImpl(final Locator world) {
        this.world = world;
        this.cam = world.getSimulationCamera();
        this.lights = new Lights(world.getAmbient(), cam);
        this.picker = new PersonPicker(world.getInput(), world.getAmbient(), cam);
    }

    @Override
    public void start(Options options) {
        this.map = world.getMap();
        this.pg = map.createPathFinder();
        for (int i = 0; i < options.numPerson; i++) {
            Person p = new PersonImpl(world, options.protection, Spawner.getRandom());
            if (options.numMasks != 0) {
                p.maskDown();
            }
            // System.out.println("person is at " + p.getPosition());
            crowd.add(p);
        }
        this.virus = new Virus(crowd);
        new Thread(this.virus).start();
    }

    @Override
    public void update() {
        if (crowd == null) {
            throw new IllegalStateException("simulation.step called before starting simulation");
        }
        cam.update();
        lights.update();
        for (var p : crowd) {
            p.update();
        }
    }

    @Override
    public List<Person> getPersonList() {
        return this.crowd;
    }

    @Override
    public int getPersonCount() {
        if (crowd == null) {
            throw new IllegalStateException("simulation.getPersonCount called before starting simulation");
        }
        return crowd.size();
    }

    @Override
    public int getInfectedNumb() {
        if (crowd == null) {
            throw new IllegalStateException("simulation.getInfectedNumb called before starting simulation");
        }
        return virus.getInfectedNumb();
    }

    @Override
    public void changeMaskState() {
        virus.stopSpreading();
        for (int i = 0; i < crowd.size(); i++) {
            this.crowd.get(i).switchMaskState();
        }
        virus.resumeSpreading();
    }

    @Override
    public void resumeInfected() {
        virus.stopSpreading();
        virus.resumeInfected();
        virus.resumeSpreading();
    }

    @Override
    public void setInfected(int infected) {
        virus.stopSpreading();
        for (var p : this.crowd) {
            if (infected != 0 && !p.isInfected()) {
                virus.forceInfection(p);
                infected--;
            }
        }
        virus.resumeSpreading();
    }

    @Override
    public void shutdown() {
        if (virus != null) {
            virus.shutdown();
        }
    }
}

