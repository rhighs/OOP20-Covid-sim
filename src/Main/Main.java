package Main;

import Simulation.Mask;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.font.BitmapText;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

import Simulation.Simulation;
import Simulation.Picker;
//import GUI.StartScreenController;

/**
 * @author chris, rob, jurismo, savi
 */
public class Main extends SimpleApplication {
    private final BulletAppState bState = new BulletAppState();
    private final Simulation simulation = new Simulation();

    private Nifty nifty;
    private BitmapText hudText;
    private StartScreenController startScreenState;

    public static void main(String[] args) {
        new Main().start();
    }

    public Main() {
        //super(new FlyCamAppState());
    }

    @Override
    public void simpleInitApp() {
        initNiftyGUI();
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState.setDebugEnabled(true);
        stateManager.attach(bState);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        //simulation.start(100, assetManager, bState, rootNode, viewPort);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // hudText.setText("Infected: " + simulation.getInfectedNumb()); //!!!!! non fa l'update
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
        //startScreenState = new StartScreenController(nifty, flyCam, inputManager, n -> startSimulation(n));
        startScreenState = new StartScreenController(nifty, flyCam, inputManager, this);
        nifty.fromXml("Interface/Screen.xml", "start", startScreenState);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        // this is the command to switch GUI nifty.gotoScreen("hud");
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText("You can write any string here");             // the text
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        guiNode.attachChild(hudText);
    }

    // public void startSimulation(int numPerson) {
    //     simulation.start(numPerson, assetManager, bState, rootNode, this.getViewPort());

    // this method is called by StartScreenController
    public void startApp() {
        System.out.println("app started");
        int numPerson = startScreenState.loadP();
        int noMask = startScreenState.getNoMask();
        Mask.MaskProtection protection = startScreenState.getMaskP();
        System.out.print(numPerson);
        simulation.start(numPerson, noMask, protection, assetManager, bState, rootNode, this.getViewPort());
        Picker picker = new Picker(this, simulation.getPersonList());
        System.out.println("Main.Main.startApp()");
    }
}
