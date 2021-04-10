/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        graphics = new Graphics(assetManager, rootNode);
        /*
            here i get the bstate by extracting it from the state manager,
            since we attach attach it to the statemanager in Main/Main it is required to do so.
            to get rid of the initialization of the bstate in main we need to perform its initialization here.
        
            var b = new BulletAppState();
            _app.getStateManager().attach(b);
        
            then pass it to physics...
        */
        app.getStateManager().attach(bullet);
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
