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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author rob
 */
public class PathGenerator{
    public NavMesh nav;
    public NavMeshPathfinder navigation;
    public Random rand;
    public Path emptyPath;
    
    public PathGenerator(final Spatial scene){
        rand = new Random();
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        
        emptyPath = new Path();
        
        nav = new NavMesh(mesh);
        navigation = new NavMeshPathfinder(nav);
    }
    
    public List<Waypoint> getPath(final Vector3f start, final Vector3f target){
        navigation.clearPath();
        navigation.setPosition(start);
        boolean success = navigation.computePath(target);
        
        
        if(success)
            return navigation.getPath().getWaypoints();
        else
            return Collections.EMPTY_LIST;
    }
    
    //returns a random 3d point inside the navmesh, so we are sure it's somehow reachable
    public Vector3f getRandomPoint(){
        var idx = rand.nextInt(nav.getNumCells());
        return nav.getCell(idx).getRandomPoint();
    }
    
}
