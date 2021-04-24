package Environment.Services.Map;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class MainMapImpl implements MainMap {
    private final String MAP_MODEL = "Scenes/ubibo/ubibo.j3o";
    private final String NAVMESH_NAME = "NavMesh";
    private final String SCENE_NAME = "SimulationScene";
    private Node scene;

    public MainMapImpl(final AssetManager assetManager, final BulletAppState bullet, final Node rootNode) {
        scene = (Node) assetManager.loadModel(MAP_MODEL);
        scene.setName(SCENE_NAME);

        bullet.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);
    }

    @Override
    public NavMesh getNavMesh() {
        var sceneAsNode = (Node) scene;
        var geometry = (Geometry) sceneAsNode.getChild(NAVMESH_NAME);
        return new NavMesh(geometry.getMesh());
    }

    @Override
    public PathFinderExecutor createPathFinderExecutor() {
        return new PathFinderExecutorImpl(scene);
    }

    @Override
    public PathFinderImpl createPathFinder() {
        return new PathFinderImpl(scene);
    }
}

