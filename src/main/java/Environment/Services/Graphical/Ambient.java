package Environment.Services.Graphical;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * @author rob
 */
public class Ambient {
    final int SHADOWMAP_SIZE = 4096;
    final private Vector3f lightDirection = new Vector3f(-0.5f, -0.5f, -0.5f);
    private final Node rootNode;
    private final ViewPort viewPort;
    private final AssetManager assetManager;

    public Ambient(final AssetManager assetManager, final Node rootNode, final ViewPort viewPort) {
        this.rootNode = rootNode;
        this.viewPort = viewPort;
        this.assetManager = assetManager;

        var light = new DirectionalLight();
        light.setDirection(lightDirection.normalizeLocal());
        light.setColor(ColorRGBA.White);
        this.addSunLight(light, SHADOWMAP_SIZE);
    }

    private void addSunLight(final DirectionalLight light, int shadowDefinition) {
        DirectionalLightShadowRenderer shadowRenderer = new DirectionalLightShadowRenderer(assetManager, shadowDefinition, 3);
        shadowRenderer.setLight(light);
        rootNode.addLight(light);
        viewPort.addProcessor(shadowRenderer);
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }

    public Node getRootNode() {
        return rootNode;
    }

}
