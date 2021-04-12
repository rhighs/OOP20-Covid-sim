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
import Simulation.PersonPicker;
import GUI.StartScreenController;

import Dependency.DependencyHelper;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Ray;

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
        inputManager.addMapping("Pause Game", new KeyTrigger(KeyInput.KEY_P));
        ActionListener pause = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                nifty.gotoScreen("pause");
                startScreenState.setLabelInf(simulation.getInfectedNumb());
            }
        };
        inputManager.addListener(pause, new String[]{"Pause Game"});
        
        inputManager.addMapping("Esc Pause Game", new KeyTrigger(KeyInput.KEY_E));
        ActionListener escPause = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                nifty.gotoScreen("hud");
            }
        };
        inputManager.addListener(escPause, new String[]{"Esc Pause Game"});
        setDisplayStatView(false);
        setDependencies();
                
        initNiftyGUI();
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState.setDebugEnabled(true);
        stateManager.attach(bState);
        flyCam.setMoveSpeed(50);
                
        cam.setLocation(new Vector3f(20, 20, 5));
        //simulation.start(100, assetManager, bState, rootNode, viewPort);
    }
    
    public void setDependencies(){
        //setting dependencies
        DependencyHelper.setDependency("rootNode", rootNode);
        DependencyHelper.setDependency("assetManager", assetManager);
        DependencyHelper.setDependency("stateManager", stateManager);
        DependencyHelper.setDependency("bulletAppState", bState);
        DependencyHelper.setDependency("viewPort", viewPort);
        DependencyHelper.setDependency("assetManager", assetManager);
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
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.Blue);                             // font color
        hudText.setText("Press [P] to pause");
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        guiNode.attachChild(hudText);
    }

    
    private void initCrossHairs() {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/PhetsarathOT.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
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
    // public void startSimulation(int numPerson) {
    //     simulation.start(numPerson, assetManager, bState, rootNode, this.getViewPort());

    // this method is called by StartScreenController
    }
}
