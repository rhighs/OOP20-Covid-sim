package Engine.graphics;

import com.jme3.material.Material;
//import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class GraphicsMeshComponent extends GraphicsComponent {
    private Mesh mesh;

    public GraphicsMeshComponent(final String name, final Mesh mesh, final Material material) {
        super(null, null, null, null, null);//new Geometry(name, mesh), material);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
