package Engine.graphics;

import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import Engine.items.Entity;

public class GraphicsComponent{
    private Entity entity;
    protected Spatial sp;
    private Material mat;
    private Node parent;
    private ColorRGBA color = ColorRGBA.Green;

    public GraphicsComponent(final Entity entity, final Spatial sp, Node parent) {
        this.entity = entity;
        this.sp = sp;
        this.parent = parent;
        show();
    }

    public GraphicsComponent(final Entity entity, final Spatial sp, final Material mat, Node parent) {
        this(entity, sp, parent);
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

    public boolean changeColor(final ColorRGBA color){
        if (!this.color.equals(color)) {
            mat.setColor(color.toString(), color);
            sp.setMaterial(mat);
            return true;
        }
        return false;
    }

    public Spatial getSpatial() {
        return sp;
    }

}
