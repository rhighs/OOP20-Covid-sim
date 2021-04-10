package Components;

import Environment.Ambient;
import Environment.Locator;
import com.jme3.asset.AssetManager;
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
public class Lighting {
    final int SHADOWMAP_SIZE = 4096;
    final private Vector3f lightDirection = new Vector3f(-0.5f, -0.5f, -0.5f);
    final Ambient ambient = Locator.getAmbient();

    public Lighting() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDirection.normalizeLocal());
        sun.setColor(ColorRGBA.White);
        
        ambient.addSunLight(sun, SHADOWMAP_SIZE);
    }

    public void setLight() { }
}

