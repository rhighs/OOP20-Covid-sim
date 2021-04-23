package Components.Graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public interface GraphicsComponent {
    void moveTo(Vector3f pos);

    void rotate(final float x, final float y, final float z);

    void scale(final float x, final float y, final float z);

    void show();

    void hide();

    void changeColor(final ColorRGBA color);

    Spatial getSpatial();
}
