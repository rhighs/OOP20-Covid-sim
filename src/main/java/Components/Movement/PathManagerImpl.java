package Components.Movement;

import Environment.Services.Map.MainMap;
import Environment.Services.Map.PathFinderExecutor;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import java.util.List;
import java.util.concurrent.Future;

/**
 * An implementation for the path manager.
 */
public class PathManagerImpl implements PathManager {

    private final PathFinderExecutor pathCalculator;

    private final BetterCharacterControl spatialControl;

    private final Spatial spatial;

    private Future<List<Waypoint>> nextPathFuture;

    private int currentWaypointIndex = 0;

    private List<Waypoint> waypoints;

    /**
     * When invoked create a new instance of the path manager implementation.
     *
     * @param map     The map.
     * @param spatial The spatial instance.
     */
    public PathManagerImpl(MainMap map, Spatial spatial) {
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        this.pathCalculator = map.createPathCalculator();
    }

    @Override
    public Waypoint getWaypoint() {
        if (waypoints == null || waypoints.isEmpty()) {
            if (!isPathReady()) {
                return null;
            }

            try {
                waypoints = nextPathFuture.get();
                nextPathFuture = null;
                currentWaypointIndex = 0;
            } catch (Exception exception) {
                return null;
            }
        }

        if (currentWaypointIndex > waypoints.size() - 1) {
            waypoints = null;
            return null;
        }

        return waypoints.get(currentWaypointIndex++);
    }

    @Override
    public void requestNewPath() {
        nextPathFuture = pathCalculator.request(spatial.getLocalTranslation());
    }

    @Override
    public void setPosition(Waypoint waypoint) {

        if (waypoint == null) {
            this.spatialControl.setWalkDirection(Vector3f.ZERO);
            return;
        }

        Vector3f v = waypoint.getPosition().subtract(spatial.getLocalTranslation());
        spatialControl.setWalkDirection(v.normalize().mult(10));
        spatialControl.setViewDirection(v.negate());
    }

    @Override
    public Boolean isPositionNear(Waypoint waypoint) {
        return spatial.getLocalTranslation().distance(waypoint.getPosition()) <= 1;
    }

    @Override
    public Boolean isPathReady() {
        if (nextPathFuture == null) {
            return false;
        }

        return nextPathFuture.isDone();
    }

    @Override
    public Boolean isPathRequested() {
        return nextPathFuture != null;
    }
}
