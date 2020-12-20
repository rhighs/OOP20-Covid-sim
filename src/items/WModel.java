/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob
 */
public class WModel extends ItemWrapper{
    
    public WModel(String name, Spatial model){
        this.name = name;
        this.geometry = model;
    }
}
