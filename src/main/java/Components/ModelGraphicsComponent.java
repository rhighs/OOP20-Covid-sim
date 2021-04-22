package Components;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.shape.Box;
import com.jme3.material.Material;
import Simulation.Entity;
import Environment.Graphics;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;

public class ModelGraphicsComponent implements GraphicsComponent {
    private Entity entity;
    private Spatial sp;
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
    public void moveTo(final Vector3f pos) {
        sp.setLocalTranslation(pos);
    }

    @Override
    public void rotate(final float x, final float y, final float z) {
        sp.rotate(x, y, z);
    }

    @Override
    public void scale(final float x, final float y, final float z) {
        sp.scale(x, y, z);
    }

    @Override
    public void show() {
        graphics.addToScene(sp);
    }

    @Override
    public void hide() {
        graphics.removeFromScene(sp);
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

