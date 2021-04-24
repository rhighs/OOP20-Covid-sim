package Components.Movement;

/**
 * An interface for the component stuck management.
 */
public interface StuckManager {

    /**
     * Checks if the component is stuck in the same position.
     *
     * @return True if is stuck, false otherwise.
     */
    Boolean isStuck();

    /**
     * Starts the checks if not already started.
     */
    void toggle();

    /**
     * Resets the checks.
     */
    void reset();
}
