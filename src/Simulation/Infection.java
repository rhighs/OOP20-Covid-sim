package Simulation;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * This is the Infection algorithm.
 *
 */
public class Infection implements BiFunction<Person, Person, Boolean> {
    private static int INF_RADIUS = 5;
    private static int STATUS_PERC = 70;
    private static int FP1_PERC = 30;
    private static int FP2_PERC = 20;
    private static int FP3_PERC = 10;

    @Override
    public Boolean apply(Person infector, Person victim) {
        return infection(victim);
    }

    private boolean infection(Person p) {
        if (p.isInfected()) {
            return false;
        }

        int trasm = checkTrasmissibility(p);
        if (trasm == 100) {
            return true;
        }
        // più è alto il parametro di trasmissione più è alta la probabilità di infettare
        Random rand = new Random();
        final int upperbound = 101;
        return rand.nextInt() <= trasm;
    }

    // infection depends from Mask status and type
    // mask status 70% mask type 30%
    private int checkTrasmissibility(Person p) {
        int perc = 0;

        perc += p.getMaskStatus() == Person.Mask.Status.UP ? 0 : STATUS_PERC;
        switch (p.getMaskProtection()) {
        case FP1: perc += FP1_PERC; break;
        case FP2: perc += FP2_PERC; break;
        case FP3: perc += FP3_PERC; break;
        }
        return perc;
    }
}
