package Simulation;

import java.util.Random;

/**
 *
 * @author simon
 */
class InfectionImpl implements InfectionAlg{

    @Override
    public void infection(Person p) {
        if(!p.isInfected()  && p.getMask() == Person.Mask.DOWN){
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
            if(p.getMask() == Person.Mask.DOWN){
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
    
    
}
