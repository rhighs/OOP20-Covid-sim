package Simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public interface Entity {
    /**
     * Updates entity's components.
     */
    void update();

    Spatial getSpatial();

    Vector3f getPosition();

    void setPosition(Vector3f pos); // used for world generation

    Identificator getIdentificator();

    enum Identificator {
        PERSON, WALL, UNKNOWN
    }
}
