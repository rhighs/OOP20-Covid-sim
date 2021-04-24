package Simulation.Virus;

import Simulation.Person;

import java.util.Random;
import java.util.function.Function;

/**
 * @author Json, rob
 */
public class Infection implements Function<Person, Boolean> {
    private static final int STATUS_PERC = 70;
    private static final int FP1_PERC = 30;
    private static final int FP2_PERC = 20;
    private static final int FP3_PERC = 10;

    @Override
    public Boolean apply(Person victim) {
        return infection(victim);
    }

   public boolean infection(Person person) {
        if (person.isInfected()) {
            return false;
        }

        int trasm = checkTrasmissibility(person);
        if (trasm == 100) {
            return true;
        }
        // più è alto il parametro di trasmissione più è alta la probabilità di infettare
        var rand = new Random();
        final int upperbound = 101;
        return rand.nextInt() <= trasm;
    }

    /*
     *  infection depends from Mask status and type
     *  mask status 70% mask type 30%
     */
    public int checkTrasmissibility(Person person) {
        var protection = person.getMask().getProtection();
        var status = person.getMask().getStatus();
        int perc = 0;

        perc += status == Person.Mask.Status.UP ? 0 : STATUS_PERC;
        switch (protection) {
            case FP1:
                perc += FP1_PERC;
                break;
            case FP2:
                perc += FP2_PERC;
                break;
            case FP3:
                perc += FP3_PERC;
                break;
        }

        return perc;
    }

}
