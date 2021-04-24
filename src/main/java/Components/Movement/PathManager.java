package Components.Movement;

import com.jme3.ai.navmesh.Path.Waypoint;

/**
 * A path manager.
 */
public interface PathManager {

    /**
     * When invoked returns the last waypoint.
     *
     * @return The last waypoint or null if not available.
     */
    Waypoint getWaypoint();

    /**
     * Requests a new path.
     */
    void requestNewPath();

    /**
     * Sets a new position for the component.
     *
     * @param waypoint The new position or null if the component should not move.
     */
    void setPosition(Waypoint waypoint);

    /**
     * Checks if the given position is near the current component position.
     *
     * @param waypoint The position to check.
     * @return True if is near, false otherwise.
     */
    Boolean isPositionNear(Waypoint waypoint);

    /**
     * Checks if the new path is ready for the use.
     *
     * @return True if is available and ready, false otherwise.
     */
    Boolean isPathReady();

    /**
     * Checks if the path was already requested.
     *
     * @return True if was already requested, false otherwise (including if was requested and already used).
     */
    Boolean isPathRequested();

    /**
     * Skips the current waypoint.
     */
    void nextWaypoint();
}
