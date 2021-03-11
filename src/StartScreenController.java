/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Simulation.Virus;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.scene.plugins.fbx.node.FbxNode;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
/**
 *  
 *  
 *
 * @author json 
 */
public class StartScreenController extends BaseAppState implements ScreenController{
    
    private Nifty nifty;
    private FlyByCamera flyCam;
    private InputManager inputManager;
    private int numPerson;
    private App app;
    
    StartScreenController(Nifty nifty) {
        this.nifty = nifty;
    }

    StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
    }
    
     StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, int numPerson, App app) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.numPerson = numPerson;
        this.app = app;
     }
     
    @Override
    protected void initialize(Application app) {
        //It is technically safe to do all initialization and cleanup in the         
        //onEnable()/onDisable() methods. Choosing to use initialize() and         
        //cleanup() for this is a matter of performance specifics for the         
        //implementor.        
        //TODO: initialize your AppState, e.g. attach spatials to rootNode    
    }
    
    @Override 
    protected void cleanup(Application app) {
        //TODO: clean up what you initialized in the initialize method,        
        //e.g. remove all spatials from rootNode    
    }
    //onEnable()/onDisable() can be used for managing things that should     
    //only exist while the state is enabled. Prime examples would be scene     
    //graph attachment or input listener attachment.    
    @Override 
    protected void onEnable() {
        //Called when the state is fully enabled, ie: is attached and         
        //isEnabled() is true or when the setEnabled() status changes after the         
        //state is attached.    
    }
    
    @Override
    protected void onDisable() {
        //Called when the state was previously enabled but is now disabled         
        //either because setEnabled(false) was called or the state is being         
        //cleaned up.    
    }
    @Override
    public void update(float tpf) { 
         
    } 

    @Override
    public void bind(Nifty arg0, Screen arg1) {
        System.out.println("StartScreenController.bind()");
    }

    @Override
    public void onStartScreen() {
        //debug
        System.out.println("StartScreenController.onStartScreen()");
        TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        textField.setText("5");
    }

    @Override
    public void onEndScreen() {
        System.out.println("StartScreenController.onEndScreen()");
    }
    
    public void startGame(String screen) {
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        // get an element 
        TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        var text = textField.getRealText();
        numPerson = Integer.parseInt(text);
        app.startApp();
        nifty.gotoScreen(screen);
  }
  public void load(){
      
  }
  
  public int loadP (){
      return numPerson;
  }
  public void quitGame() {
    getApplication().stop();
  }
}
