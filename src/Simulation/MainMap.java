package Simulation;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import Components.PathCalculator;
import Components.PathGenerator;
import Components.Lightning;

public class MainMap {
    Node scene;
    Lightning light;

    public MainMap(AssetManager assetManager, BulletAppState bState, Node rootNode) {
        scene = (Node) assetManager.loadModel("Scenes/test.j3o");
        scene.setName("SimulationScene");
        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        //scene.setLocationTranslation(new Vector3f(2, -10, 1));
        bState.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);
    }

    public PathCalculator createPathCalculator() {
        return new PathCalculator(scene);
    }

    public PathGenerator createPathGenerator() {
        return new PathGenerator(scene);
    }
}

