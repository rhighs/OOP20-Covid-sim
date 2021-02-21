package Simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author rob
 */
public class Virus extends Thread{

    private float strenght;
    private Random rand;
    private List<Person> crowd;
    private List<Person> infectedPeople;
    private int numPeople;
    private boolean isSpreading = false;
    private InfectionImpl infectionAlgo;

    public Virus(final List<Person> crowd, final float strenght) {
        this.strenght = strenght;
        this.crowd = crowd;
        this.numPeople = crowd.size();
        infectionAlgo = new InfectionImpl();

        rand = new Random();
    }

    public void startSpreading() {
        infectedPeople = new ArrayList<>();
        var unluckyBoi = crowd.get(rand.nextInt(numPeople));
        unluckyBoi.infect();

        infectedPeople.add(unluckyBoi);
        isSpreading = true;
    }

    public void stopSprading() {
        isSpreading = false;
    }

    private void keepSpreading() {

        if (!isSpreading) {
            return;
        }

        for (var p : crowd) {
            if (p.isInfected()) {
                var nearPeople = p.getNearPeople();

                var allNearPeople = new HashSet<>(nearPeople);
                if (p.getLastNear() != null) {
                    nearPeople.removeAll(p.getLastNear());
                }

                nearPeople.forEach(person -> tryInfection(p, person));

                p.setLastNear(allNearPeople);
            }
        }

    }

    public boolean tryInfection(Person infector, Person victim) {

        if (infectionAlgo.apply(infector, victim)) {
            victim.infect();
            infectedPeople.add(victim);
            return true;
        }

        return false;
    }

    public void update(float tpf) {
        keepSpreading();
    }

    @Override
    public void run() {
        startSpreading();
        while(isSpreading){
            keepSpreading();
            try{
            Thread.sleep(10);}
            catch(Exception ex){
                
            }
        }
    }

}
