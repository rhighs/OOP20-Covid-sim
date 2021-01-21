package Engine.movement;

import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PathFollower { //extends Thread {
    private PathGenerator pathGen;
    private BetterCharacterControl spatialControl;
    private Spatial spatial;
    private Waypoint currPoint;
    // private Vector3f target;
    // private boolean isMoving;
    // private boolean keepMoving;
    // private Lock lock;
    // public boolean hasArrived = false;

    List<Waypoint> wayPoints = new ArrayList<>();
    Vector3f v, w, spatialPos;

    private static final long MAXIMUM_TIME = 6000L;

    public PathFollower(Spatial spatial, PathGenerator pathGen) { //, Vector3f target) {
        this.spatial = spatial;
        // what the fucking hell, why???
        // these are everywhere too. do you guys like shared state everywhere that much?
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        this.pathGen = pathGen;
        // this.isMoving = true;
        // this.keepMoving = true;
        // this.target = target;
        // lock = new ReentrantLock();
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

    //@Override
    public void run() {
        // whoever wrote this is dumb
        // dumb dumb dumb DUMB
        // there's literally no way this thread will ever be able to exit
        // while(true)
        //     movement();
    }

    public void movement() {
        // this.hasArrived = false;
        // create a new path
        // follow path
        // for (int i = 1; i < wayPoints.size(); i++) {
        //     currPoint = wayPoints.get(i).getPosition();
        //     long start = System.currentTimeMillis();
        //     long elapsed = 0;
            // the _ will walk to currPoint until either it got there or enough time has elapsed
            // while ( && elapsed < MAXIMUM_TIME) {
                // Vector3f v = currPoint.subtract(spatial.getLocalTranslation());
                // spatialControl.setWalkDirection(v.normalize().mult(8));
                // spatialControl.setViewDirection(v.negate());
                // try {
                //     Thread.sleep(200);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                // elapsed = start - System.currentTimeMillis();
            // }
            // stopWalking();
        // }
        // wayPoints.clear();
        // stopWalking();
    }

    // private void stopWalking() {
    // }

    // public void setMovementEnabled(boolean cond) {
    //     isMoving = cond;
    // }

    // public void setTarget(Vector3f target) {
    //     this.target = target;
    // }

    // public void setLoopEnabled(boolean cond) {
    //     this.keepMoving = cond;
    // }
}
