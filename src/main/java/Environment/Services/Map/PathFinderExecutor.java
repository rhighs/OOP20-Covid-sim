package Environment.Services.Map;

import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author rob
 */
public class PathFinderExecutor {

    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    private Node scene;

    public PathFinderExecutor(final Node scene) {
        this.scene = scene;
    }

    public Future<List<Waypoint>> request(final Vector3f currentPos) {
        return pool.submit(new PathGeneratorCall(new PathFinder(scene), currentPos));
    }
}

class PathGeneratorCall implements Callable<List<Waypoint>> {

    final Vector3f startingPoint;
    PathFinder pathGen;

    public PathGeneratorCall(final PathFinder pathGen, final Vector3f pos) {
        this.pathGen = pathGen;
        this.startingPoint = pos;
    }

    public List<Waypoint> call() {
        return pathGen.getPath(startingPoint, pathGen.getRandomPoint());
    }
}

