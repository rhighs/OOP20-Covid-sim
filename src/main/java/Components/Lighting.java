package Components;

import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.light.DirectionalLight;

import Environment.Ambient;
import Environment.Locator;

/**
 *
 * @author rob, chris
 */
public class Lighting {
    final int SHADOWMAP_SIZE = 4096;
    final private Vector3f lightDirection = new Vector3f(-0.5f, -0.5f, -0.5f);
    final private Ambient ambient;

    public Lighting(final Ambient ambient) {
        this.ambient = ambient;
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(lightDirection.normalizeLocal());
        sun.setColor(ColorRGBA.White);
        
        ambient.addSunLight(sun, SHADOWMAP_SIZE);
    }

    public void setLight() { }
}

