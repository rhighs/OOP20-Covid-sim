package Engine.graphics;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Engine.items.Entity;

public class GraphicsMeshComponent extends GraphicsComponent {

    private List<Geometry> geometries;

    public GraphicsMeshComponent(final Entity entity, Node parent, final String name, final Mesh mesh, final Material material) {
        super(entity, new Node(), parent);
        //this.entity = entity;
        //this.parent = parent;
        geometries = new ArrayList<>();
        addGeometry(name, mesh, material);
    }

    public List<Mesh> getMeshes(Mesh mesh) {
        return geometries.stream()
                .filter(g -> g.getMesh().equals(mesh))
                .map(x -> x.getMesh())
                .collect(Collectors.toList());
    }

    public void addGeometry(final String name, final Mesh mesh, final Material mat) {
        Geometry newGeom = new Geometry(name, mesh);
        newGeom.setMaterial(mat);
        ((Node) sp).attachChild(newGeom);
        geometries.add(newGeom);
    }
}
