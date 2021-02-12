package Components;

import java.util.List;
import java.util.ArrayList;
import com.jme3.scene.Spatial;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;

public class MovementComponent {
    private Spatial scene;
    private Spatial spatial;
    private BetterCharacterControl spatialControl;
    private PathGenerator pathGen;
    private Waypoint currPoint;
    private int currIndex = 0;
    private List<Waypoint> wayPoints = new ArrayList<>();

    private enum State {
        NO_MORE_WAYPOINTS,
        FOLLOW_WAYPOINT,
        AT_WAYPOINT,
    }
    State state = State.NO_MORE_WAYPOINTS;

    public MovementComponent(final Spatial spatial, final Spatial scene) {
        this.scene = scene;
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        this.pathGen = new PathGenerator(scene);
    }

    private void finishedWaypoints() {
        // create a new path
        Vector3f target = pathGen.getRandomPoint();
        wayPoints = pathGen.getPath(spatial.getLocalTranslation(), target);
        //System.out.println(wayPoints);
        // get a new point
        currIndex = 0;
        try {
            currPoint = wayPoints.get(currIndex);
            currIndex++;
        } catch (UnsupportedOperationException e) {
            System.out.println("caught an exception here");
            System.out.println("no wayPoints: " + wayPoints.size() + ", target: " + target.getX() + "," + target.getY() + "," + target.getZ());
            System.exit(1);
        }
        //System.out.println(", target: " + target.getX() + "," + target.getY() + "," + target.getZ());
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
        } else {
            // continue getting nearer
            Vector3f v = currPointVector.subtract(spatial.getLocalTranslation());
            spatialControl.setWalkDirection(v.normalize().mult(8));
            spatialControl.setViewDirection(v.negate());
        }
    }

    private void atWaypoint() {
        // pick a new waypoint to follow if there are still some
        if (wayPoints.isEmpty() || currIndex == wayPoints.size()) {
            this.state = State.NO_MORE_WAYPOINTS;
        } else {
            this.currPoint = wayPoints.get(currIndex);
            currIndex++;
            this.state = State.FOLLOW_WAYPOINT;
        }
    }

    public void update(float tpf) {
        /*
        switch (state) {
        case NO_MORE_WAYPOINTS: finishedWaypoints(); break;
        case FOLLOW_WAYPOINT:   followWaypoint();    break;
        case AT_WAYPOINT:       atWaypoint();        break;
        }
        */
    }
}

