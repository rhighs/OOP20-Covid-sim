package Simulation;

/**
 *
 * @author simon
 */
public interface Person {
    public Mask getMask();
    public void wearMask(Mask m);
    public void maskDown();
    public boolean isInfected();
    public void infect();
}
