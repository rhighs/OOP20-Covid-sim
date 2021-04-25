package Environment.Services.Graphical;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author Rob
 */
public class Graphics {
    private final AssetManager assetManager;
    private final Node rootNode;

    public Graphics(AssetManager assetManager, Node rootNode) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }

    public void addToScene(final Spatial elem) {
        rootNode.attachChild(elem);
    }

// <<<<<<< HEAD
    public void removeFromScene(final Spatial elem) {
        rootNode.detachChild(elem);
    }

// =======
// >>>>>>> d8345603be4bd98f429811f34d5dc400b2f54966
    public Material createShadedMaterial(final ColorRGBA ambient, final ColorRGBA diffuse) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ambient);
        material.setColor("Diffuse", diffuse);

        return material;
    }

    public void changeMaterialColor(final Spatial spatial, final ColorRGBA color) {
        Material m = ((Geometry) spatial).getMaterial();
        m.setColor("Diffuse", color);
    }
// <<<<<<< HEAD

    public Spatial getModel(final String modelPath) {
        return this.assetManager.loadModel(modelPath);
    }

// =======
// >>>>>>> d8345603be4bd98f429811f34d5dc400b2f54966
}
