package Simulation;

import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

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
    private Infection infectionAlgo;

    public Virus(final List<Person> crowd, final float strenght) {
        this.strenght = strenght;
        this.crowd = crowd;
        this.numPeople = crowd.size();
        infectionAlgo = new Infection();

        rand = new Random();
    }

    public void startSpreading() {
        infectedPeople = new ArrayList<>();
        Person unluckyBoi = crowd.get(rand.nextInt(numPeople));
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

        for (Person p : crowd) {
            if (p.isInfected()) {
                Set<Person> nearPeople = p.getNearPeople();

                Set<Person> allNearPeople = new HashSet<Person>(nearPeople);
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
    
    public int getInfectedNumb(){
        return infectedPeople.isEmpty() ? 0 : infectedPeople.size();
    }

    public void update(float tpf) {
        keepSpreading();
    }

    @Override
    public void run() {
        startSpreading();
        while (isSpreading){
            keepSpreading();
            try { Thread.sleep(10); } catch(Exception ex) { }
        }
    }

}
