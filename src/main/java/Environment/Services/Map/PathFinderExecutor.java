package Environment.Services.Map;

import com.jme3.ai.navmesh.Path;
import com.jme3.math.Vector3f;

import java.util.List;
import java.util.concurrent.Future;

public interface PathFinderExecutor {

    /**
     * @param currentPos current position of the entity
     * @return a Future which will contain a list of waypoint obtained by a PathGeneratorCall
     */
    Future<List<Path.Waypoint>> request(Vector3f currentPos);
}
