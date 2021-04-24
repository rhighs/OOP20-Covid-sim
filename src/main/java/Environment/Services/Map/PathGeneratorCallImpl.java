package Environment.Services.Map;

import com.jme3.ai.navmesh.Path;
import com.jme3.math.Vector3f;

import java.util.List;
import java.util.concurrent.Callable;

class PathGeneratorCallImpl implements Callable<List<Path.Waypoint>>, PathGeneratorCall {

    final Vector3f startingPoint;
    PathFinderImpl pathGen;

    public PathGeneratorCallImpl(final PathFinderImpl pathGen, final Vector3f pos) {
        this.pathGen = pathGen;
        this.startingPoint = pos;
    }

    @Override
    public List<Path.Waypoint> call() {
        return pathGen.getPath(startingPoint, pathGen.getRandomPoint());
    }
}
