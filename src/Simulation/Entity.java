package Simulation;

import com.jme3.scene.Spatial;

public interface Entity {
    public enum Identificator { // find a better name!
        PERSON, WALL, UNKNOWN
    }
    public void    update();
    public Spatial getSpatial();
    public void    collision(Entity e);
    public Identificator getIdentificator();
}
