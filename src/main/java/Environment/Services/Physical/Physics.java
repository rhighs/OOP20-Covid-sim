package Environment.Services.Physical;

public interface Physics {

    /**
     * Adds an object to the physics space, must be instantiable by a jme3 Spatial
     * @param obj the object to add to the physics space
     */
    void addToSpace(final Object obj);
}
