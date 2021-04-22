package Environment;

import com.jme3.scene.Node;
import com.jme3.scene.Mesh;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import Components.PathFinder;
import Components.PathFinderExecutor;
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

    public MainMap(final AssetManager assetManager, final BulletAppState bullet, final Node rootNode) {
        this.assetManager = assetManager;
        this.bullet = bullet;
        this.rootNode = rootNode;

        scene = (Node) assetManager.loadModel(MAP_MODEL);
        scene.setName("SimulationScene");
        //this is temp, speific for the test map
        scene.setLocalTranslation(new Vector3f(2, -10, 1));

        bullet.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);

        pathCalc = new PathFinderExecutor(scene);
    }

    public PathFinderExecutor createPathCalculator() {
        return new PathFinderExecutor(scene);
    }

    public PathFinderExecutor getPathCalculator(){
        return pathCalc;
    }

    public PathFinder createPathGenerator() {
        return new PathFinder(scene);
    }

    public NavMesh getNavFromScene(){
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        return new NavMesh(mesh);
    }
}

