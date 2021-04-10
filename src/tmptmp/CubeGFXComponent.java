package tmptmp;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
//import Simulation.Entity;

public class CubeGFXComponent implements GraphicsComponent {
    final private Entity entity;

    public CubeGFXComponent(Entity e) {
        entity = e;
    }

    public Spatial create(String name, AssetManager assetManager) {
        Geometry cube = new Geometry(name, new Box(40, 40, 40));
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Ambient", ColorRGBA.Blue);
        mat.setColor("Diffuse", ColorRGBA.Red);
        cube.setMaterial(mat);
        cube.scale(0.03f);
        cube.setShadowMode(ShadowMode.CastAndReceive);
        cube.setUserData("entity", entity);
        return cube;
    }

    public void setColor(final ColorRGBA color) {
        Geometry cube = (Geometry) entity.getSpatial();
        cube.getMaterial().setColor("Diffuse", color);
    }
}

