package Components;

import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.List;
import java.util.ArrayList;

class PathFollower {
    private PathGenerator pathGen;
    private BetterCharacterControl spatialControl;
    private Spatial spatial;
    private Waypoint currPoint;
    List<Waypoint> wayPoints = new ArrayList<>();

    public PathFollower(Spatial spatial, PathGenerator pathGen) {
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        this.pathGen = pathGen;
    }

    public void update() {
        if (wayPoints.isEmpty()) {
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
        if (currPoint == null) {
            System.out.println("currPoint is null");
            System.exit(1);
        }
        Vector3f currPointVector = currPoint.getPosition();
        if (spatial.getLocalTranslation().distance(currPointVector) >= 1) {
            this.spatialControl.setWalkDirection(Vector3f.ZERO);
            currPoint = wayPoints.remove(wayPoints.size() - 1);
        } else {
            Vector3f v = currPointVector.subtract(spatial.getLocalTranslation());
            spatialControl.setWalkDirection(v.normalize().mult(8));
            spatialControl.setViewDirection(v.negate());
        }
    }
}
