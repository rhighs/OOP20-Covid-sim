import com.jme3.math.Vector3f;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class GraphicsComponent {
    private Spatial sp;
    private Material mat;
    private Node parent;

    public Entity(final Spatial sp, final Material mat, Node parent) {
        this.sp = sp;
        this.mat = mat;
        this.parent = parent;
    }

    public void moveTo(final Vector3f pos) {
        sp.setLocalTranslation(pos);
    }

    public void rotate(final float x, final float y, final float z) {
        sp.rotate(x, y, z);
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
}
