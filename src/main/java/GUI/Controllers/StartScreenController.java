package GUI.Controllers;

import Environment.Locator;
import GUI.Models.Controls;
import GUI.Models.Screens;
import Simulation.Person;
import Simulation.Simulation;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.util.List;

/**
 * @author json
 * Controller for GUI attached with screen.xml
 */
public class StartScreenController extends BaseAppState implements ScreenController {


    private final String SCREEN_PATH = "Interface/Screen.xml";
    public static final String HUD_IMAGE_PATH = "Interface/black.png";
    private final String START_SCREEN_NAME = Screens.START.getName();
    private final Nifty nifty;
    private final FlyByCamera flyCam;
    private final InputManager inputManager;
    private Callback<Simulation.Options> startSimFn;
    private Callback<Boolean> quitFn;
    private Person.Mask.Protection prot;
    private Simulation sim;
    private final Node guiNode;
    private BitmapText personText;
    private BitmapText infText;
    private BitmapText timeText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText;
    private final Locator world;
    private Picture pic;
    private SituationComponent situationControl;
    private EditComponent editComponent;


    /**
     * Creates a new instance of the class.
     *
     * @param app The application.
     * @param world  A locator which have the guiNode.
     */
    public StartScreenController(SimpleApplication app, Locator world) {
        this.flyCam = app.getFlyByCamera();
        this.inputManager = app.getInputManager();
        this.world = world;

        var niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                app.getAssetManager(),
                app.getInputManager(),
                app.getAudioRenderer(),
                app.getGuiViewPort()
        );

        this.nifty = niftyDisplay.getNifty();
        app.getGuiViewPort().addProcessor(niftyDisplay);
        guiNode = world.getGuiNode();
        nifty.fromXml(SCREEN_PATH, START_SCREEN_NAME, this);
        prot = Person.Mask.Protection.FP1;

    }

    @Override
    public void onStartScreen() {
        //add items to the dropDown
        var dropDown = nifty.getScreen(START_SCREEN_NAME).findNiftyControl("dropMask", DropDown.class);
        dropDown.addItem(Person.Mask.Protection.FP1);
        dropDown.addItem(Person.Mask.Protection.FP2);
        dropDown.addItem(Person.Mask.Protection.FP3);
    }

    /**
     * @return The nifty object
     */
    public Nifty getNifty(){
        return nifty;
    }


    /**
     * Load worst preset simulation
     */
    //load screen
    public void loadWorst() {

        startSimulation(SituationComponent.getWorst());

    }

    /**
     * Load best preset simulation
     */
    public void loadBest() {

        startSimulation(SituationComponent.getBest());

    }

    /**
     * Load set simulation for controller usage
     */
    public void loadSimulation(Simulation simulation) {
        this.sim = simulation;
    }

    /**
     * Start screen
     * Called from Start button, read input from user and load screen components
     */
    public void startGame(String screen) {
        var niftyScreen = nifty.getScreen(START_SCREEN_NAME);

        var textField = niftyScreen.findNiftyControl(Controls.TEXT_PERSON.getName(), TextField.class);
        var textNoM = niftyScreen.findNiftyControl(Controls.TXT_NOMASK.getName(), TextField.class);
        var dropDown = niftyScreen.findNiftyControl(Controls.DROP_MASK.getName(), DropDown.class);

        prot = (Person.Mask.Protection) dropDown.getSelection();
        if (prot == null) {
            prot = Person.Mask.Protection.FP1;
        }

        var options = new Simulation.Options(
                Integer.parseInt(textField.getRealText()),
                Integer.parseInt(textNoM.getRealText()),
                prot
        );

        startSimulation(options);
        loadEditComponent();
    }

    private void loadEditComponent(){
        editComponent = new EditComponent(nifty,sim);
    }

    /**
     * Callback for startSimulation
     */
    public void startSimulation(Simulation.Options options) {
        if (startSimFn == null) {
            throw new IllegalStateException("Callback for starting simulation not set.");
        }

        nifty.gotoScreen(Screens.HUD.getName());
        startSimFn.call(options);
    }


    public void quit() {
        if (quitFn == null) {
            throw new IllegalStateException("quitFn not set.");
        }

        quitFn.call(false);
    }


    /**
     * Go to pause screen
     */
    public void enterPauseScreen() {
        nifty.gotoScreen(Screens.PAUSE.getName());
    }


    //edit screen
    /**
     * Go to edit screen
     */
    public void edit() {
        nifty.gotoScreen(Screens.EDIT.getName());
    }

    /**
     * call cancel method in editComponent
     */
    public void cancel() {
        editComponent.cancel();
    }

    /**
     * call stateMask method in editComponent
     */
    public void stateMask() {
        editComponent.stateMask();
    }

    /**
     * call noInfected method in editComponent
     */
    public void noInfected() {
        editComponent.noInfected();
    }

    /**
     * call apply method in editComponent
     */
    public void apply() {
        editComponent.apply();
    }


    // This method is called by the load button.
    /**
     * Go to load screen
     */
    public void load() {
        nifty.gotoScreen(Screens.LOAD.getName());
    }

    /**
     * Go to commands screen
     */
    public void commands() {
        nifty.gotoScreen(Screens.COMMANDS.getName());
    }

    /**
     * Set start callback
     */
    public void onStartButtonClicked(Callback<Simulation.Options> callback) {
        startSimFn = callback;
    }

    /**
     * Set quit callback
     */
    public void onQuitButtonClicked(Callback<Boolean> callback) {
        quitFn = callback;
    }

    private Screen getScreen(String screeName) {
        return this.nifty.getScreen(screeName);
    }
    /**
     * Go to Start screen from load screen
     */
    public void backSit(){
        nifty.gotoScreen(Screens.START.getName());
    }

    /**
     * Go to pause screen from commands screen
     */
    public void backCom(){
        nifty.gotoScreen(Screens.PAUSE.getName());
    }
    @Override
    public void onEndScreen() {
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void bind(Nifty arg0, Screen arg1) {
    }

    @FunctionalInterface
    public interface Callback<T> {
        void call(T elem);
    }
}