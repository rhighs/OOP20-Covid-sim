package Components;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.shape.Box;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import Simulation.Entity;
import Environment.Graphics;

public class CubeGraphicsComponent implements GraphicsComponent {
    private Entity entity;
    private Spatial sp;
    private Material mat;
    private ColorRGBA color = ColorRGBA.Green;
    private Graphics graphics;

    public CubeGraphicsComponent(final Graphics graphics, final Entity entity) {
        this.graphics = graphics;
        this.entity = entity;
        this.sp = new Geometry("PersonCube", new Box(40, 40, 40));
        mat = graphics.createShadedMaterial(ColorRGBA.Red, ColorRGBA.Blue);
        this.sp.setMaterial(mat);
        this.sp.scale(0.03f);
        this.sp.setShadowMode(ShadowMode.CastAndReceive);
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
        graphics.changeMaterialColor(sp, color);
    }

    @Override
    public Spatial getSpatial() {
        return sp;
    }
}
