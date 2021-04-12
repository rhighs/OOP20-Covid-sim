package Simulation;

import java.util.Random;
import java.util.function.BiFunction;

/**
 *
 * @author simon
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
        //più è alto il parametro di trasmissione più è alta la probabilità di infettare
        Random rand = new Random();
        final int upperbound = 101;
        if (rand.nextInt(upperbound) <= trasm) {
            return true;
        }   
        return false;
    }

    //infection depends from Mask status and type
    // mask status 70% mask type 30%
    private int checkTrasmissibility(Person p) {
        
        Mask maskPerson = p.getMask();
        
        //percentage of trasmissibility
        int perc = 0;
        //Migliorabile
        
        //check maskStatus (if Down is 70%)
        perc += maskPerson.getStatus() == Mask.MaskStatus.UP ? 0 : STATUS_PERC;
        
        //check maskProtection (if FFP1 perc is 30%)
        if(maskPerson.getProtection() == Mask.MaskProtection.FP1){
            perc += FP1_PERC;
        }else{
            perc += maskPerson.getProtection() == Mask.MaskProtection.FP2 ? FP2_PERC : FP3_PERC;
        }

        return perc;
    }
}
