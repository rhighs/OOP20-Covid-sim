/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Components;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author rob
 */
public class PathCalculator {

    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private NavMesh nav;
    PathGenerator pathGen;

    public PathCalculator(final NavMesh navMesh) {
        nav = navMesh;
    }

    public Future<List<Waypoint>> request(final Vector3f currentPos) {
        return pool.submit(new PathGeneratorCall(new PathGenerator(nav), currentPos));
    }

}

class PathGeneratorCall implements Callable<List<Waypoint>> {

    final Vector3f startingPoint;
    PathGenerator pathGen;

    public PathGeneratorCall(final PathGenerator pathGen, final Vector3f pos) {
        this.pathGen = pathGen;
        this.startingPoint = pos;
    }

    public List<Waypoint> call() throws Exception {
        var r = pathGen.getRandomPoint();
        System.out.println(startingPoint + " " + r);
        return pathGen.getPath(startingPoint, pathGen.getRandomPoint());
    }
}
