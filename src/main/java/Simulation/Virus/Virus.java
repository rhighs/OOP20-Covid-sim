package Simulation.Virus;

import Simulation.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author rob
 */
public class Virus extends Thread {

    private final Random rand;

    private int numPeople;

    private final Infection infectionAlgo;

    private volatile List<Person> crowd;

    private List<Person> infectedPeople;

    private boolean isSpreading = false;

    public Virus(final List<Person> crowd) {
        this.crowd = crowd;
        infectionAlgo = new Infection();
        rand = new Random();
    }

    private void startSpreading() {
        this.numPeople = crowd.size();
        infectedPeople = new ArrayList<>();
        Person unluckyBoi = crowd.get(rand.nextInt(numPeople));
        unluckyBoi.infect();

        infectedPeople.add(unluckyBoi);
        isSpreading = true;
    }

    private void keepSpreading() {

        if (!isSpreading) {
            return;
        }

        for (Person p : crowd) {

            if (p.isInfected()) {
                var nearPeople = p.getNearPeople();
                var allNearPeople = new HashSet<>(nearPeople);

                if (p.getLastNear() != null) {
                    nearPeople.removeAll(p.getLastNear());
                }

                nearPeople.forEach(person -> tryInfection(person));
                p.setLastNear(allNearPeople);
            }
        }

    }

    public boolean tryInfection(Person victim) {

        if (infectionAlgo.apply(victim)) {
            victim.infect();
            infectedPeople.add(victim);
            return true;
        }

        return false;
    }

    public void forceInfection(Person victim) {
        victim.infect();
        infectedPeople.add(victim);
    }

    public void resumeInfected() {
        this.infectedPeople.forEach(i -> i.heal());
        this.infectedPeople.clear();
        //resume to 1 infected
        this.forceInfection(crowd.get(0));
    }

    @Override
    public void run() {
        startSpreading();
        while (isSpreading) {
            keepSpreading();
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
                //if .sleep somehow fails, just ingore and retry.
            }
        }
    }

    public void stopSpreading() {
        isSpreading = false;
    }

    public void resumeSpreading() {
        isSpreading = true;
    }

    public int getInfectedNumb() {
        return infectedPeople.isEmpty() ? 0 : infectedPeople.size();
    }

    public void updateCrowd(List<Person> p) {
        this.crowd = p;
    }

    public List<Person> getCrowd() {
        return crowd;
    }

    public void update(float tpf) {
        keepSpreading();
    }

}
