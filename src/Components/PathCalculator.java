package Components;


import java.util.List;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import java.util.concurrent.Future;
import com.jme3.ai.navmesh.NavMesh;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import com.jme3.ai.navmesh.Path.Waypoint;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author rob
 */
public class PathCalculator {

    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private Node scene;
    private NavMesh nav;
    PathGenerator pathGen;

    public PathCalculator(final NavMesh navMesh) {
        nav = navMesh;
    }
    
    public PathCalculator(final Node scene) {
        this.scene = scene;
    }

    public Future<List<Waypoint>> request(final Vector3f currentPos) {
        return pool.submit(new PathGeneratorCall(new PathGenerator(scene), currentPos));
        //return pool.submit(new PathGeneratorCall(new PathGenerator(nav), currentPos));
    }
}

class PathGeneratorCall implements Callable<List<Waypoint>> {

    final Vector3f startingPoint;
    PathGenerator pathGen;

    public PathGeneratorCall(final PathGenerator pathGen, final Vector3f pos) {
        this.pathGen = pathGen;
        this.startingPoint = pos;
    }

    public List<Waypoint> call() throws Exception {
        Vector3f r = pathGen.getRandomPoint();
        System.out.println(startingPoint + " " + r);
        return pathGen.getPath(startingPoint, pathGen.getRandomPoint());
    }
}
