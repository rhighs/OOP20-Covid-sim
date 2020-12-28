package Simulation;

import com.jme3.scene.Spatial;

public interface Entity{
    
    public enum Identificator {
        PERSON, WALL, UNKNOWN
    }
    
    void    update();
    Spatial getSpatial();
    void    collision(Entity e, float distance);
    Identificator getIdentificator();
}