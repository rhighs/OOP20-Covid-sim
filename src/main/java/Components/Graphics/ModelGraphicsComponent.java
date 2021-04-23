package Components.Graphics;

import Environment.Services.Graphical.Graphics;
import Simulation.Entity;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;

public class ModelGraphicsComponent implements GraphicsComponent {
    private final Spatial sp;
    private Entity entity;
    private Graphics graphics;

    public ModelGraphicsComponent(final Graphics graphics, final Entity entity) {
        // TODO
        // load a model, setup color, etc...

        var model = graphics.getModel("Models/person.glb");
        model.setShadowMode(ShadowMode.CastAndReceive);
        this.sp = model;

        this.show();
    }

    @Override
    public void show() {
        graphics.addToScene(sp);
    }

    @Override
    public void changeColor(final ColorRGBA color) {
        // TODO
        // maybe load a different model?
    }

    @Override
    public Spatial getSpatial() {
        return sp;
    }
}

