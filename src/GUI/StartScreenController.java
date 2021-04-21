package GUI;

import Simulation.Person.Mask;
import Simulation.Simulation;
import de.lessvoid.nifty.Nifty;
import com.jme3.app.Application;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import de.lessvoid.nifty.screen.Screen;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.ScreenController;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import Environment.Locator;

/**
 * @author json 
 */
public class StartScreenController extends BaseAppState implements ScreenController{

    @FunctionalInterface
    public static interface Callback<T> {
        void call(T elem);
    }

    private static int DEFAULT_PERSON = 50;
    private Nifty nifty;
    private FlyByCamera flyCam;
    private InputManager inputManager;
    private Callback<Simulation.Options> startSimFn;
    private Callback<Boolean> quitFn;
    private Mask.Protection prot;
    private Simulation sim;
    private Node guiNode;
    private Instant start;
    private BitmapText personText;
    private BitmapText infText;
    private BitmapText timeText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText;
    private Locator world;
    private Picture pic;

    public StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, Locator world) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.start = Instant.now();
        this.world = world;
        guiNode = world.getGuiNode();
        //default
        prot = Mask.Protection.FP1;
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

    @Override
    public void onStartScreen() {
        //add items to the dropDown
        DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);
        dropDown.addItem(Mask.Protection.FP1);
        dropDown.addItem(Mask.Protection.FP2);
        dropDown.addItem(Mask.Protection.FP3);
    }

    @Override
    public void onEndScreen() {
    }

    public void GoTo(String screen) {
        nifty.gotoScreen(screen);
    }

    // This method is called by the load button.
    public void load(){
        nifty.gotoScreen("load");
    }

    public void loadWorst(){
        prot = Mask.Protection.FP1;
        startSimulation(new Simulation.Options(
            DEFAULT_PERSON,
            DEFAULT_PERSON,
            prot
        ));
    }

    public void loadBest(){
        prot = Mask.Protection.FP3;
        startSimulation(new Simulation.Options(
            DEFAULT_PERSON,
            0,
            prot
        ));
    }

    public void startGame(String screen) {
        final TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        final TextField textNoM = nifty.getScreen("start").findNiftyControl("txtNoMask", TextField.class);
        final DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);

        prot = (Mask.Protection) dropDown.getSelection();
        if (prot == null) {
            prot = Mask.Protection.FP1;
        }
        // nifty.getScreen("start").findNiftyControl("StartButton", Button.class).enable();
        startSimulation(new Simulation.Options(
            Integer.parseInt(textField.getRealText()),
            Integer.parseInt(textNoM.getRealText()),
            prot
        ));
    }

    public void startSimulation(Simulation.Options options) {
        if (startSimFn == null) {
            throw new IllegalStateException("Callback for starting simulation not set.");
        }
        nifty.gotoScreen("hud");
        startSimFn.call(options);
    }



    //PauseScreen

    private Long getTime(){

        long timeElapsed;

        try{
            Instant finish = Instant.now();
            timeElapsed = Duration.between(start, finish).toSeconds();

        }catch(NullPointerException ex){
            return 0L;
        }
        return timeElapsed;
    }

    public void quit() {
        System.exit(0);

    }

    public void commands(){
        nifty.gotoScreen("commands");
    }

    public void loadSimulation(Simulation simulation){
        this.sim = simulation;
    }

    public void edit(){
        setEditComponent();
        nifty.gotoScreen("edit");
        cleanEditComps();
    }

    private void setEditComponent(){
        var txtAddInf = nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class);
        txtAddInf.setText("0");
    }
    //edit screen
    public void apply(){
        try{
            var txtAddInf = nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class);
            sim.setInfected(Integer.parseInt(txtAddInf.getRealText()));
        }catch(Exception ex){}
        cleanEditComps();
        nifty.gotoScreen("pause");

    }
    private void cleanEditComps(){
        nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class).setText("0");
        nifty.getScreen("edit").findNiftyControl("RepLabel", Label.class).setText("");
        nifty.getScreen("edit").findNiftyControl("RepLabel", Label.class).setText("");
    }
    public void stateMask(){
        sim.changeMaskState();
        nifty.getScreen("edit").findNiftyControl("RepLabel", Label.class).setText("Switching mask state!");
    }

    public void noInfected(){
        sim.resumeInfected();
        nifty.getScreen("edit").findNiftyControl("RepLabel", Label.class).setText("Infected resumed!");
    }

    public void cancel() {
        nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class).setText("");
        nifty.gotoScreen("pause");
    }

    public void initHudText(BitmapFont guiFont){
        personText = new BitmapText(guiFont, true);
        timeText = new BitmapText(guiFont, false);
        infText = new BitmapText(guiFont,false);
        maskTypeText = new BitmapText(guiFont,false);
        hudText = new ArrayList<>();
        hudText.addAll(Arrays.asList(personText,timeText,infText,maskTypeText));
    }

    public void setHudText(AppSettings settings, BitmapFont guiFont){
        this.initHudText(guiFont);
        personText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        personText.setColor(ColorRGBA.White);                             // font color
        personText.setText("People: ");
        personText.setLocalTranslation(0, settings.getHeight()/4, 0); // position
        guiNode.attachChild(personText);

        infText.setSize(guiFont.getCharSet().getRenderedSize());
        infText.setColor(ColorRGBA.White);                             // font color
        infText.setText("Infected: ");
        infText.setLocalTranslation(0, settings.getHeight()/5, 0); // position
        guiNode.attachChild(infText);

        timeText.setSize(guiFont.getCharSet().getRenderedSize());
        timeText.setColor(ColorRGBA.White);                             // font color
        timeText.setText("Time: ");
        timeText.setLocalTranslation(0, settings.getHeight()/12, 0); // position
        guiNode.attachChild(timeText);

        maskTypeText.setSize(guiFont.getCharSet().getRenderedSize());
        maskTypeText.setColor(ColorRGBA.White);                             // font color
        maskTypeText.setText("Mask Type: ");
        maskTypeText.setLocalTranslation(0, settings.getHeight()/7, 0); // position
        guiNode.attachChild(maskTypeText);
    }

    public void setHudImage(AssetManager assetManager, AppSettings settings){
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Interface/black.jpg", true);
        pic.setWidth(settings.getWidth()/4);
        pic.setHeight(settings.getHeight()/4);
        pic.setPosition(0, 0);
        pic.move(0f, 0f, -1);
        guiNode.attachChild(pic);
    }

    public void updateText(){
            personText.setText("Person: " + sim.getPersonCount());
            infText.setText("Infected: " + sim.getInfectedNumb());
            maskTypeText.setText("Mask Type: " + prot);
            try{
                timeText.setText("Time: " + this.getTime());
            }catch(Exception ex){
                timeText.setText("0");
            }
    }

    public void hideHudComp(){
        hudText.forEach(i -> guiNode.detachChild(i));
        guiNode.detachChild(pic);
    }

    public void showHudComp(){
        hudText.forEach(i -> guiNode.attachChild(i));
        guiNode.attachChild(pic);
    }
    public void onStartButtonClicked(Callback<Simulation.Options> callback) {
        startSimFn = callback;
    }

    public void onQuitButtonClicked(Callback<Boolean> callback) {
        quitFn = callback;
    }
}

