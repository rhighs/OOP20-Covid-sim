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
    Thread virusThread;
    private PathFinder pg;
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
            try{
                Person p = new Person(protection, pg.getRandomPoint());
                if(noMask != 0){
                    p.maskDown();
                }
            crowd.add(p);
            }catch(Exception ex){
                Person p = new Person(Mask.MaskProtection.FP1, pg.getRandomPoint());
                if(noMask != 0){
                    p.maskDown();
                }
            crowd.add(p);
            }
            
        }
        virusThread = new Virus(crowd, 2);
        virusThread.start();
        virus = (Virus) virusThread;
        this.light = new Lighting();
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
        
        try{
            return crowd.size();
        }catch(Exception ex){
            return 0;
        }
    }
    
    public int getInfectedNumb(){
        try{
            return virus.getInfectedNumb();
        }catch(Exception ex){
            return 0;
        }
    }
    
    public void setCrowd(int n){
        virus.stopSpreading();
        for (int i=0; i<n; i++){
            this.crowd.add(new Person(protection, pg.getRandomPoint()));
        }
        virus.updateCrowd(crowd);
        virus.startSpreading();
    }
    
    public void changeMaskState(){
        virus.stopSpreading();
        for (int i=0; i<crowd.size(); i++){
            this.crowd.get(i).switchMaskState();
        }
        virus.startSpreading();
    }
    
    public void resumeInfected(){
        virus.stopSpreading();
        virus.resumeInfected();
        virus.startSpreading();
    }

    public void setInfected(int infected) {
        virus.stopSpreading();
        for (int i=0; i<infected; i++){
            Person p = new Person(protection, pg.getRandomPoint());
            this.crowd.add(p);
            virus.forceInfection(p);
        }
        virus.updateCrowd(crowd);
        virus.startSpreading();
    }
 }


