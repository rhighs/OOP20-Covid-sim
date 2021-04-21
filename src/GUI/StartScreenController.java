package GUI;

import Simulation.Mask;
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
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.ScreenController;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private Mask.MaskProtection prot;
    private Simulation sim;
    private Node guiNode;
    private Instant start;
    private BitmapText personText;
    private BitmapText infText;
    private BitmapText timeText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText;
    Picture pic;

    public StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, Node guiNode, Callback<Simulation.Options> call) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.start = Instant.now();
        this.guiNode = guiNode;
        this.startSimFn = call;
        //default
        prot = Mask.MaskProtection.FP1;
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
        dropDown.addItem(Mask.MaskProtection.FP1);
        dropDown.addItem(Mask.MaskProtection.FP2);
        dropDown.addItem(Mask.MaskProtection.FP3);
    }

    @Override
    public void onEndScreen() {
    }


    public void GoTo(String screen) {
        nifty.gotoScreen(screen);
    }

    public void load(){
        nifty.gotoScreen("load");
    }

    public void loadWorst(){
        prot = Mask.MaskProtection.FP1;
        var options = new Simulation.Options(DEFAULT_PERSON, DEFAULT_PERSON, prot);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        if (startSimFn == null) {
            throw new IllegalStateException("Callback for starting simulation not set.");
        }
        startSimFn.call(options);
        nifty.gotoScreen("hud");
    }

    public void loadBest(){
        prot = Mask.MaskProtection.FP3;
        var options = new Simulation.Options(DEFAULT_PERSON, 0, prot);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        if (startSimFn == null) {
            throw new IllegalStateException("Callback for starting simulation not set.");
        }
        startSimFn.call(options);
        nifty.gotoScreen("hud");
    }

    public void startGame(String screen) {
        final TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        final TextField textNoM = nifty.getScreen("start").findNiftyControl("txtNoMask", TextField.class);
        final DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);
        final var text = textField.getRealText();
        prot = (Mask.MaskProtection) dropDown.getSelection();
        if(Optional.ofNullable(prot).isEmpty()){
            prot  = Mask.MaskProtection.FP1;
        }
        
        var options = new Simulation.Options(
                Integer.parseInt(text),
                Integer.parseInt(textNoM.getRealText()),
                prot
        );
        nifty.getScreen("start").findNiftyControl("StartButton", Button.class).enable();
        if (startSimFn == null) {
            throw new IllegalStateException("Callback for starting simulation not set.");
        }
        startSimFn.call(options);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        nifty.gotoScreen(screen);
    }
    
    //pause screen
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
        nifty.gotoScreen("edit");
        setTextEdit();
    }

    private void setTextEdit(){
        var txtAdd = nifty.getScreen("edit").findNiftyControl("txtAdd", TextField.class);
        txtAdd.setText("0");
        var txtAddInf = nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class);
        txtAddInf.setText("0");
    }
    
    //edit screen
    public void apply(){
        try{
            var txtAdd = nifty.getScreen("edit").findNiftyControl("txtAdd", TextField.class);
            sim.setCrowd(Integer.parseInt(txtAdd.getRealText()));
            txtAdd.setText("");
            var txtAddInf = nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class);
            sim.setInfected(Integer.parseInt(txtAdd.getRealText()));
            txtAddInf.setText("");
        }catch(Exception ex){}
        nifty.gotoScreen("pause");
        
    }

    public void stateMask(){
        sim.changeMaskState();
        nifty.getScreen("edit").findNiftyControl("txtReport", TextField.class).disable();
        nifty.getScreen("edit").findNiftyControl("txtReport", TextField.class).setText("Done!");
    }

    public void noInfected(){
        sim.resumeInfected();
        nifty.getScreen("edit").findNiftyControl("txtReport", TextField.class).disable();
        nifty.getScreen("edit").findNiftyControl("txtReport", TextField.class).setText("Done!");
    }

    public void cancel() {
        nifty.getScreen("edit").findNiftyControl("txtAdd", TextField.class).setText("");
        nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class).setText("");
        nifty.gotoScreen("pause");
    }
    
    public void initHudText(BitmapFont guiFont){
        personText = new BitmapText(guiFont, true);
        timeText = new BitmapText(guiFont, false);
        infText = new BitmapText(guiFont,false);
        maskTypeText = new BitmapText(guiFont,false);
        hudText = new ArrayList<>();
        hudText.addAll(Arrays.asList(personText, timeText, infText, maskTypeText));
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
}