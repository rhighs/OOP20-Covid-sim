package Simulation;

import Environment.MainMap;
import java.util.List;
import java.util.ArrayList;
import com.jme3.bullet.BulletAppState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.renderer.ViewPort;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.Lighting;

import Environment.Locator;

public class Simulation {
    private MainMap map;
    private List<Person> crowd = null;
    private Virus virus;
    private PathGenerator pg;
    private int nPerson = 0;
    private int noMask = 0;
    Mask.MaskProtection protection;

    public Simulation() {
    }

    public void start(int nPerson, int noMask, Mask.MaskProtection protection) {
        this.nPerson = nPerson;
        this.noMask = noMask;
        this.protection = protection;
        this.map = Locator.getMap();
        this.crowd = new ArrayList<>();
        
        this.pg = map.createPathGenerator();
        for (int i = 0; i < this.nPerson; i++) {
            Person p = new Person(protection, pg.getRandomPoint());
            if(noMask != 0){
                p.maskDown();
            }
            crowd.add(p);
        }
        Thread virusThread = new Virus(crowd, 2);
        virusThread.start();
        new Lighting();
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
    
    public int getInfectedNumb(){
        try{
            return virus.getInfectedNumb();
        }catch(NullPointerException ex){
            return 0;
        }
    }
}

