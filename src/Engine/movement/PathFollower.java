/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
package Engine.movement;

import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PathFollower extends Thread {

    private PathFinder pathFinder;
    private BetterCharacterControl spatialControl;
    private Spatial spatial;
    private Vector3f target;
    private boolean isMoving;
    private boolean keepMoving;

    private long start, elapsed;
    private static final long MAXIMUM_TIME = 6000L;

    public PathFollower(Spatial spatial, PathFinder pathFinder, Vector3f target) {
        this.spatial = spatial;
        this.spatialControl = spatial.getControl(BetterCharacterControl.class);
        isMoving = true;
        keepMoving = true;

        this.pathFinder = pathFinder;
        this.spatialControl = spatialControl;
        this.target = target;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }

            movement();
        }
    }

    public void movement() {
        var spatialPos = spatial.getLocalTranslation();
        List<Waypoint> wayPoints = new ArrayList<>();
        Vector3f v, w;

        pathFinder.SetStartingPoint(spatialPos);
        wayPoints = pathFinder.computePath(target);

        for (int i = 1; i < wayPoints.size(); i++) {
            w = wayPoints.get(i).getPosition();

            start = System.currentTimeMillis();

            while (spatial.getLocalTranslation().distance(w) >= 1 && elapsed < MAXIMUM_TIME) {
                spatialPos = spatial.getLocalTranslation();

                v = w.subtract(spatialPos);
                spatialControl.setWalkDirection(v.normalize().mult(8));
                spatialControl.setViewDirection(v.negate());

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                elapsed = start - System.currentTimeMillis();
            }

            stopWalking();

        }

        wayPoints.clear();
        stopWalking();
        target = pathFinder.getRandomPoint();
    }

    private void stopWalking() {
        this.spatialControl.setWalkDirection(Vector3f.ZERO);
    }

    public void setMovementEnabled(boolean cond) {
        isMoving = cond;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }

    public void setLoopEnabled(boolean cond) {
        this.keepMoving = cond;
    }
}

*/
