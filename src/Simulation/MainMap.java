package Simulation;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import Components.PathCalculator;
import Components.PathGenerator;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh; 

import Dependency.DependencyHelper;

public class MainMap {
    private Node scene;

    public MainMap() {
        var assetManager = (AssetManager) DependencyHelper.getDependency("assetManager", AssetManager.class);
        var bState = (BulletAppState) DependencyHelper.getDependency("bulletAppState", BulletAppState.class);
        var rootNode = (Node) DependencyHelper.getDependency("rootNode", Node.class);
        
        scene = (Node) assetManager.loadModel("Scenes/test.j3o");
        scene.setName("SimulationScene");
        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        
        bState.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);
    }

    public PathCalculator createPathCalculator() {
        return new PathCalculator(scene);
    }

    public PathGenerator createPathGenerator() {
        return new PathGenerator(scene);
    }

    public NavMesh getNavFromScene(){
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        return new NavMesh(mesh);
    }
}

