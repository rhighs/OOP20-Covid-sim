package tmptmp;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;

public interface GraphicsComponent {
    Spatial create(String name, AssetManager assetManager);
    void setColor(final ColorRGBA color);
}
