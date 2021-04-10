package tmptmp;

import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;

public interface Entity {
    // used by components
    Spatial getSpatial();
    Vector3f getPos();
    void stopMoving();
    void adjustPosition(Vector3f distance);
    void update(float tpf);
}
