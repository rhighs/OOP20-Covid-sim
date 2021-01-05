package Engine.items;

import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

public interface Entity {
    public enum Identificator { // find a better name!
        PERSON, WALL, UNKNOWN
    }
    public void    update(float tpf);
    public Spatial getSpatial();
    public void    collision();
    public Identificator getIdentificator();
    public void    setPosition(Vector3f pos);  // used for world generation
}
