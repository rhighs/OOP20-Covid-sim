package Simulation;

enum MaskStatus {
    UP,
    DOWN
}

enum MaskProtection{
    FP1,
    FP2,
    FP3
}

/**
 *
 * @author simon
 */
public class Mask{

    private MaskProtection protection;
    private MaskStatus status;
    
    public enum MaskStatus {
        UP,
        DOWN
    }

    public enum MaskProtection{
        FP1,
        FP2,
        FP3
    }

    public Mask(MaskProtection p, MaskStatus s) {
        this.protection = p;
        this.status = s;
    }

    public static Mask create(MaskProtection p, MaskStatus s) {
        return new Mask(p, s);
    }

    public MaskProtection getProtection() {
        return this.protection;
    }

    public MaskStatus getStatus() {
        return this.status;
    }

    public void maskDown() {
        this.status = MaskStatus.DOWN;
    }
}
