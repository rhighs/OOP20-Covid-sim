package Engine;

import com.jme3.scene.Spatial;
import com.jme3.material.Material;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;

/* just a bunch of constants for our models and meshes
 * this should make it easier to load some stuff
 * (without passing assetManager everywhere
 */
public class Assets {
    public static Spatial PERSON_MODEL;
    public static Material BRICK_WALL;
    public static Texture BRICK_WALL_TEXTURE;
    
    private Assets() {
    }

    public static void loadAssets(AssetManager assetManager) {
        PERSON_MODEL = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        BRICK_WALL = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        BRICK_WALL_TEXTURE = assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg");
        BRICK_WALL.setTexture("ColorMap", BRICK_WALL_TEXTURE);
    }
}
