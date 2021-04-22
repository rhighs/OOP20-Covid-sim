package Components;

import java.util.List;
import java.util.Random;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.ai.navmesh.NavMeshPathfinder;

public class PathGenerator {

    public NavMesh nav;
    public NavMeshPathfinder pathFinder;
    public Random rand;

    public PathGenerator(final NavMesh navMesh) {
        rand = new Random();
        nav = navMesh;
    }
    
    public PathGenerator(final Spatial scene) {
        rand = new Random();
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        nav = new NavMesh(mesh);
        pathFinder = new NavMeshPathfinder(nav);
    }

    public List<Waypoint> getPath(final Vector3f start, final Vector3f target) {
        pathFinder.clearPath();
        pathFinder.setPosition(start);
        boolean success;

        success = pathFinder.computePath(target);

        return success == true ? pathFinder.getPath().getWaypoints() : null;
    }

    // returns a random 3d point inside the navmesh, so we are sure it's somehow reachable
    public Vector3f getRandomPoint() {
        int idx = rand.nextInt(nav.getNumCells());
        
        Vector3f v = new Vector3f(nav.getCell(idx).getRandomPoint());
        
        return v;
    }
}
