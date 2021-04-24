package Components.Physics;

import Simulation.Entity;
import com.jme3.math.Vector3f;
import java.util.Set;

public interface PhysicsComponent {

    /**
     * Initializes a GhostControl around the spatial object,
     * the GhostControl is used to detect nearby entities to the current entity.
     * @param size the size of box side
     */
    void initProximityBox(float size);

    /**
     * @return a Set containing near entities using the GhostControl defined in the implementation.
     */
    Set<Entity> getNearEntities();

    /**
     * @return a Vector3f containing the entity's position.
     */
    Vector3f getPosition();

    /**
     * Sets the position of the current entity.
     * @param point
     */
    void setPosition(Vector3f point);

    /**
     * updates any desired field/method in the implementation.
     */
    void update();
}
