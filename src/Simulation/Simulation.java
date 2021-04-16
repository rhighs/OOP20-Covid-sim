package Simulation;

import java.util.List;
import Environment.MainMap;
import java.util.ArrayList;
import Components.Lighting;
import Components.PathFinder;

import Environment.Locator;
import com.jme3.math.Vector3f;

public class Simulation {
    private MainMap map;
    private Lighting light;
    private List<Person> crowd = null;
    private Virus virus;
    private PathFinder pg;
    private int nPerson = 0;
    private int noMask = 0;
    private Locator world;
    Mask.MaskProtection protection;

    public Simulation() {
    }

    public void start(final Locator world, int nPerson, int noMask, Mask.MaskProtection protection) {
        this.world = world;
        this.nPerson = nPerson;
        this.noMask = noMask;
        this.protection = protection;
        this.map = world.getMap();
        this.crowd = new ArrayList<>();
        
        this.pg = map.createPathGenerator();
        for (int i = 0; i < this.nPerson; i++) {
            Person p = new Person(world, protection, pg.getRandomPoint());

            if(noMask != 0){
                p.maskDown();
            }
            crowd.add(p);
            
        }
        Thread virusThread = new Virus(crowd, 2);
        virusThread.start();
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
    
    public void setCrowd(int n){
        for (int i=0; i<n; i++){
            this.crowd.add(new Person(world, protection, pg.getRandomPoint()));
        }
    }
}

