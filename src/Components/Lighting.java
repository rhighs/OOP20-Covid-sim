/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 *
 * @author rob
 */
public class Lighting {

    final int SHADOWMAP_SIZE = 4096;
    private SimpleApplication app;
    private Node rootNode;

    private Vector3f lightDirection = new Vector3f(-0.5f, -0.5f, -0.5f);
    private AssetManager assetManager;
    private ViewPort viewPort;

    public Lighting(final SimpleApplication app) {
        this.app = app;
        this.rootNode = app.getRootNode();
        this.assetManager = app.getAssetManager();
        this.viewPort = app.getViewPort();
    }

    public void setLight() {
        var sun = new DirectionalLight();
        sun.setDirection(lightDirection.normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        var dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);

        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        //var al = new AmbientLight();
        //al.setColor(ColorRGBA.White.mult(1.3f));
        //rootNode.addLight(al);
    }
}
