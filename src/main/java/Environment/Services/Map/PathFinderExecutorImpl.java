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
public class PathFinderExecutorImpl implements PathFinderExecutor {

    private final ExecutorService pool = Executors.newSingleThreadExecutor();

    private Node scene;

    public PathFinderExecutorImpl(final Node scene) {
        this.scene = scene;
    }

    @Override
    public Future<List<Waypoint>> request(final Vector3f currentPos) {
        return pool.submit(new PathGeneratorCallImpl(new PathFinderImpl(scene), currentPos));
    }

    @Override
    public void shutdown() {
        pool.shutdown();
    }
}
