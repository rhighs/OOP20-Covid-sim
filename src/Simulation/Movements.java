package Simulation;

import java.util.function.Function;
import com.jme3.math.Vector3f;

/* This class is a container for many classes which implement some kind of
 * movement algorithm. (think of it as a namespace containing many free functions.
 * To add a new movement algorithm, simply create a new nested class here.
 * It must implement Function<Vector3f, Vector3f>.
 * Too many nested classes? We'll care about that later.
 */
public class Movements {

    /* SimpleWalk simply makes the person walk to one direction. */
    public static class SimpleWalk implements Function<Vector3f, Vector3f> {

        Vector3f dir;

        public SimpleWalk(Vector3f direction) {
            this.dir = direction;
        }

        public Vector3f apply(Vector3f oldPos) {
            return dir;
        }
    }
}
