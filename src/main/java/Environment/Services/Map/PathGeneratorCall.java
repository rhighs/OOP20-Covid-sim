package Environment.Services.Map;

import com.jme3.ai.navmesh.Path;

import java.util.List;

public interface PathGeneratorCall {

    /**
     * @return a list of waypoints obtained by the path finder
     */
    List<Path.Waypoint> call();
}
