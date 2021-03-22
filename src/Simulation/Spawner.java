/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import java.util.List;

/**
 *
 * @author rob
 */
public class Spawner {
    private final List<Person> crowd;
    private final MainMap map;
    
    public Spawner(final MainMap map, final List<Person> crowd){
        this.map = map;
        this.crowd = crowd;
    }
    
    public void setRandomSpawnPoints(int numOfPoints){
        
    }
    
}
