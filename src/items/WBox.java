/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;

/**
 *
 * @author rob
 */
public class WBox extends ItemWrapper{
    
    protected float w, h, t; //width height and thickness
    private Box b;
    
    public WBox(){
        this("a Box" , 1, 1, 1);
    }
    
    public WBox(final String name, final float w, final float h, final float t){
        this.w = w;
        this.h = h;
        this.t = t;
        this.name = name;
        b = new Box(w, h, t);
        geometry = new Geometry(name, b);
    }
    
    public WBox(final String name, final float w, final float h, final float t, final Vector3f position){
        this(name, w, h, t);
        this.v = new Vector3f(position);
        setPosition(v);
    }
}
