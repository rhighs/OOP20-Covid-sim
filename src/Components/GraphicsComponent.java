package Components;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import Simulation.Entity;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import Dependency.DependencyHelper;

public class GraphicsComponent {

    private Entity entity;
    protected Spatial sp;
    private Material mat;
    private Node parent;
    private ColorRGBA color = ColorRGBA.Green;
    private AssetManager assetManager;

    public GraphicsComponent(final Entity entity) {
        this.entity = entity;
        this.parent = (Node) DependencyHelper.getDependency("rootNode", Node.class);
        Spatial cube = new Geometry("PersonCube", new Box(40, 40, 40));
        assetManager = (AssetManager) DependencyHelper.getDependency("assetManager", AssetManager.class);
        mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Blue);
        mat.setColor("Diffuse", ColorRGBA.Red);
        cube.setMaterial(mat);
        cube.scale(0.03f);
        cube.setShadowMode(ShadowMode.CastAndReceive);
        this.sp = cube;
        this.show();

        //binding spatial to entity, in order to make it "pickable"
        sp.setUserData("entity", entity);
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

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void show() {
        parent.attachChild(sp);
    }

    public void hide() {
        parent.detachChild(sp);
    }

    public boolean changeColor(final ColorRGBA color) {
        if (!this.color.equals(color)) {
            var m = ((Geometry) sp).getMaterial();
            m.setColor("Diffuse", color);
            return true;
        }
        return false;
    }

    public Spatial getSpatial() {
        return sp;
    }

}
