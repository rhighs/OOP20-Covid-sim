package Components;

import java.util.List;
import java.util.ArrayList;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

class PathFollower {
    private PathGenerator pathGen;
    private BetterCharacterControl spatialControl;
    private Spatial spatial;
    private Waypoint currPoint;
    private int currentIndex;
    List<Waypoint> wayPoints = new ArrayList<>();

    public PathFollower(Spatial spatial, PathGenerator pathGen) {
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        this.pathGen = pathGen;
        
        this.currentIndex = 0;
    }

    public void update() {
        if (wayPoints.isEmpty() || currentIndex == wayPoints.size()) {
            Vector3f target = pathGen.getRandomPoint();
            currentIndex = 0;
            wayPoints = pathGen.getPath(spatial.getLocalTranslation(), target);
            try {
                currPoint = wayPoints.get(currentIndex);
                System.out.println(wayPoints);
                currentIndex++;
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
        if (spatial.getLocalTranslation().distance(currPointVector) <= 1) {
            this.spatialControl.setWalkDirection(Vector3f.ZERO);
            
            if(currentIndex <= wayPoints.size()){
                currPoint = wayPoints.get(currentIndex);
                currentIndex++;
            }
        } else {
            Vector3f v = currPointVector.subtract(spatial.getLocalTranslation());
            spatialControl.setWalkDirection(v.normalize().mult(8));
            spatialControl.setViewDirection(v.negate());
        }
    }
}

