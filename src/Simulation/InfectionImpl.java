package Simulation;

import java.util.Random;
import java.util.function.Function;
/**
 *
 * @author simon
 */
public class InfectionImpl implements Function<Person, Boolean> {
    public Boolean apply(Person p) {
        if (!p.isInfected() && p.getMask() == Person.Mask.DOWN) {
            //p.infect();
            return true;
        } else {
            return maskInfection(p);
        }
    }

    //infection depends from Mask status
    private boolean maskInfection(Person p){
        //bad
        Random rand = new Random(); //instance of random class
        final int upperbound = 101;
        //case mask DOWN
        if (p.getMask() == Person.Mask.DOWN){
            //probability of 70% to get infected
            if (rand.nextInt(upperbound) <= 70 ) {
                //p.infect();
                return true;
            }
        //case mask is UP
        } else {
            //probability of 30% to get Infected 
            if (rand.nextInt(upperbound) > 70 ) {
                p.infect();
                return true;
            }
        }
        return false;
    }
}
