package Main;

import com.jme3.math.Vector3f;
import com.jme3.input.KeyInput;
import com.jme3.math.ColorRGBA;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.ActionListener;
import de.lessvoid.nifty.Nifty;

import Environment.Locator;
import Simulation.Simulation;
import GUI.StartScreenController;
/**
 * @author chris, rob, jurismo, savi
 */
public class Main extends SimpleApplication {
    private final BulletAppState bState = new BulletAppState();
    private Simulation simulation;
    private Locator world;
    private boolean start = false;
    private Nifty nifty;
    private StartScreenController screenControl;
    private BitmapText ch;

    public static void main(String[] args) {
        new Main().start();
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
        world = new Locator(this);
        this.simulation = new Simulation(world);

        initNiftyGUI();
        screenControl.setHudImage(assetManager, settings);
        screenControl.setHudText(settings,guiFont);
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        flyCam.setMoveSpeed(50);

        cam.setLocation(new Vector3f(20, 20, 5));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(start){
            screenControl.updateText();
        }
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
        
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
            assetManager,
            inputManager,
            audioRenderer,
            guiViewPort
        );

        nifty = niftyDisplay.getNifty();
        screenControl = new StartScreenController(nifty, flyCam, inputManager, guiNode, o -> startSimulation(o));
        nifty.fromXml("Interface/Screen.xml", "start", screenControl);
        guiViewPort.addProcessor(niftyDisplay);
        
        screenControl.initHudText(guiFont);
    }

    private void initCrossHairs() {
        guiFont = assetManager.loadFont("Interface/Fonts/PhetsarathOT.fnt");
        ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");        // fake crosshairs
        ch.setLocalTranslation( // center
            settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
            settings.getHeight() / 2 + ch.getLineHeight() / 2, 0
        );
        guiNode.attachChild(ch);
    }


    public void startSimulation(Simulation.Options options) {
        initCrossHairs();
        screenControl.loadSimulation(simulation);
        start = true;
    }
}