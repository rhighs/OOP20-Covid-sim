package GUI.Controllers;

import Environment.Locator;
import GUI.Models.Controls;
import GUI.Models.Screens;
import Simulation.Person;
import Simulation.Simulation;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * @author json 
 */
public class StartScreenController extends BaseAppState implements ScreenController {

    public static final int DEFAULT_PERSON = 50;
    private final String SCREEN_PATH = "Interface/Screen.xml";
    public static final String HUD_IMAGE_PATH = "Interface/black.png";
    private final String START_SCREEN_NAME = Screens.START.getName();
    private final Locator world;
    private final Instant start;
    private final Node guiNode;
    private final Nifty nifty;
    private final FlyByCamera flyCam;
    private final InputManager inputManager;
    private Callback<Simulation.Options> startSimFn;
    private Callback<Boolean> quitFn;
    private Person.Mask.Protection prot;
    private Simulation sim;
    private BitmapText personText;
    private BitmapText infText;
    private BitmapText timeText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText;
    private Picture pic;
    private EditControl editControl;
    private SituationControl situationControl;
    private HudText hudTextControl;

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
        this.start = Instant.now();
        guiNode = world.getGuiNode();
        nifty.fromXml(SCREEN_PATH, START_SCREEN_NAME, this);
        prot = Person.Mask.Protection.FP1;

        situationControl = new SituationControl();
        hudTextControl = new HudText();
        editControl = new EditControl(nifty);

    }

    @Override
    public void onStartScreen() {
        //add items to the dropDown
        var dropDown = nifty.getScreen(START_SCREEN_NAME).findNiftyControl("dropMask", DropDown.class);
        dropDown.addItem(Person.Mask.Protection.FP1);
        dropDown.addItem(Person.Mask.Protection.FP2);
        dropDown.addItem(Person.Mask.Protection.FP3);
    }

    //load screen
    public void loadWorst() {

        startSimulation(situationControl.getWorst());

    }

    public void loadBest() {

        startSimulation(situationControl.getBest());

    }

    public void loadSimulation(Simulation simulation) {
        this.sim = simulation;
        //TODO
        editControl.setSim(sim);
    }

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
    }

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

    //HUD screens
    public void initHudText(BitmapFont guiFont) {
        hudTextControl.initHudText(guiFont);
    }

    public void setHudText(AppSettings settings, BitmapFont guiFont) {
        hudTextControl.setHudText(settings,guiFont,guiNode);
    }

    public void setHudImage(AssetManager assetManager, AppSettings settings) {
        hudTextControl.setHudImage(assetManager,settings,guiNode);
    }

    public void updateText() {

        Long time = this.getTime();

        hudTextControl.updateText(sim.getPersonCount(), sim.getInfectedNumb(), time, prot);
    }

    private Long getTime() {
        long timeElapsed;

        try {
            Instant finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toSeconds();
        } catch (NullPointerException ex) {
            return 0L;
        }

        return timeElapsed;
    }

    public void enterPauseScreen() {
        nifty.gotoScreen(Screens.PAUSE.getName());

    }

    public void exitPauseScreen() {
        nifty.gotoScreen(Screens.HUD.getName());
        hudText.forEach(i -> guiNode.attachChild(i));
        guiNode.attachChild(pic);
    }

    //edit screen
    public void edit() {
        editControl.setEditComponent();
        nifty.gotoScreen(Screens.EDIT.getName());
        editControl.cleanEditComps();
    }

    public void apply() {
        editControl.apply();
    }

    public void stateMask() {
        editControl.stateMask();
    }

    public void noInfected() {
        editControl.noInfected();
    }

    public void cancel() {
        editControl.cancel();
    }

    // This method is called by the load button.
    public void load() {
        nifty.gotoScreen(Screens.LOAD.getName());
    }

    public void commands() {
        nifty.gotoScreen(Screens.COMMANDS.getName());
    }

    public void onStartButtonClicked(Callback<Simulation.Options> callback) {
        startSimFn = callback;
    }

    public void onQuitButtonClicked(Callback<Boolean> callback) {
        quitFn = callback;
    }

    //TODO
    private Screen getScreen(String screeName) {
        return this.nifty.getScreen(screeName);
    }

    public void backSit(){
        nifty.gotoScreen(Screens.START.getName());
    }

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
