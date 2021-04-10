/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environment;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 *
 * @author rob
 */
public class Ambient {
    private Node rootNode;
    private ViewPort viewPort;
    private AssetManager assetManager;
    
    public Ambient(final AssetManager assetManager, final Node rootNode, final ViewPort viewPort){
        this.rootNode = rootNode;
        this.viewPort = viewPort;
        this.assetManager = assetManager;
    }
    
    public void addSunLight(final DirectionalLight light, int shadowDefinition){
        rootNode.addLight(light);        
        var shadowRenderer = new DirectionalLightShadowRenderer(assetManager, shadowDefinition, 3);
        shadowRenderer.setLight(light);
        viewPort.addProcessor(shadowRenderer);
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }
            
}
