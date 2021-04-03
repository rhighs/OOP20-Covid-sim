package tmptmp;

import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

public interface Entity {
    // used by components
    Spatial getSpatial();
    Vector3f getPos();
    void stopMoving();
    void adjustPosition(Vector3f distance);

    // used by infection algorithm
    public Mask getMask();
    public void wearMask(Mask m);
    public void maskDown();
    public boolean isInfected();
    public void infect();

    void update(float tpf);
}
