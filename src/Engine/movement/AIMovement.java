/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.movement;

import Engine.NavMeshGenerator;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path;
import com.jme3.app.Application;
import com.jme3.cinematic.MotionPath;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.util.clone.JmeCloneable;
import java.io.IOException;
import com.jme3.ai.steering.behaviour.Evade;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import jme3tools.optimize.GeometryBatchFactory;

/**
 *
 * @author rob
 */
public class AIMovement extends NavMeshPathfinder {

    private Spatial spatial;
    private MotionPath path;
    private boolean pathFinding;
    private Vector3f pos;
    private Vector3f target;
    private final ScheduledExecutorService executor;

    public AIMovement(NavMesh navMesh, final Spatial spatial, final Vector3f target) {
        super(navMesh);
        this.target = target;
        this.executor = Executors.newScheduledThreadPool(1);
        
    }

    public void startPathFinding() {
        executor.scheduleWithFixedDelay(() -> {
            if (target != null) {
                clearPath();
                this.pos = null;
                pathFinding = true;
                //setPosition must be set before computePath is called.
                this.setPosition(spatial.getLocalTranslation());
                //*The first waypoint on any path is the one you set with 
                //`setPosition()`.
                //*The last waypoint on any path is always the `target` Vector3f.
                //computePath() adds one waypoint to the cell *nearest* to the 
                //target only if you are not in the goalCell (the cell target is in), 
                //and if there is a cell between first and last waypoint, 
                //and if there is no direct line of sight. 
                //*If inside the goalCell when a new target is selected, 
                //computePath() will do a direct line of sight placement of 
                //target. This means there will only be 2 waypoints set, 
                //`setPosition()` and `target`.
                //*If the `target` is outside the `NavMesh`, your endpoint will 
                //be also.
                //warpInside(target) moves endpoint within the navMesh always.
                warpInside(target);
                System.out.println("Target " + target);
                boolean success;
                //compute the path
                success = computePath(target);
                System.out.println("SUCCESS = " + success);
                if (success) {
                    //clear target if successful
                    target = null;
                }
                pathFinding = false;
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public List<Vector3f> getWayPoints() {
        var waypList = new ArrayList<Vector3f>();
        System.out.print(pathFinding);
        if (pathFinding) {
            try {
                if (executor.awaitTermination(6, TimeUnit.SECONDS)) {
                    while (this.getWaypointPosition() != target) {
                        waypList.add(getWaypointPosition());
                        this.goToNextWaypoint();
                    }
                }
            } catch (InterruptedException ie) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        return waypList;
    }

}
