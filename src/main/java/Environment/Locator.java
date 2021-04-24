package Environment;

import Environment.Services.Graphical.Ambient;
import Environment.Services.Graphical.Graphics;
import Environment.Services.Graphical.SimulationCamera;
import Environment.Services.InputHandling.InputHandler;
import Environment.Services.InputHandling.InputHandlerImpl;
import Environment.Services.Map.MainMap;
import Environment.Services.Map.MainMapImpl;
import Environment.Services.Physical.Physics;
import Environment.Services.Physical.PhysicsImpl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 * @author rob
 */
public class Locator {
    private final SimpleApplication app;
    private final Graphics graphics;
    private final Physics physics;
    private final Ambient ambient;
    private final MainMap map;
    private final InputHandler input;
    private final SimulationCamera cam;
    private final Node guiNode;

    public Locator(SimpleApplication app) {
        this.app = app;
        guiNode = app.getGuiNode();
        var rootNode = app.getRootNode();
        var assetManager = app.getAssetManager();
        var bullet = new BulletAppState();
        app.getStateManager().attach(bullet);

        graphics = new Graphics(assetManager, rootNode);
        physics = new PhysicsImpl(bullet);
        ambient = new Ambient(assetManager, rootNode, app.getViewPort());
        map = new MainMapImpl(assetManager, bullet, rootNode);
        input = new InputHandlerImpl(app.getInputManager());
        cam = new SimulationCamera(app.getCamera(), app.getFlyByCamera());
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Physics getPhysics() {
        return physics;
    }

    public Ambient getAmbient() {
        return ambient;
    }

    public MainMap getMap() {
        return map;
    }

    public InputHandler getInput() {
        return input;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public SimulationCamera getSimulationCamera() {
        return cam;
    }
}
