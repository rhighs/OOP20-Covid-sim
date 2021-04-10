package Environment;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;

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
    
    static public void provideApplication(SimpleApplication app){
        _app = app;
        
        var rootNode = _app.getRootNode();
        var assetManager = _app.getAssetManager();
        var bullet = new BulletAppState();
        app.getStateManager().attach(bullet);

        graphics = new Graphics(assetManager, rootNode);
        physics = new Physics(bullet);
        ambient = new Ambient(assetManager, rootNode, _app.getViewPort());
        map = new MainMap(assetManager, bullet, rootNode);
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
}
