package Components;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;

<<<<<<< HEAD
import Environment.Graphics;
import Environment.Locator;

public class GraphicsComponent {
    private Entity entity;
    protected Spatial sp;
    private Material mat;
    private ColorRGBA color = ColorRGBA.Green;
    private Graphics graphics = Locator.getGraphics();

    public GraphicsComponent(final Entity entity) {
        this.entity = entity;
        
        Spatial cube = new Geometry("PersonCube", new Box(40, 40, 40));
        mat = graphics.createShadedMaterial(ColorRGBA.Red, ColorRGBA.Blue);
        
        cube.setMaterial(mat);
        cube.scale(0.03f);
        cube.setShadowMode(ShadowMode.CastAndReceive);
        
        this.sp = cube;
        this.show();

        //binding spatial to entity, in order to make it "pickable"
        sp.setUserData("", mat);
    }

    public GraphicsComponent(final Entity entity, final Material mat) {
        this(entity);
        this.mat = mat;
        this.sp.setMaterial(mat);
    }

    public void moveTo(final Vector3f pos) {
        sp.setLocalTranslation(pos);
    }

    public void rotate(final float x, final float y, final float z) {
        sp.rotate(x, y, z);
    }

    public void scale(final float x, final float y, final float z) {
        sp.scale(x, y, z);
    }

    public void show() {
        graphics.addToScene(sp);
    }

    public void hide() {
        graphics.removeFromScene(sp);
    }

    public void changeColor(final ColorRGBA color) {
        graphics.changeMaterialColor(sp, color);
    }

    public Spatial getSpatial() {
        return sp;
    }

}
=======
public interface GraphicsComponent {
    public void moveTo(Vector3f pos);
    public void rotate(final float x, final float y, final float z);
    public void scale(final float x, final float y, final float z);
    public void show();
    public void hide();
    public void changeColor(final ColorRGBA color);
    public Spatial getSpatial();
}
>>>>>>> 784b82dcda83e9fc82c8a86efc2476c85c32da6c
