package Components;

import java.util.List;
import java.util.ArrayList;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.concurrent.Future;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;

import Environment.Locator;
import Environment.MainMap;

public class MovementComponent {
    private Spatial spatial;
    private BetterCharacterControl spatialControl;
    private Waypoint currPoint;
    private int currIndex = 0;
    private List<Waypoint> wayPoints = new ArrayList<>();
    private MainMap map = Locator.getMap();

    private long start;

    private Future<List<Waypoint>> wayPointsFuture;

    private PathCalculator pathCalc;

    private enum State {
        NO_MORE_WAYPOINTS,
        FOLLOW_WAYPOINT,
        AT_WAYPOINT,
    }
    State state = State.NO_MORE_WAYPOINTS;

    public MovementComponent(final Spatial spatial) {
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);

        this.pathCalc = map.createPathCalculator();
    }

    private void finishedWaypoints() {
        //create a new path
        if (wayPointsFuture == null) {
            wayPointsFuture = pathCalc.request(spatial.getLocalTranslation());
        }

        //get a new point
        if (wayPointsFuture.isDone()) {
            currIndex = 0;
            try {
                wayPoints = wayPointsFuture.get();
                if (wayPoints == null) {
                    return;
                }
                currPoint = wayPoints.get(currIndex);
            } catch (Exception e) {
                System.out.println("caught an exception here");
                System.exit(1);
            }
            start = System.currentTimeMillis();
            currIndex++;
            state = State.FOLLOW_WAYPOINT;
        }
    }

    private void followWaypoint() {
        if (currPoint == null) {
            System.out.println("currPoint is null");
            System.exit(1);
        }
        Vector3f currPointVector = currPoint.getPosition();
        // if we're very near to the waypoint, change state to pick a new one
        if (spatial.getLocalTranslation().distance(currPointVector) <= 1) {
            this.spatialControl.setWalkDirection(Vector3f.ZERO);
            this.state = State.AT_WAYPOINT;
            start = System.currentTimeMillis();
        } else {
            // continue getting nearer
            Vector3f v = currPointVector.subtract(spatial.getLocalTranslation());
            spatialControl.setWalkDirection(v.normalize().mult(10));
            spatialControl.setViewDirection(v.negate());

            //if someone gets stuck we create a new path
            if (System.currentTimeMillis() - start > 10000) {
                this.state = State.NO_MORE_WAYPOINTS;
                wayPointsFuture = null;
                start = 0;
            }
        }
    }

    private void atWaypoint() {
        // pick a new waypoint to follow if there are still some
        if (wayPoints.isEmpty() || currIndex == wayPoints.size()) {
            //System.out.println("change to no more waypoints state");
            this.state = State.NO_MORE_WAYPOINTS;
            wayPointsFuture = null;
        } else {
            this.currPoint = wayPoints.get(currIndex);
            currIndex++;
            this.state = State.FOLLOW_WAYPOINT;
        }
    }

    public void update(float tpf) {
        switch (state) {
            case NO_MORE_WAYPOINTS:
                finishedWaypoints();
                break;
            case FOLLOW_WAYPOINT:
                followWaypoint();
                break;
            case AT_WAYPOINT:
                atWaypoint();
                break;
        }
    }
}
