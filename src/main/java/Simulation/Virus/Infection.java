package Simulation.Virus;

import Simulation.Person;

import java.util.Random;
import java.util.function.Function;

/**
 * A class that models an infection algorithm.
 * It takes on input the victim, and returns whether to
 * infect the victim or not.
 * note: in the beginning there were supposed to be more infection algorithm,
 * but we ran out of time.
 */
public class Infection implements Function<Person, Boolean> {
    private static final int STATUS_PERC = 70;
    private static final int FP1_PERC = 30;
    private static final int FP2_PERC = 20;
    private static final int FP3_PERC = 10;

    /**
     * Run infection algorithm.
     * This specific algorithm doesn't take care of the infector
     * and only looks at the victim.
     */
    @Override
    public Boolean apply(Person victim) {
        if (victim.isInfected()) {
            return false;
        }

        int trasm = checkTrasmissibility(victim);
        var rand = new Random();
        final int upperbound = 101;
        return rand.nextInt(upperbound) <= trasm;
    }

    /**
     * Check trasmissibility of a person.
     * Trasmissibility only depends on the mask.
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
