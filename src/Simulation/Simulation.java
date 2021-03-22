package Simulation;

import java.util.List;
import java.util.ArrayList;
import com.jme3.bullet.BulletAppState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.renderer.ViewPort;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.Lighting;

public class Simulation {
    private MainMap map;
    private List<Person> crowd = null;
    private Virus virus;
    private PathCalculator pathCalculator;
    private PathGenerator pg;
    private Lighting light;
    private int nPerson = 0;

    public Simulation() {
    }
    
    public void start(int nPerson, AssetManager assetManager, BulletAppState bState, Node rootNode, ViewPort viewport) {
        this.nPerson = nPerson;
        this.map = new MainMap(assetManager, bState, rootNode);
        this.crowd = new ArrayList<>();
        this.pathCalculator = map.createPathCalculator();
        this.pg = map.createPathGenerator();
        for (int i = 0; i < this.nPerson; i++) {
            Person p = new Person(pg.getRandomPoint(), bState, rootNode, this.pathCalculator, assetManager);
            crowd.add(p);
        }
        Thread virusThread = new Virus(crowd, 2);
        virusThread.start();
        this.light = new Lighting(assetManager, rootNode, viewport);
    }

    public void step(float tpf) {
        if (crowd == null) {
            return;
        }
        
        for (var p : crowd) {
            p.update(tpf);
        }
    }

    public List<Person> getPersonList() {
        return this.crowd;
    }

    public int getPersonCount() {
        return nPerson;
    }
}

