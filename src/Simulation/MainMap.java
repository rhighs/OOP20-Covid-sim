package Simulation;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.Lighting;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public class MainMap {
    private Node scene;

    public MainMap(AssetManager assetManager, BulletAppState bState, Node rootNode) {
        scene = (Node) assetManager.loadModel("Scenes/test.j3o");
        scene.setName("SimulationScene");
        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        bState.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);
    }

    public PathCalculator createPathCalculator() {
        return new PathCalculator(getNavFromScene(scene));
    }

    public PathGenerator createPathGenerator() {
        return new PathGenerator(getNavFromScene(scene));
    }
    
    public NavMesh getNavFromScene(final Node scene){
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        return new NavMesh(mesh);
    }
}

