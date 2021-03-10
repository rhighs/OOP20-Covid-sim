package Main;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;

import Simulation.Simulation;

/**
 * @author chris, rob, jurismo, savi
 */
public class Main extends SimpleApplication {
    private BulletAppState bState;
    private Simulation simulation;

    public static void main(String[] args) {
        new Main().start();
    }

    public Main() {
        //super(new FlyCamAppState());
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState = new BulletAppState();
        bState.setDebugEnabled(true);
        stateManager.attach(bState);
        //Assets.loadAssets(assetManager);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        simulation = new Simulation(assetManager, bState, rootNode, this.getViewPort());

    }

    @Override
    public void simpleUpdate(float tpf) {
        simulation.step(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
