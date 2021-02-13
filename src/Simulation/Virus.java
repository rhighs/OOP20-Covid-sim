package Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author rob
 */
public class Virus {

    private float strenght;
    private Random rand;
    private List<Person> crowd;
    private List<Person> infectedPeople;
    private int numPeople;
    private boolean isSpreading = false;

    public Virus(final List<Person> crowd, final float strenght) {
        this.strenght = strenght;
        this.crowd = crowd;
        this.numPeople = crowd.size();

        rand = new Random();
    }

    public void startSpreading() {
        infectedPeople = new ArrayList<>();
        var unluckyBoi = crowd.get(rand.nextInt(numPeople));
        unluckyBoi.infect();

        infectedPeople.add(unluckyBoi);
        isSpreading = true;
    }

    public boolean tryInfection(Person infector, Person victim) {
        //...todo attempts inf. based on certain params

        victim.infect();
        this.infectedPeople.add(victim);
        return true;
    }

    public void update(float tpf) {
        keepSpreading();
    }
    
    public void stopSprading(){
        isSpreading = false;
    }

    private void keepSpreading() {

        if (!isSpreading) return;

        for (var inf : infectedPeople) {
            var entityPeople = inf.getNearEntities()
                    .stream()
                    .filter(e -> e.getIdentificator() == Entity.Identificator.PERSON)
                    .collect(Collectors.toList());

            var nearPeople = crowd
                    .stream()
                    .filter(person -> entityPeople.contains(person))
                    .collect(Collectors.toList());

            nearPeople.forEach(p -> tryInfection(inf, p));
        }

    }

}
