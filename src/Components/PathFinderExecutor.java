package Components;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author rob
 */
public class PathFinderExecutor {
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    private Node scene;
    private NavMesh nav;
    private PathFinder pathGen;

    static class WaypointCreator implements Callable<List<Waypoint>> {
        private final Vector3f startPos;
        private final PathFinder generator;

        public WaypointCreator(final PathFinder pathGen, final Vector3f pos) {
            this.generator = pathGen;
            this.startPos = pos;
        }

        @Override
        public List<Waypoint> call() throws Exception {
            return generator.getPath(startPos, generator.getRandomPoint());
        }
    }

    static class PositionCreator implements Callable<List<Vector3f>> {
        private final Vector3f startPos;
        private final PathFinder generator;

        public PositionCreator(final PathFinder pathGen, final Vector3f pos) {
            this.generator = pathGen;
            this.startPos = pos;
        }

        @Override
        public List<Vector3f> call() throws Exception {
            return generator.getPath(startPos, generator.getRandomPoint())
                    .stream()
                    .map(x -> x.getPosition())
                    .collect(Collectors.toList());
        }
    }

    public PathFinderExecutor(final NavMesh navMesh) {
        nav = navMesh;
    }

    public PathFinderExecutor(final Node scene) {
        this.scene = scene;
    }

    public Future<List<Vector3f>> requestVector3f(final Vector3f pos) {
        return pool.submit(new PositionCreator(new PathFinder(scene), pos));
    }

    public Future<List<Waypoint>> request(final Vector3f pos) {
        return pool.submit(new WaypointCreator(new PathFinder(scene), pos));
    }
}
