package Simulation;

/**
 *
 * @author simon
 */
public class Mask{

    private MaskProtection protection;
    private MaskStatus status;

    public Mask(MaskProtection p, MaskStatus s) {
        this.protection = p;
        this.status = s;
    }

    public static Mask create(MaskProtection p, MaskStatus s) {
        return new Mask(p, s);
    }

    @Override
    public MaskProtection getProtection() {
        return this.protection;
    }

    @Override
    public MaskStatus getStatus() {
        return this.status;
    }

    @Override
    public void maskDown() {
        this.status = MaskStatus.DOWN;
    }
}
