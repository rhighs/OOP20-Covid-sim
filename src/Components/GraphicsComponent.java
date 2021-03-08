package Components;

import Simulation.Assets;
import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.ColorRGBA;
import Simulation.Entity;
import Simulation.Person;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;

public class GraphicsComponent {
    
    private Entity entity;
    protected Spatial sp;
    private Material mat;
    private Node parent;
    private ColorRGBA color = ColorRGBA.Green;

    public GraphicsComponent(Entity entity, Node parent) {
        this.entity = entity;
        this.setSpatial();
        this.parent = parent;
        this.show();
        
        //binding spatial to entity, in order to make it "pickable"
        sp.setUserData("entity", entity);
    }
    
    public void setSpatial(){
        
        switch(entity.getIdentificator()){
            case PERSON:
                sp = Assets.CUBE.clone();
                sp.scale(0.03f);
                sp.setShadowMode(ShadowMode.CastAndReceive);
                break;
                
            default:
                break;
        }
        
    }

    public GraphicsComponent(final Entity entity, final Material mat, Node parent) {
        this(entity, parent);
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
