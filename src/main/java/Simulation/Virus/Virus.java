package Simulation.Virus;

import Simulation.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author rob
 */
public class Virus implements VirusInterface {

    private final float strenght;
    private final Random rand;
    private final int numPeople;
    private final Infection infectionAlgo;
    private volatile List<Person> crowd;
    private List<Person> infectedPeople;
    private boolean running = true;
    private boolean isSpreading = false;

    public Virus(final List<Person> crowd, final float strenght) {
        this.strenght = strenght;
        this.crowd = crowd;
        this.numPeople = crowd.size();
        this.infectionAlgo = new Infection();
        this.rand = new Random();
    }

    @Override
    public void run() {
        startSpreading();
        while (running) {
            if (isSpreading) {
                keepSpreading();
            }
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
                //if .sleep somehow fails, just ingore and retry.
            }
        }
    }

    public void shutdown() {
        running = false;
    }

    private void startSpreading() {
        infectedPeople = new ArrayList<>();
        Person unluckyBoi = crowd.get(rand.nextInt(numPeople));
        unluckyBoi.infect();
        infectedPeople.add(unluckyBoi);
        isSpreading = true;
    }

    private void keepSpreading() {
        for (Person p : crowd) {
            if (!p.isInfected()) {
                continue;
            }
            var adjPeople = p.getAdjacentPeople();
            var allAdjPeople = new HashSet<>(adjPeople);
            var lastPeople = p.getLastAdjacentPeople();
            if (lastPeople != null) {
                adjPeople.removeAll(lastPeople);
            }
            adjPeople.forEach(person -> tryInfection(p, person));
            p.setLastAdjacentPeople(allAdjPeople);
        }
    }

    private boolean tryInfection(Person infector, Person victim) {
        if (infectionAlgo.apply(infector, victim)) {
            victim.infect();
            infectedPeople.add(victim);
            return true;
        }
        return false;
    }

    @Override
    public void forceInfection(Person victim) {
        victim.infect();
        infectedPeople.add(victim);
    }

    @Override
    public void resumeInfected() {
        this.infectedPeople.forEach(i -> i.heal());
        this.infectedPeople.clear();
        this.forceInfection(crowd.get(0));
    }

    @Override
    public void stopSpreading() {
        isSpreading = false;
    }

    @Override
    public void resumeSpreading() {
        isSpreading = true;
    }

    @Override
    public int getInfectedNumb() {
        return infectedPeople.isEmpty() ? 0 : infectedPeople.size();
    }

    @Override
    public void updateCrowd(List<Person> p) {
        this.crowd = p;
    }
}
