package Main;

import Environment.Locator;
import GUI.Controllers.EditComponent;
import GUI.Controllers.HudText;
import GUI.Controllers.StartScreenController;
import Simulation.Simulation;
import Simulation.SimulationImpl;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This is the Main class. This class handles everything: Simulation, GUI,
 * Simulation states, etc.
 */
public class Main extends SimpleApplication {
    /**
     * Fields for Main class.
     *
     * @state: keeps track of the screen state. See above.
     * @world:
     * @screenControl: controller for the GUI class.
     * @simulation: controller for simulation.
     * @crosshair: A crosshair only seen inside the simulation screen.
     */
    private ScreenState state = ScreenState.START_SCREEN;
    private Locator world;
    private StartScreenController screenControl;
    private HudText hudControl;
    private EditComponent editComponent;
    private Simulation simulation;
    private BitmapText ch;

    public static void main(String[] args) {
        new Main().start();
    }

    /**
     * Initializes the application.
     * This initializes the world, initializes the GUI controller,
     * sets key mappings for entering/exiting the pause screen
     * and hides the JME debug status (which is visible by default).
     */
    @Override
    public void simpleInitApp() {
        // keep this method at the top or else we'll get exceptions.
        world = new Locator(this);
        this.simulation = new SimulationImpl(world);
        setDisplayStatView(false);
        setDisplayFps(false);
        setupGUI();
        setupKeyMappings();
    }

    /**
     * Updates the application.
     * This should do different things depending on whether we are
     * in the starting screen, in the pause screen or in the simulation
     * screen.
     * More importantly, when we are in the pause screen, the simulation
     * SHOULDN'T update.
     */
    @Override
    public void simpleUpdate(float tpf) {
        switch (state) {
            case START_SCREEN:
                break;
            case SIMULATION_SCREEN:
                hudControl.updateText();
                simulation.update();
                break;
            case PAUSE_SCREEN:
                break;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    /**
     * This function setups key mappings.
     * In particular, it sets up key mapping for entering and exiting the pause screen:
     * - P for entering
     * - E for exiting
     * When entering/exiting, we also set the @state field.
     */
    private void setupKeyMappings() {
        inputManager.addMapping("Pause Game", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                screenControl.enterPauseScreen();
                hudControl.detachAll();
                guiNode.detachChild(ch);
                inputManager.setCursorVisible(true);
                state = ScreenState.PAUSE_SCREEN;
            }
        }, "Pause Game");

        inputManager.addMapping("Esc Pause Game", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                hudControl.exitPauseScreen();
                guiNode.attachChild(ch);
                inputManager.setCursorVisible(false);
                state = ScreenState.SIMULATION_SCREEN;
            }
        }, "Esc Pause Game");
    }

    /**
     * Sets up the GUI.
     * We set up the GUI by creating it and setting up callbacks for the start button
     * and the quit button.
     */
    private void setupGUI() {
        flyCam.setEnabled(false);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        screenControl = new StartScreenController(this, world);
        screenControl.loadSimulation(simulation);
        screenControl.onStartButtonClicked(o -> startSimulation(o));
        screenControl.onQuitButtonClicked(from -> finish(from));
    }

    /**
     * Sets up the crosshairs.
     */
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

    /**
     * Starts the simulation.
     * This involves not just calling simulation's startSimulation method,
     * but also setting up the camera and changing the state field.
     */
    public void startSimulation(Simulation.Options options) {
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        initCrossHairs();
        simulation.start(options);
        screenControl.loadSimulation(simulation);
        hudControl = new HudText(simulation,assetManager,settings,world,screenControl.getNifty(),options);
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        state = ScreenState.SIMULATION_SCREEN;
    }

    /**
     * This function closes application.
     * It is called both when the user closes the window and when the user
     * presses the quit button on the GUI.
     *
     * @fromQuitButton indicates if it came from the GUI.
     */
    public void finish(Boolean fromQuitButton) {
        System.err.println("exiting...");
        System.exit(0);
    }

    /**
     * This function is called by JME when the user closes the window.
     */
    @Override
    public void destroy() {
        finish(false);
    }

    /**
     * Enum for screen states. These are used in update()
     * to do different things based on which screen we are.
     * For example, while we are inside the pause screen,
     * we won't update the simulation
     */
    enum ScreenState {
        START_SCREEN,
        SIMULATION_SCREEN,
        PAUSE_SCREEN,
    }
}
