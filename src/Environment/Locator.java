package Environment;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 *
 * @author rob
 */
public class Locator {
    private SimpleApplication app;
    private Graphics graphics;
    private Physics physics;
    private Ambient ambient;
    private MainMap map;
    private Input input;
    private SimulationCamera cam;
    private Node guiNode;
    
    public Locator(SimpleApplication app){
        this.app = app;
        guiNode = app.getGuiNode();
        var rootNode = app.getRootNode();
        var assetManager = app.getAssetManager();
        var bullet = new BulletAppState();
        app.getStateManager().attach(bullet);

        graphics = new Graphics(assetManager, rootNode);
        physics = new Physics(bullet);
        ambient = new Ambient(assetManager, rootNode, app.getViewPort());
        map = new MainMap(assetManager, bullet, rootNode);
        input = new Input(app.getInputManager(), app.getGuiNode());
        cam = new SimulationCamera(app.getCamera(), app.getFlyByCamera());
    }
    
    public Graphics getGraphics(){
        return graphics;
    }
    
    public Physics getPhysics(){
        return physics;
    }
    
    public Ambient getAmbient(){
        return ambient;
    }
    
    public MainMap getMap(){
        return map;
    }
    
    public Input getInput(){
        return input;
    }
    
    public Node getGuiNode(){
        return guiNode;
    }
    public SimulationCamera getSimulationCamera(){
        return cam;
    }
}
