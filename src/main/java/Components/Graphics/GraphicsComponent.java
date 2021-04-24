package Components.Graphics;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 * An interface for a graphic component.
 */
public interface GraphicsComponent {

    /**
     * Show the component on the screen.
     */
    void show();

    /**
     * Changes color to the special.
     *
     * @param color The new color.
     */
    void changeColor(final ColorRGBA color);

    /**
     * Returns the spatial.
     *
     * @return The spatial.
     */
    Spatial getSpatial();
}
