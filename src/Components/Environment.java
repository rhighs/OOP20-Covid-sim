/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 *
 * @author Rob
 */
public class Environment {
    private BulletAppState bullet;
    private AssetManager assetManager;
    private Node rootNode;
    
    public Environment(SimpleApplication app){  
        this.bullet = new BulletAppState();
        app.getStateManager().attach(bullet);
    }
    
    public void addToScene(final Node elem){
        rootNode.attachChild(elem);
    }
    
    public void addToPhysicSpace(Object obj){
        bullet.getPhysicsSpace().add(obj);
    }
}
