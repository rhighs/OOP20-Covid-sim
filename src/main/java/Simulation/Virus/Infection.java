package Simulation.Virus;

import Simulation.Person;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * A class that models an infection algorithm.
 * It takes on input the infector and the victim, and returns whether to
 * infect the victim.
 * note: in the beginning there were supposed to be more infection algorithm,
 * but we ran out of time.
 */
public class Infection implements BiFunction<Person, Person, Boolean> {
    private static final int INF_RADIUS = 5;
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
    public Boolean apply(Person infector, Person victim) {
        if (victim.isInfected()) {
            return false;
        }
        int trasm = checkTrasmissibility(victim);
        if (trasm == 100) {
            return true;
        }
        var rand = new Random();
        final int upperbound = 101;
        return rand.nextInt() <= trasm;
    }

    /**
     * Check trasmissibility of a person.
     * Trasmissibility only depends on the mask.
     */
    private int checkTrasmissibility(Person p) {
        var protection = p.getMask().getProtection();
        var status = p.getMask().getStatus();
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
