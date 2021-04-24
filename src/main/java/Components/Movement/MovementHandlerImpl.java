package Components.Movement;

import Environment.Services.Map.MainMap;
import Environment.Services.Map.MainMapImpl;
import com.jme3.scene.Spatial;

/**
 * An implementation for the movement handler.
 */
public class MovementHandlerImpl implements MovementHandler {

    private final PathManager pathManager;

    private final StuckManager stuckManager;

    /**
     * When invoked creates a new instance with the default stuck manager.
     *
     * @param map     The main map.
     * @param spatial The spatial instance.
     */
    public MovementHandlerImpl(final MainMap map, final Spatial spatial) {
        this(new PathManagerImpl(map, spatial), new SystemTimeStuckManager());
    }

    /**
     * When invoked creates a new instance with a custom stuck manager (inversion of control).
     * For testing purposes.
     *
     * @param stuckManager The stuck manager implementation instance.
     */
    public MovementHandlerImpl(final PathManager pathManager, final StuckManager stuckManager) {
        this.pathManager = pathManager;
        this.stuckManager = stuckManager;
    }

    @Override
    public void update() {

        final var waypoint = pathManager.getWaypoint();

        if (waypoint == null && !pathManager.isPathRequested()) {
            pathManager.requestNewPath();
            return;
        }

        if (waypoint == null && !pathManager.isPathReady()) {
            return;
        }

        if (stuckManager.isStuck()) {
            pathManager.requestNewPath();
            stuckManager.reset();
            return;
        }

        if (pathManager.isPositionNear(waypoint)) {
            pathManager.setPosition(null);
            pathManager.nextWaypoint();
            stuckManager.toggle();
            return;
        }

        pathManager.setPosition(waypoint);
    }
}
