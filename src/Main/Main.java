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
/**
 * @author chris, rob, jurismo, savi
 */
public class Main extends SimpleApplication {
    private final BulletAppState bState = new BulletAppState();
    private final Simulation simulation = new Simulation();

    private Nifty nifty;
    private BitmapText hudText;
    private StartScreenController startScreenState;
    BitmapText ch;

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
                nifty.gotoScreen("pause");
                guiNode.detachChild(ch);
                startScreenState.setLabelInf(simulation.getInfectedNumb());
            }
        };
        inputManager.addListener(pause, new String[]{"Pause Game"});
        
        inputManager.addMapping("Esc Pause Game", new KeyTrigger(KeyInput.KEY_E));
        ActionListener escPause = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                guiNode.attachChild(ch);
                nifty.gotoScreen("hud");
            }
        };
        inputManager.addListener(escPause, new String[]{"Esc Pause Game"});
        setDisplayStatView(false);
        Locator.provideApplication(this);

                
        initNiftyGUI();
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        //bState.setDebugEnabled(true);
        stateManager.attach(bState);
        flyCam.setMoveSpeed(50);
                
        cam.setLocation(new Vector3f(20, 20, 5));
        //simulation.start(100, assetManager, bState, rootNode, viewPort);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //hudText.setText("Infected: " + simulation.getPersonCount());
        //simulation.getInfectedNumb(); //!!!!! non fa l'update
        simulation.step(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm){

    }

    private void initNiftyGUI() {
        hudText = new BitmapText(guiFont, false);
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
        startScreenState = new StartScreenController(nifty, flyCam, inputManager, o -> startSimulation(o));
        nifty.fromXml("Interface/Screen.xml", "start", startScreenState);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        // this is the command to switch GUI nifty.gotoScreen("hud");
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
        PersonPicker picker = new PersonPicker(this);
        new Lighting();
    }
}
