package Environment.Services.Map;

import com.jme3.scene.Node;
import com.jme3.scene.Geometry;
import com.jme3.asset.AssetManager;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.bullet.BulletAppState;

public class MainMap {
    private final String MAP_MODEL = "Scenes/test/scene.j3o";
    private Node scene;
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bullet;

    private PathFinderExecutor pathCalc;
    private final String NAVMESH_NAME = "NavMesh";
    private final String SCENE_NAME = "SimulationScene";

    public MainMap(final AssetManager assetManager, final BulletAppState bullet, final Node rootNode) {
        this.assetManager = assetManager;
        this.bullet = bullet;
        this.rootNode = rootNode;

        scene = (Node) assetManager.loadModel(MAP_MODEL);
        scene.setName(SCENE_NAME);

        bullet.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);

        pathCalc = new PathFinderExecutor(scene);
    }

    public NavMesh getNavMesh(){
        var sceneAsNode = (Node) scene;
        var geometry = (Geometry) sceneAsNode.getChild(NAVMESH_NAME);
        return new NavMesh(geometry.getMesh());
    }

    public PathFinderExecutor createPathCalculator() {
        return new PathFinderExecutor(scene);
    }

    public PathFinder createPathGenerator() {
        return new PathFinder(scene);
    }

}

