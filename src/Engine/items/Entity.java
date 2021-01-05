package Engine.items;

import com.jme3.scene.Spatial;

public interface Entity {
    public enum Identificator { // find a better name!
        PERSON, WALL, UNKNOWN
    }
    void    update(float tpf);
    Spatial getSpatial();
    void    collision();
    Identificator getIdentificator();
}
