package Simulation;

import com.jme3.scene.Spatial;
import com.jme3.material.Material;
import com.jme3.texture.Texture;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/* just a bunch of constants for our models and meshes
 * this should make it easier to load some stuff
 * (without passing assetManager everywhere) */
public class Assets {

    public static Spatial PERSON_MODEL;
    public static Spatial CUBE;
    public static Material BRICK_WALL;
    public static Texture BRICK_WALL_TEXTURE;
    
    private Assets() {
    }

    public static void loadAssets(AssetManager assetManager) {
        PERSON_MODEL = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        BRICK_WALL = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        BRICK_WALL_TEXTURE = assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg");
        BRICK_WALL.setTexture("ColorMap", BRICK_WALL_TEXTURE);
        
        CUBE = new Geometry("boi", new Box(40,40,40));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // ... specify .j3md file to use (illuminated).
        mat.setBoolean("UseMaterialColors",true);  // Set some parameters, e.g. blue.
        mat.setColor("Ambient", ColorRGBA.Blue);   // ... color of this object
        mat.setColor("Diffuse", ColorRGBA.Blue);   // ... color of light being reflected
        CUBE.setMaterial(mat);               // Use new material on this Geometry.
    }
}
