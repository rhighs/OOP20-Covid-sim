package Simulation;

/**
 *
 * @author simon
 */
public interface IPerson {
    public enum Mask {
        UP, DOWN
    }
    public Mask getMask();
    public void maskDown();
    public boolean isInfected();
    public void infect();
}
