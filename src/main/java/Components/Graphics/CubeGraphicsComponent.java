package Components.Graphics;

import Environment.Services.Graphical.Graphics;
import Simulation.Entity;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * A cube implementation.
 *
 * @author rob, chris, savi
 */
public class CubeGraphicsComponent implements GraphicsComponent {

    private static final String GEO_NAME = "PersonCube";

    private final Entity entity;

    private final Spatial sp;

    private final Material mat;

    private final ColorRGBA color = ColorRGBA.Green;

    private final Graphics graphics;

    /**
     * Creates a new instance of the class.
     *
     * @param graphics A service from jME3 that allow you to interact with graphics.
     * @param entity   An interface that is implemented from entities (people, etc).
     */
    public CubeGraphicsComponent(final Graphics graphics, final Entity entity) {
        this(graphics, entity, new Geometry(GEO_NAME, new Box(10, 50, 10)));
    }

    /**
     * Creates a new instance of the class with all instance as parameters for inversion
     * of control during unit testing.
     *
     * @param graphics A service from jME3 that allow you to interact with graphics.
     * @param entity   An interface that is implemented from entities (people, etc).
     * @param sp       A node of the scene.
     */
    public CubeGraphicsComponent(final Graphics graphics, final Entity entity, final Geometry sp) {
        this.graphics = graphics;
        this.entity = entity;
        this.sp = sp;

        mat = graphics.createShadedMaterial(ColorRGBA.Red, ColorRGBA.Blue);

        this.sp.setMaterial(mat);
        this.sp.scale(0.03f);
        this.sp.setShadowMode(ShadowMode.CastAndReceive);

        this.show();
    }

    @Override
    public void show() {
        graphics.addToScene(sp);
    }

    @Override
    public void changeColor(final ColorRGBA color) {
        graphics.changeMaterialColor(sp, color);
    }

    @Override
    public Spatial getSpatial() {
        return sp;
    }
}
