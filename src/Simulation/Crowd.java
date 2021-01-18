/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;

/**
 *
 * @author rob
 */
public class Crowd {
    
    private List<Person> people;
    private int nPeople;
    private Spatial scene;
    
    public Crowd(final Spatial scene, final int nPeople, SimpleApplication app){
        this.nPeople = nPeople;
        this.scene = scene;
        
        for(int counter = 0; counter < nPeople; counter++){
            people.add(new Person(scene, new Vector3f(0,0,0), app));
        }
    }
    
}
