package Simulation;

import Environment.SimulationCamera;
import java.util.List;
import Environment.MainMap;
import java.util.ArrayList;
import Components.Lighting;
import Components.PathFinder;
import Environment.Locator;
import java.util.Collections;
import com.jme3.scene.Node;

public class Simulation {
    public static class Options {
        public final int numPerson;
        public final int numMasks;
        public final Person.Mask.Protection protection;

        public Options(int p, int m, Person.Mask.Protection pr) {
            numPerson = p;
            numMasks = m;
            protection = pr;
        }
    }

    private MainMap map;
    private List<Person> crowd = Collections.synchronizedList(new ArrayList<>());
    private Virus virus;
    private PathFinder pg;
    private int numPerson = 0;
    private Locator world;
    private Lighting light;
    private SimulationCamera cam;
    private PersonPicker picker;

    public Simulation(final Locator world) {
        this.world = world;
        cam = world.getSimulationCamera();
    }

    public void start(Options options) {
        this.numPerson = options.numPerson;
        this.map = world.getMap();

        this.light = new Lighting(world.getAmbient());
        this.pg = map.createPathGenerator();

        for (int i = 0; i < options.numPerson; i++) {
            Person p = new Person(world, options.protection, pg.getRandomPoint());
            if (options.numMasks != 0) {
                p.maskDown();
            }
            crowd.add(p);
        }
        picker = new PersonPicker(world.getInput(), world.getAmbient(), cam);
        this.virus = new Virus(crowd, 2);
        // virus is a thread, by the way
        virus.start();
    }

    public void step(float tpf) {
        if (crowd == null) {
            throw new IllegalStateException("simulation.step called before starting simulation");
        }
        cam.update(tpf);
        for (var p : crowd) {
            p.update(tpf);
        }
    }

    public List<Person> getPersonList() {
        return this.crowd;
    }

    public int getPersonCount() {
        if (crowd == null) {
            throw new IllegalStateException("simulation.step called before starting simulation");
        }
        return crowd.size();
    }

    public int getInfectedNumb(){
        if (crowd == null) {
            throw new IllegalStateException("simulation.step called before starting simulation");
        }
        return virus.getInfectedNumb();
    }

    public void changeMaskState(){
        virus.stopSpreading();
        for (int i = 0; i < crowd.size(); i++){
            this.crowd.get(i).switchMaskState();
        }
        
        virus.resumeSpreading();
    }

    public void resumeInfected(){
        virus.stopSpreading();
        virus.resumeInfected();
        virus.resumeSpreading();
    }

    public void setInfected(int infected) {
        virus.stopSpreading();
        for (var p : this.crowd){
            if (infected != 0 && !p.isInfected()) {
                virus.forceInfection(p);
                infected--;
            }
        }
        virus.resumeSpreading();
    }

    public Node getGuiNode(){
        return world.getGuiNode();
    }
}
