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
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author json 
 */
public class StartScreenController extends BaseAppState implements ScreenController {

    private static final int DEFAULT_PERSON = 50;
    private final String SCREEN_PATH = "Interface/Screen.xml";
    private final String HUD_IMAGE_PATH = "Interface/black.png";
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
    }

    @Override
    public void onStartScreen() {
        //add items to the dropDown
        var dropDown = nifty.getScreen(START_SCREEN_NAME).findNiftyControl("dropMask", DropDown.class);
        dropDown.addItem(Person.Mask.Protection.FP1);
        dropDown.addItem(Person.Mask.Protection.FP2);
        dropDown.addItem(Person.Mask.Protection.FP3);
    }

    public void loadWorst() {
        prot = Person.Mask.Protection.FP1;
        startSimulation(new Simulation.Options(
                DEFAULT_PERSON,
                DEFAULT_PERSON,
                prot
        ));
    }

    public void loadBest() {
        prot = Person.Mask.Protection.FP3;
        startSimulation(new Simulation.Options(
                DEFAULT_PERSON,
                0,
                prot
        ));
    }

    public void loadSimulation(Simulation simulation) {
        this.sim = simulation;
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

    //edit screen
    public void apply() {
        var realText = getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .getRealText();

        try {
            sim.setInfected(Integer.parseInt(realText));
        } catch (Exception ex) {
            //TODO
            //something went wrong nullptr exc
        }

        cleanEditComps();
        nifty.gotoScreen(Screens.PAUSE.getName());
    }

    private void cleanEditComps() {
        var screen = getScreen(Screens.EDIT.getName());
        screen.findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class).setText("0");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
    }

    public void initHudText(BitmapFont guiFont) {
        var textRight = new BitmapText(guiFont, true);
        var textLeft = new BitmapText(guiFont, false);

        personText = textRight;
        timeText = textLeft;
        infText = textLeft;
        maskTypeText = textLeft;
        hudText = new ArrayList<>();
        hudText.addAll(Arrays.asList(personText, timeText, infText, maskTypeText));
    }

    public void setHudText(AppSettings settings, BitmapFont guiFont) {
        this.initHudText(guiFont);
        personText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        personText.setColor(ColorRGBA.White);                             // font color
        personText.setText("People: ");
        personText.setLocalTranslation(0, settings.getHeight() / 4, 0); // position
        guiNode.attachChild(personText);

        infText.setSize(guiFont.getCharSet().getRenderedSize());
        infText.setColor(ColorRGBA.White);                             // font color
        infText.setText("Infected: ");
        infText.setLocalTranslation(0, settings.getHeight() / 5, 0); // position
        guiNode.attachChild(infText);

        timeText.setSize(guiFont.getCharSet().getRenderedSize());
        timeText.setColor(ColorRGBA.White);                             // font color
        timeText.setText("Time: ");
        timeText.setLocalTranslation(0, settings.getHeight() / 12, 0); // position
        guiNode.attachChild(timeText);

        maskTypeText.setSize(guiFont.getCharSet().getRenderedSize());
        maskTypeText.setColor(ColorRGBA.White);                             // font color
        maskTypeText.setText("Mask Type: ");
        maskTypeText.setLocalTranslation(0, settings.getHeight() / 7, 0); // position
        guiNode.attachChild(maskTypeText);
    }

    public void setHudImage(AssetManager assetManager, AppSettings settings) {
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, HUD_IMAGE_PATH, true);
        pic.setWidth(settings.getWidth() / 4);
        pic.setHeight(settings.getHeight() / 4);
        pic.setPosition(0, 0);
        pic.move(0f, 0f, -1);
        guiNode.attachChild(pic);
    }

    public void updateText() {

        if(sim == null){
            System.out.println("dino");
        }

        try{
            personText.setText("Person: " + sim.getPersonCount());
            infText.setText("Infected: " + sim.getInfectedNumb());
            maskTypeText.setText("Mask Type: " + prot);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        Long time = this.getTime();
        timeText.setText(time != null ? "Text: " + time : "0");
    }

    public void enterPauseScreen() {
        nifty.gotoScreen(Screens.PAUSE.getName());
        hudText.forEach(i -> guiNode.detachChild(i));
        guiNode.detachChild(pic);
    }

    public void exitPauseScreen() {
        nifty.gotoScreen(Screens.HUD.getName());
        hudText.forEach(i -> guiNode.attachChild(i));
        guiNode.attachChild(pic);
    }

    public void edit() {
        setEditComponent();
        nifty.gotoScreen(Screens.EDIT.getName());
        cleanEditComps();
    }

    private void setEditComponent() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("0");
    }

    public void stateMask() {
        sim.changeMaskState();
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.REP_LABEL.getName(), Label.class)
                .setText("Switching mask state!");
    }

    public void noInfected() {
        sim.resumeInfected();
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.REP_LABEL.getName(), Label.class)
                .setText("Infected resumed!");
    }

    public void cancel() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("");
        nifty.gotoScreen(Screens.PAUSE.getName());
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

    private Screen getScreen(String screeName) {
        return this.nifty.getScreen(screeName);
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
