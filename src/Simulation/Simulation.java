package Simulation;

import Environment.SimulationCamera;
import java.util.List;
import Environment.MainMap;
import java.util.ArrayList;
import Components.Lighting;
import Components.PathFinder;
import Environment.Locator;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Collections;

public class Simulation {
    private MainMap map;
    private List<Person> crowd = Collections.synchronizedList(new ArrayList<>());
    private Virus virus;
    private Thread virusThread;
    private PathFinder pg;
    private int nPerson = 0;
    private int noMask = 0;
    private Locator world;
    private Lighting light;
    private SimulationCamera cam;
    private PersonPicker picker;
    
    Mask.MaskProtection protection;

    public Simulation(final Locator world) {
        this.world = world;
        cam = world.getSimulationCamera();
        
    }

    public void start(int nPerson, int noMask, Mask.MaskProtection protection){
        this.nPerson = nPerson;
        this.noMask = noMask;
        this.protection = protection;
        this.map = world.getMap();
        this.light = new Lighting(world.getAmbient());
        
        this.pg = map.createPathGenerator();
        for (int i = 0; i < this.nPerson; i++) {
            Person p = new Person(world, protection, pg.getRandomPoint());

            if(noMask != 0){
                p.maskDown();
            }
            
            crowd.add(p);
        }
        
        picker = new PersonPicker(world.getInput(), world.getAmbient(), cam);

        virusThread = new Virus(crowd, 2);
        virus = (Virus) virusThread;
        virusThread.start();
    }

    public void step(float tpf) {
        cam.update(tpf);
        
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

        for(var i : this.crowd){
            if(infected != 0){
                if(!i.isInfected()){
                    virus.forceInfection(i);
                    infected--;
                }
            
            }
            
        }
        virus.resumeSpreading();
    }
    
    public Node getGuiNode(){
        return world.getGuiNode();
    }
 }


