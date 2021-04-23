package Simulation;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public interface Entity {
    /**
     * Entity update method.
     * This is called every frame to update entity position.
     * @param tpf time per frame
     */
    void update(float tpf);

    /**
     * A getter for a spatial.
     * Spatials are the lowest level representation in JME. Every
     * entity must have one to be shown.
     * @return The spatial the entity is holding.
     */
    Spatial getSpatial();

    /**
     * Getter for entity's position.
     * @return A 3D vector describing the position.
     */
    Vector3f getPosition();

    /**
     * Setter for entity's position.
     * Mostly used in world generation.
     * @param pos the new position for the entity.
     */
    void setPosition(Vector3f pos);

    /** Getter for entity ID.
     * An ID describes what the actual entity is. It
     * can be used to do safe casts when the actual concrete
     * entity is needed.
     * Unfortunately we didn't have time to add other kinds of
     * entities, so the ID enum holds a single value.
     */
    ID getID();

    enum ID {
        PERSON,
    }
}
