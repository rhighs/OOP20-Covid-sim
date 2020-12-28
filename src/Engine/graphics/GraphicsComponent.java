/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.graphics;

import Simulation.Entity;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author rob
 */
public class GraphicsComponent {
    private Node node;
    private Entity entity;
    private Spatial spatial;
    private Material material;
    private AssetManager assetManager;
    private ColorRGBA color = ColorRGBA.Green;
    
    public GraphicsComponent(final Entity entity, final Spatial Spatial, final Node node, final AssetManager assetManager){
        this.assetManager = assetManager;
        this.entity       = entity;
        this.spatial      = spatial;
        this.node         = node;
        
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.spatial.setMaterial(material);
        
        node.attachChild(this.spatial);
    }
    
    public boolean changeColor(final ColorRGBA color){
        if(!this.color.equals(color)){
            material.setColor(color.toString(), color);
            spatial.setMaterial(material);
            return true;
        }
        return false;
    }
    
    public Spatial getSpatial(){
        return this.spatial;
    }
    
    //adding arms or other stuff to the gfx object
    public void attachShape(final Node n){
        ((Node)spatial).attachChild(n);
    }
    
    public void hide(){
        this.node.detachChild(spatial);
    }
    
}
