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
import java.util.List;

/**
 *
 * @author rob
 */
public class PathFinder {
    private final NavMeshPathfinder navigation;
    private final NavMesh navMesh;
    private final List<Waypoint> wayPoints;
    private MotionPath mp;
    private SimpleApplication app;
    
    public PathFinder(Spatial scene){
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        
        Mesh mesh = geom.getMesh();
        navMesh = new NavMesh(mesh);
        navigation = new NavMeshPathfinder(navMesh);
        wayPoints = new ArrayList<Waypoint>();
        mp = new MotionPath();
    }
    
    public void SetStartingPoint(final Vector3f start){
        navigation.setPosition(start);
    }
    
    public List<Waypoint> computePath(final Vector3f target){
        mp.clearWayPoints();
        wayPoints.clear();
        navigation.computePath(target);
        
        wayPoints.addAll(navigation.getPath().getWaypoints());
        
        navigation.clearPath();
        
        for(var w : wayPoints){
            mp.addWayPoint(w.getPosition());
        }
        
        
        
        return wayPoints;
    }
    
    public void setDebugEnabled(boolean value, final SimpleApplication app){
        mp.enableDebugShape(app.getAssetManager(), app.getRootNode());
    }
}
