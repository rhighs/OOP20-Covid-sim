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
    static SimpleApplication _app;
    static Graphics graphics;
    static Physics physics;
    static Ambient ambient;
    static MainMap map;
    static Input input;
    
    static public void provideApplication(SimpleApplication app){
        _app = app;
        
        Node rootNode = _app.getRootNode();
        AssetManager assetManager = _app.getAssetManager();
        BulletAppState bullet = new BulletAppState();
        app.getStateManager().attach(bullet);

        graphics = new Graphics(assetManager, rootNode);
        physics = new Physics(bullet);
        ambient = new Ambient(assetManager, rootNode, _app.getViewPort());
        map = new MainMap(assetManager, bullet, rootNode);
        input = new Input(_app.getInputManager());
    }
    
    static public Graphics getGraphics(){
        return graphics;
    }
    
    static public Physics getPhysics(){
        return physics;
    }
    
    static public Ambient getAmbient(){
        return ambient;
    }
    
    static public MainMap getMap(){
        return map;
    }
    
    static public Input getInput(){
        return input;
    }
}
