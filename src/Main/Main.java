package Main;

import Components.Lighting;
import Simulation.Simulation;
import com.jme3.math.Vector3f;
import com.jme3.input.KeyInput;
import com.jme3.math.ColorRGBA;
import com.jme3.font.BitmapText;
import com.jme3.app.SimpleApplication;
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
    enum ScreenState {
        START_SCREEN,
        SIMULATION_SCREEN,
        PAUSE_SCREEN,
    }
    ScreenState state = ScreenState.START_SCREEN;

    private Locator world;
    private StartScreenController screenControl;
    private Simulation simulation;
    private BitmapText ch;
    private Nifty nifty;

    public static void main(String[] args) {
        new Main().start();
    }

    @Override
    public void simpleInitApp() {
        // keep this at the top or else we'll get exceptions.
        world = new Locator(this);
        this.simulation = new Simulation(world);
        setDisplayStatView(false);
        setDisplayFps(false);
        setupGUI();
        setupKeyMappings();
    }

    @Override
    public void simpleUpdate(float tpf) {
        switch (state) {
        case START_SCREEN: break;
        case SIMULATION_SCREEN:
            screenControl.updateText();
            simulation.step(tpf);
            break;
        case PAUSE_SCREEN: break;
        }
    }

    @Override
    public void simpleRender(RenderManager rm){
    }

    private void setupKeyMappings() {
        inputManager.addMapping("Pause Game", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                screenControl.GoTo("pause");
                screenControl.hideHudComp();
                guiNode.detachChild(ch);
                inputManager.setCursorVisible(true);
                state = ScreenState.PAUSE_SCREEN;
            }
        }, "Pause Game");

        inputManager.addMapping("Esc Pause Game", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf){
                guiNode.attachChild(ch);
                screenControl.showHudComp();
                inputManager.setCursorVisible(false);
                nifty.gotoScreen("hud");
                state = ScreenState.SIMULATION_SCREEN;
            }
        }, "Esc Pause Game");
    }

    private void setupGUI() {
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
        screenControl = new StartScreenController(nifty, flyCam, inputManager, world);
        screenControl.onStartButtonClicked(o -> startSimulation(o));
        screenControl.onQuitButtonClicked(from -> finish(from));
        nifty.fromXml("Interface/Screen.xml", "start", screenControl);
        guiViewPort.addProcessor(niftyDisplay);
        screenControl.initHudText(guiFont);
        screenControl.setHudImage(assetManager, settings);
        screenControl.setHudText(settings,guiFont);
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
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        initCrossHairs();
        simulation.start(options);
        screenControl.loadSimulation(simulation);
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        state = ScreenState.SIMULATION_SCREEN;
    }

    /* This function is called both when the user closes the window and when the user
     * presses the quit button on the GUI.
     * @fromQuitButton indicates if it came from the GUI. */
    public void finish(Boolean fromQuitButton) {
        System.err.println("exiting...");
        System.exit(0);
    }

    @Override
    public void destroy() {
        finish(false);
    }
}