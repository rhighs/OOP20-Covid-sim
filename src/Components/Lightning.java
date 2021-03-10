package Components;

import com.jme3.asset.AssetManager;
//import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 *
 * @author rob, chris
 */
public class Lightning {
    final int SHADOWMAP_SIZE = 4096;
    final private Vector3f lightDirection = new Vector3f(-0.5f, -0.5f, -0.5f);
    
    public Lightning(AssetManager assetManager, Node rootNode, ViewPort viewport) {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDirection.normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        var shadowRenderer = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        shadowRenderer.setLight(sun);
        viewport.addProcessor(shadowRenderer);

        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        //var al = new AmbientLight();
        //al.setColor(ColorRGBA.White.mult(1.3f));
        //rootNode.addLight(al);
    }
}
