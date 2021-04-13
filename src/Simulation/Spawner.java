package Simulation;

import java.util.List;
import java.util.Random;
import Environment.MainMap;
import java.util.ArrayList;
import com.jme3.math.Vector3f;

/**
 *
 * @author rob
 */
public class Spawner {
    private final List<Person> crowd;
    private final MainMap map;
    private int numPoints;
    private List<Vector3f> spawnPoints = new ArrayList<Vector3f>();

    public Spawner(final MainMap map, final List<Person> crowd){
        this.crowd = new ArrayList<>();
        this.crowd.addAll(crowd);
        this.map = map;
    }

    public void setRandomSpawnPoints(int numPoints){
        this.numPoints = numPoints;
        var nav = map.getNavFromScene();
        Random rng = new Random();
        for(int i = 0; i < numPoints; i++){
            int randIdx = rng.nextInt(nav.getNumCells());
            var randomPoint = nav.getCell(randIdx).getRandomPoint();
            spawnPoints.add(randomPoint);
        }
    }

    public void distributeAcross(){
        if (spawnPoints == null){
            return;
        }
        int numPeople = crowd.size();
        int perPoint = numPeople / numPoints;

        if (perPoint == 0){
            try {
                for(int i = 0; i < numPoints; i++){
                    var point = spawnPoints.get(i);
                    crowd.get(i).setPosition(point);
                }
                crowd.removeAll(crowd);
            } catch(NullPointerException ex) {
               ex.printStackTrace();
               return;
            }
        }

        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < perPoint; j++) {
                try {
                    var person = crowd.get(j);
                    var point = spawnPoints.get(i);
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
