package Simulation.CrowdHandlers;

import java.util.Random;
import com.jme3.math.Vector3f;

public class Spawner {
    private static final Vector3f spawnPoints[] = {
        new Vector3f(10.507881f, 2.9698987f, 114.997314f),
        new Vector3f(-50.79747f, 4.737642f, 166.79053f),
        new Vector3f(70.552925f, 3.3783529f, 166.85222f),
        new Vector3f(16.212626f, 3.3799171f, 161.2333f),
        new Vector3f(30.818537f, 2.2003968f, 47.216175f),
        new Vector3f(-19.870083f, 1.3123372f, 21.745106f),
        new Vector3f(28.811577f, 2.0368135f, 112.71367f),
        new Vector3f(-24.593493f, 4.144818f, -2.7742112f),
        new Vector3f(-74.77427f, 3.9840486f, 14.256519f),
        new Vector3f(-73.42941f, 3.3356795f, -29.485258f),
        new Vector3f(-14.387127f, 4.256944f, -48.617405f),
        new Vector3f(51.59857f, 4.9895287f, -46.860672f),
        new Vector3f(58.89262f, 3.5334468f, -20.10649f),
        new Vector3f(-32.95941f, 6.0867987f, 35.902355f),
        new Vector3f(-39.950603f, 4.0083995f, 73.206985f)
    };

    public static Vector3f getRandom() {
        Random rng = new Random();
        return spawnPoints[rng.nextInt(spawnPoints.length)];
    }
}
