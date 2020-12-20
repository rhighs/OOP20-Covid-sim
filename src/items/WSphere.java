/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author rob
 */
public class WSphere extends ItemWrapper {
    private Sphere sphere;
    private int zSamples, radialSamples;
    private float radius;
    
    public WSphere(String name, final int zSamples, final int radialSamples, final float radius){
        this.name = name;
        this.zSamples = zSamples;
        this.radialSamples = radialSamples;
        this.radius = radius;
        
        this.sphere = new Sphere(this.zSamples, this.radialSamples, this.radius);
        this.geometry = new Geometry(name, sphere);
    }
    
    public Sphere getSphere() {
        return sphere;
    }

    public int getzSamples() {
        return zSamples;
    }

    public int getRadialSamples() {
        return radialSamples;
    }

    public float getRadius() {
        return radius;
    }
}
