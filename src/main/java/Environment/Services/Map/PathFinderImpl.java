package Environment.Services.Map;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PathFinderImpl implements PathFinder {

    public NavMesh nav;
    public NavMeshPathfinder pathFinder;
    public Random rand;

    public PathFinderImpl(final Spatial scene) {
        rand = new Random();
        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");
        Mesh mesh = geom.getMesh();
        nav = new NavMesh(mesh);
        pathFinder = new NavMeshPathfinder(nav);
    }

    @Override
    public List<Waypoint> getPath(final Vector3f start, final Vector3f target) {
        pathFinder.clearPath();
        pathFinder.setPosition(start);
        boolean success;

        success = pathFinder.computePath(target);

        return success ? pathFinder.getPath().getWaypoints() : Collections.emptyList();
    }

    @Override
    public Vector3f getRandomPoint() {
        var idx = rand.nextInt(nav.getNumCells());
        return new Vector3f(nav.getCell(idx).getRandomPoint());
    }
}
