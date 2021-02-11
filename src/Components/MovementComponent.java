package Components;

import com.jme3.scene.Spatial;

public class MovementComponent {
    private Spatial scene;
    private PathGenerator pg;
    private PathFollower pathFollower;

    public MovementComponent(final Spatial spatial, final Spatial scene) {
        this.scene = scene;
        pg = new PathGenerator(scene);
        pathFollower = new PathFollower(spatial, pg);
    }

    public void startPathFollower(){
        // pathFollower.start();
    }

    public void update(float tpf){
        pathFollower.update();
    }
}
