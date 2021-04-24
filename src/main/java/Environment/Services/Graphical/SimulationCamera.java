package Environment.Services.Graphical;

import Simulation.Entity;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * @author rob
 */
public class SimulationCamera {

    private final Camera cam;

    private final FlyByCamera flyCam;

    private Entity attachedEntity;

    private final float MOVE_SPEED = 30f;

    public SimulationCamera(final Camera cam, final FlyByCamera flyCam) {
        this.cam = cam;
        this.flyCam = flyCam;
        flyCam.setMoveSpeed(MOVE_SPEED);
    }

    public void update() {
        followEntity();
    }

    public Vector3f getLocation() {
        return cam.getLocation();
    }

    public Vector3f getDirection() {
        return cam.getDirection();
    }

    public void attachToEntity(final Entity entity) {
        this.attachedEntity = entity;
    }

    public void detachEntity() {
        if (this.attachedEntity != null) {
            this.attachedEntity = null;
        }
    }

    private void followEntity() {
        if (attachedEntity == null) {
            return;
        }

        var pos = attachedEntity.getPosition();
        var fixedPos = new Vector3f(pos.x, pos.y + 5, pos.z);
        cam.setLocation(fixedPos);
    }

}