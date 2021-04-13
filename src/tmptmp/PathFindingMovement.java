package tmptmp;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import com.jme3.math.Vector3f;
//import Simulation.Entity;
import Components.PathFinderExecutor;

public class PathFindingMovement implements MovementComponent {
    private final Entity entity;

    private enum State {
        NO_MORE_WAYPOINTS,
        FOLLOW_WAYPOINT,
        AT_WAYPOINT,
    }
    private State state = State.NO_MORE_WAYPOINTS;
    private Vector3f currPoint;
    private int currIndex = 0;
    private List<Vector3f> points = new ArrayList<>();
    private long start;
    private Future<List<Vector3f>> pointsFuture;
    private final PathFinderExecutor calc;

    public PathFindingMovement(final Entity p, PathFinderExecutor pc) {
        this.entity = p;
        this.calc = pc;
    }

    @Override
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

    private void finishedWaypoints() {
        if (pointsFuture == null) {
            pointsFuture = calc.requestVector3f(entity.getPos());
        }

        if (pointsFuture.isDone()) {
            currIndex = 0;
            try {
                points = pointsFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (points == null) {
                return;
            }
            currPoint = points.get(currIndex);
            start = System.currentTimeMillis();
            currIndex++;
            state = State.FOLLOW_WAYPOINT;
        }
    }

    private void followWaypoint() {
        assert currPoint == null;
        Vector3f currPos = entity.getPos();
        if (currPos.distance(currPoint) <= 1) {
            entity.stopMoving();
            state = State.AT_WAYPOINT;
            start = System.currentTimeMillis();
        } else {
            Vector3f dist = currPoint.subtract(currPos);
            entity.adjustPosition(dist);
            if (System.currentTimeMillis() - start > 10000) {
                state = State.NO_MORE_WAYPOINTS;
                pointsFuture = null;
                start = 0;
            }
        }
    }

    private void atWaypoint() {
        if (points.isEmpty() || currIndex == points.size()) {
            state = State.NO_MORE_WAYPOINTS;
            pointsFuture = null;
        } else {
            currPoint = points.get(currIndex);
            currIndex++;
            state = State.FOLLOW_WAYPOINT;
        }
    }
}
