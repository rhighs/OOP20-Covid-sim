/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.movement;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author rob
 */
public class PathFinder {

    private NavMeshPathfinder navigation;
    private final NavMesh navMesh;
    private MotionPath mp;
    private SimpleApplication app;
    private Random rand;
    private Vector3f startingPos;

    public PathFinder(Spatial scene) {
        
        rand = new Random();
        
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        navMesh = new NavMesh(mesh);
        
        navigation = new NavMeshPathfinder(navMesh);
    }

    public void SetStartingPoint(final Vector3f start) {
        this.startingPos = start;
    }

    public List<Waypoint> computePath(final Vector3f target) {
        
        navigation.clearPath();
        navigation.setPosition(startingPos);
        navigation.warpInside(target);
        
        boolean success = navigation.computePath(target);
        
        return success ? navigation.getPath().getWaypoints() : Collections.EMPTY_LIST;
    }

    public Vector3f getRandomPoint() {
        int idx = rand.nextInt(navMesh.getNumCells());
        return navMesh.getCell(idx).getRandomPoint();
    }

    public void setDebugEnabled(boolean value, final SimpleApplication app) {
        mp.enableDebugShape(app.getAssetManager(), app.getRootNode());
    }
}
