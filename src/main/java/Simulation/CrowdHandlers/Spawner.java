package Simulation.CrowdHandlers;

import Environment.Services.Map.MainMap;
import Simulation.Person;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author rob
 */
public class Spawner {
    private final List<Person> crowd;
    private final MainMap map;
    private final List<Vector3f> spawnPoints = new ArrayList<>();
    private int numPoints;

    public Spawner(final MainMap map, final List<Person> crowd) {
        this.crowd = new ArrayList<>();
        this.crowd.addAll(crowd);
        this.map = map;
    }

    public void setRandomSpawnPoints(int numPoints) {
        this.numPoints = numPoints;
        var nav = map.getNavMesh();
        var rng = new Random();
        for (int i = 0; i < numPoints; i++) {
            int randIdx = rng.nextInt(nav.getNumCells());
            Vector3f randomPoint = nav.getCell(randIdx).getRandomPoint();
            spawnPoints.add(randomPoint);
        }
    }

    public void distributeAcross() {
        if (spawnPoints == null) {
            return;
        }

        int numPeople = crowd.size();
        int perPoint = numPeople / numPoints;

        if (perPoint == 0) {
            try {
                for (int i = 0; i < numPoints; i++) {
                    Vector3f point = spawnPoints.get(i);
                    crowd.get(i).setPosition(point);
                }
                crowd.removeAll(crowd);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                return;
            }
        }

        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < perPoint; j++) {
                try {
                    Person person = crowd.get(j);
                    Vector3f point = spawnPoints.get(i);
                    person.setPosition(point);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    return;
                }
                crowd.remove(j);
            }
        }
    }
}
