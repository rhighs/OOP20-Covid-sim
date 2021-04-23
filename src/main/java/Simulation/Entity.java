package Simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public interface Entity {
    void update(float tpf);
    void setPosition(Vector3f pos);
    Spatial getSpatial();
    Vector3f getPosition();
    Identificator getIdentificator();

    enum Identificator {
        PERSON, WALL, UNKNOWN
    }
}
