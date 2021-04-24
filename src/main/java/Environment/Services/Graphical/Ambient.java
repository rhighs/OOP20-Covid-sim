package Environment.Services.Graphical;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * @author rob
 */
public class Ambient {
    final int SHADOWMAP_SIZE = 4096;
    final private int NB_SPLITS = 3;
    private final Node rootNode;
    private final ViewPort viewPort;
    private final AssetManager assetManager;
    private DirectionalLight cameraLight;

    public Ambient(final AssetManager assetManager, final Node rootNode, final ViewPort viewPort) {
        this.rootNode = rootNode;
        this.viewPort = viewPort;
        this.assetManager = assetManager;

        cameraLight = new DirectionalLight();
        cameraLight.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(cameraLight);
    }

    public void addSunLight(final Vector3f lightDirection){
        var sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(lightDirection);
        rootNode.addLight(sun);

        var directionalShadowRender = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, NB_SPLITS);
        directionalShadowRender.setLight(sun);
        viewPort.addProcessor(directionalShadowRender);

        addShadowFilter(sun);
    }

    private void addShadowFilter(final DirectionalLight light){
        var directionalShadowFilter = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, NB_SPLITS);
        var postProcessingFilter = new FilterPostProcessor(assetManager);
        directionalShadowFilter.setLight(light);
        directionalShadowFilter.setEnabled(true);

        postProcessingFilter.addFilter(directionalShadowFilter);
        viewPort.addProcessor(postProcessingFilter);
    }

    public void addAmbientLight(final ColorRGBA color){
        var ambientLight = new AmbientLight();
        ambientLight.setColor(color);
        rootNode.addLight(ambientLight);
    }

    public void setBackgroundColor(final ColorRGBA color){
        viewPort.setBackgroundColor(color);
    }

    public void setCamLightDirection(final Vector3f lightDirection){
        cameraLight.setDirection(lightDirection);
    }

    public Node getRootNode() {
        return rootNode;
    }
}
