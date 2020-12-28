package Simulation;

import com.jme3.scene.Spatial;

public interface Entity{
    public enum Names {
        PERSON, WALL, UNKNOWN
    }
    void    update();
    Spatial getSpatial();
    void    collision(Entity e);
}