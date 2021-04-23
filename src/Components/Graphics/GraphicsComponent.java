package Components;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;

public interface GraphicsComponent {
    public void moveTo(Vector3f pos);
    public void rotate(final float x, final float y, final float z);
    public void scale(final float x, final float y, final float z);
    public void show();
    public void hide();
    public void changeColor(final ColorRGBA color);
    public Spatial getSpatial();
}
