package Environment.Services.Map;

import com.jme3.ai.navmesh.NavMesh;

public interface MainMap {

    /**
     * @return a navigation mesh, extracted from the scene
     */
    NavMesh getNavMesh();

    /**
     * @return an instance of path finder executor
     */
    PathFinderExecutor createPathFinderExecutor();

    /**
     * @return an instance of path finder
     */
    PathFinderImpl createPathFinder();

    /**
     * Shutdown the shared path finder.
     */
    void shutdown();
}
