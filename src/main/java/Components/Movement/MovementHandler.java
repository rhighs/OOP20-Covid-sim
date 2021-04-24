package Components.Movement;

import Environment.Services.Map.MainMap;
import com.jme3.scene.Spatial;

public class MovementHandler {

    private final PathManager pathManager;

    private Boolean ignoreStuck = true;

    private long stuckStartTime;

    public MovementHandler(final MainMap map, final Spatial spatial) {
        pathManager = new PathManagerImpl(map, spatial);
    }

    public void update() {

        final var waypoint = pathManager.getWaypoint();

        if (waypoint == null && !pathManager.isPathRequested()) {
            pathManager.requestNewPath();
            return;
        }

        if (waypoint == null && !pathManager.isPathReady()) {
            return;
        }

        if (isStuck()) {
            pathManager.requestNewPath();
            ignoreStuck = true;
        }

        if (pathManager.isPositionNear(waypoint)) {
            pathManager.setPosition(null);

            if (ignoreStuck) {
                stuckStartTime = System.currentTimeMillis();
                ignoreStuck = false;
            }

            return;
        }

        pathManager.setPosition(waypoint);
    }

    private Boolean isStuck() {
        return !ignoreStuck && System.currentTimeMillis() - stuckStartTime > 10000;
    }
}
