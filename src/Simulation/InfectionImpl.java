package Simulation;

import java.util.Random;

/**
 *
 * @author simon
 */
class InfectionImpl implements InfectionAlg{
    //vedere uso
    private static int INF_RADIUS = 5;
    @Override
    public void infection(Person p) {
        if(!p.isInfected()){
            int param = checkTrasmissibility(p);
            if(param == 100){
                p.infect();
            }else{
                // se trasm piu di 60 generi numero random da 0 a 100 e se num > complementare, infetti
                //else se trasm meno di 60% generi numero random da 0 a 100 e se num > complementare, infetti
                    Random rand = new Random(); //instance of random class
                    final int upperbound = 101;
                    if(rand.nextInt(upperbound) >= (100 - param)){
                        p.infect();
                    }
                }
            }
        }
    //infection depends from Mask status
    /*private void maskInfection(Person p){
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
    }*/
    // mask status 70% mask type 30%
    private int checkTrasmissibility(Person p){
        //percentage of trasmissibility
        int perc = 0;
        //Migliorabile
        //check maskStatus (if Down is 70%)
        perc += 70 - (70 * p.getMask().getProtection().ordinal() / 2);
        //check maskProtection (if FFP1 perc is 30%)        
        perc += 30 - 10 * (p.getMask().getProtection().ordinal());
        return perc;
    }
    //calcolare indice di trasmissibilita attraverso parametri : mask, protMask, MaskStat from 0 to 10
    //es: se mascherina down e FFP1 = 100%
    
    
}
