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
    
    static public void provideApplication(SimpleApplication app){
        _app = app;
        graphics = new Graphics(_app.getAssetManager(), _app.getRootNode());
        /*
            here i get the bstate by extracting it from the state manager,
            since we attach attach it to the statemanager in Main/Main it is required to do so.
            to get rid of the initialization of the bstate in main we need to perform its initialization here.
        
            var b = new BulletAppState();
            _app.getStateManager().attach(b);
        
            then pass it to physics...
        */
        physics = new Physics(_app.getStateManager().getState(BulletAppState.class));
    }
    
    static Graphics getGraphics(){
        return graphics;
    }
    
    static Physics getPhysics(){
        return physics;
    }
}
