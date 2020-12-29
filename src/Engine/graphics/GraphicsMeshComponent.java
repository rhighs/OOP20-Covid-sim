package Engine.items;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class GraphicsMeshComponent extends Entity {
    private Mesh mesh;

    public MeshEntity(final String name, final Mesh mesh, final Material material) {
        super(new Geometry(name, mesh), material);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
