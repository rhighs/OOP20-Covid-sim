/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.items;

import Engine.items.Entity;
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
public class BoxEntity extends Entity{
    private Box box;

    public BoxEntity(final Box box, final Material material, final int id){
        super(new Geometry(null, box), material, id);
        this.box = box;
    }
    
    public BoxEntity(final Box box, final Material material, final Vector3f position, final int id){
        this(box, material, id);
        setPosition(position);
    }
    
    public Box getBox(){
        return this.box;
    }
}
