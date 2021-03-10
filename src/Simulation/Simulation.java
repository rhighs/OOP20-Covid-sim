package Simulation;

import java.util.List;
import java.util.ArrayList;
import com.jme3.bullet.BulletAppState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.renderer.ViewPort;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.Lightning;

public class Simulation {
    public static final int NUM_PERSON = 1;
    final MainMap map;
    List<Person> crowd;
    Virus virus;
    PathCalculator pathCalculator;
    PathGenerator pg;
    Lightning light;

    public Simulation(AssetManager assetManager, BulletAppState bState, Node rootNode, ViewPort viewport) {
        this.map = new MainMap(assetManager, bState, rootNode);
        this.crowd = new ArrayList<>();
        this.pathCalculator = map.createPathCalculator();
        this.pg = map.createPathGenerator();
        for (int i = 0; i < NUM_PERSON; i++) {
            Person p = new Person(pg.getRandomPoint(), bState, rootNode, this.pathCalculator, assetManager);
            crowd.add(p);
        }
        Thread virusThread = new Virus(crowd, 2);
        virusThread.start();
        this.light = new Lightning(assetManager, rootNode, viewport);
    }

    public void step(float tpf) {
        for (var p: crowd) {
            p.update(tpf);
        }
    }
}

