package Environment;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;

/**
 *
 * @author rob
 */
public class Locator {
    private SimpleApplication _app;
    private Graphics graphics;
    private Physics physics;
    private Ambient ambient;
    private MainMap map;
    private Input input;
    
    public void provideApplication(SimpleApplication app){
        _app = app;
        
        var rootNode = _app.getRootNode();
        var assetManager = _app.getAssetManager();
        var bullet = new BulletAppState();
        app.getStateManager().attach(bullet);

        graphics = new Graphics(assetManager, rootNode);
        physics = new Physics(bullet);
        ambient = new Ambient(assetManager, rootNode, _app.getViewPort());
        map = new MainMap(assetManager, bullet, rootNode);
        input = new Input(_app.getInputManager());
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
}
