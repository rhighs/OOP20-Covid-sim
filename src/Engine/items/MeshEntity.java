package Engine.items;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class MeshEntity extends EntityOld {
    private Mesh mesh;

    public MeshEntity(final int id, final String name, final Mesh mesh, final Material material) {
        super(id, name, new Geometry(name, mesh), material);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
