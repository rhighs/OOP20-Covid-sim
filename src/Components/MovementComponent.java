package Components;

import com.jme3.scene.Spatial;

public class MovementComponent {
    private Spatial scene;
    private Spatial spatial;
    private BetterCharacterControl spatialControl;
    private PathGenerator pathGen;
    private Waypoint currPoint;
    private List<Waypoint> wayPoints = new ArrayList<>();

    private enum State {
        NO_MORE_WAYPOINTS,
        FOLLOW_WAYPOINT,
        AT_WAYPOINT,
    }
    State state;

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
        try {
            currPoint = wayPoints.remove(wayPoints.size() - 1);
        } catch (UnsupportedOperationException e) {
            System.out.println("caught an exception here");
            System.out.println("no wayPoints: " + wayPoints.size() + ", target: " + target.getX() + "," + target.getY() + "," + target.getZ());
            System.exit(1);
        }
        System.out.println(", target: " + target.getX() + "," + target.getY() + "," + target.getZ());
    }

    private void followWaypoint() {
        if (currPoint == null) {
            System.out.println("currPoint is null");
            System.exit(1);
        }
        Vector3f currPointVector = currPoint.getPosition();
        if (spatial.getLocalTranslation().distance(currPointVector) <= 1) {
            // change state
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
        if (wayPoints.isEmpty()) {
            this.state = State.NO_MORE_WAYPOINTS;
        } else {
            this.currPoint = wayPoints.remove(wayPoints.size() - 1);
            this.state = State.FOLLOW_WAYPOINT;
        }
    }

    public void update(float tpf) {
        switch (state) {
        case NO_MORE_WAYPOINTS: finishedWaypoints(); break;
        case FOLLOW_WAYPOINT:   followWaypoint();    break;
        case AT_WAYPOINT:       atWaypoint();        break;
        }
    }
}
