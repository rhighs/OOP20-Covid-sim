package Simulation;

import java.util.Random;

/**
 *
 * @author simon
 */
class InfectionImpl implements InfectionAlg{

    private static int INF_RADIUS = 5;
    @Override
    public void infection(Person p) {
        if(!p.isInfected()  && p.getMask().getStatus() == Mask.MaskStatus.DOWN){
            p.infect();
        }else{
            maskInfection(p);
        }
        
    }
    //infection depends from Mask status
    private void maskInfection(Person p){
        //bad
            Random rand = new Random(); //instance of random class
            final int upperbound = 101;
            //case mask DOWN
            if(p.getMask().getStatus() == Mask.MaskStatus.DOWN){
                //probability of 70% to get infected
                if(rand.nextInt(upperbound) <= 70 ){
                    p.infect();
                }
            //case mask is UP
            }else{
                //probability of 30% to get Infected 
               if(rand.nextInt(upperbound) > 70 ){
                    p.infect();
                }
            }
    }
    
    private void checkTrasmissibility(Person p){
        int perc = 0;
        //check maskStatus refactor like bottom
        /*if(p.getMask().getStatus() == Mask.MaskStatus.DOWN){
            perc += 5;
        }else{
            perc += 2;
        }*/
        perc += 5 - p.getMask().getStatus().ordinal();
        //check maskProtection (if FFP1 perc is 50)
        perc += 5 - p.getMask().getProtection().ordinal();
    }
    //calcolare indice di trasmissibilita attraverso parametri : mask, protMask, MaskStat from 0 to 10
    //es: se mascherina down e FFP1 = 100%
    
    
}
