package Simulation;

/**
 *
 * @author simon, rob, chris
 */
public interface IPerson {
    public Mask getMask();
    public void wearMask(Mask m);
    public void maskDown();
    public boolean isInfected();
    public void infect();
}
