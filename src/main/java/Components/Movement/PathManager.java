package Components.Movement;

import com.jme3.ai.navmesh.Path.Waypoint;

public interface PathManager {

    Waypoint getWaypoint();

    void requestNewPath();

    void setPosition(Waypoint waypoint);

    Boolean isPositionNear(Waypoint waypoint);

    Boolean isPathReady();

    Boolean isPathRequested();
}
