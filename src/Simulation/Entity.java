package Simulation;

import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

public interface Entity {
    public void update(float tpf);
    public Spatial getSpatial();
    public void collision();
    public void setPosition(Vector3f pos); // used for world generation
    public Vector3f getPosition();

    public static enum Identificator {
        PERSON, WALL, UNKNOWN
    }

    public Identificator getIdentificator();
}
