package Main;

import Components.Lighting;
import com.jme3.math.Vector3f;
import com.jme3.input.KeyInput;
import com.jme3.math.ColorRGBA;
import de.lessvoid.nifty.Nifty;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.ActionListener;

import Simulation.Simulation;
import Simulation.PersonPicker;
import GUI.StartScreenController;
import Environment.Locator;
import Simulation.HudText;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.List;
/**
 * @author chris, rob, jurismo, savi
 */
public class Main extends SimpleApplication {
    private final BulletAppState bState = new BulletAppState();
    private final Simulation simulation = new Simulation();
    private boolean start=false;
    private Nifty nifty;
    private StartScreenController screenControl;
    private BitmapText ch;

    public static void main(String[] args) {
        new Main().start();
    }

    public Main() {
        //super(new FlyCamAppState());
    }
    
    @Override
    public void simpleInitApp() {
        
        inputManager.addMapping("Pause Game", new KeyTrigger(KeyInput.KEY_P));
        ActionListener pause = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                screenControl.GoTo("pause");
                screenControl.hideHudComp();
                guiNode.detachChild(ch);
                inputManager.setCursorVisible(true);
            }
        };
        inputManager.addListener(pause, new String[]{"Pause Game"});
        
        inputManager.addMapping("Esc Pause Game", new KeyTrigger(KeyInput.KEY_E));
        ActionListener escPause = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                guiNode.attachChild(ch);
                screenControl.showHudComp();
                inputManager.setCursorVisible(false);
                nifty.gotoScreen("hud");
            }
        };
        inputManager.addListener(escPause, new String[]{"Esc Pause Game"});
        setDisplayStatView(false);
        setDisplayFps(false);
        Locator.provideApplication(this);

                
        initNiftyGUI();
        screenControl.setHudImage(assetManager, settings);
        screenControl.setHudText(settings,guiFont);
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        //bState.setDebugEnabled(true);
        stateManager.attach(bState);
        flyCam.setMoveSpeed(50);
                
        cam.setLocation(new Vector3f(20, 20, 5));
        //simulation.start(100, assetManager, bState, rootNode, viewPort);
    }

    @Override
    public void simpleUpdate(float tpf) {

        if(start){screenControl.updateText();}
        simulation.step(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm){

    }

    private void initNiftyGUI() {
        
        //set cursor visible on init GUI
        flyCam.setEnabled(false);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        //stateManager.attach(startScreenState);
        
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
            assetManager,
            inputManager,
            audioRenderer,
            guiViewPort
        );

        nifty = niftyDisplay.getNifty();
        screenControl = new StartScreenController(nifty, flyCam, inputManager, guiNode, o -> startSimulation(o));
        nifty.fromXml("Interface/Screen.xml", "start", screenControl);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        // this is the command to switch GUI nifty.gotoScreen("hud");
        
        screenControl.initHudText(guiFont);
       
    
    }

    private void initCrossHairs() {
        //guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/PhetsarathOT.fnt");
        ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");        // fake crosshairs
        ch.setLocalTranslation( // center
        settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
        settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }
    
    
    public void startSimulation(StartScreenController.Options options) {
        // int numPerson = startScreenState.loadP();
        // int noMask = startScreenState.getNoMask();
        // Mask.MaskProtection protection = startScreenState.getMaskP();
        initCrossHairs();
        simulation.start(options.nPerson, options.nMasks, options.protection);
        screenControl.loadSimulation(simulation);
        start = true;
        PersonPicker picker = new PersonPicker(this);
        new Lighting();
    }
}
