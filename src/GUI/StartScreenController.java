package GUI;

import Simulation.Mask;
import Simulation.Simulation;
import de.lessvoid.nifty.Nifty;
import com.jme3.app.Application;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import de.lessvoid.nifty.screen.Screen;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.StopWatch;
import java.time.Duration;
import java.time.Instant;

/**
 * @author json 
 */
public class StartScreenController extends BaseAppState implements ScreenController{
    
    public final static class Options {
        public final int nPerson;
        public final int nMasks;
        public final Mask.MaskProtection protection;

        public Options(int p, int m, Mask.MaskProtection pr) {
            nPerson = p;
            nMasks = m;
            protection = pr;
        }
    }
    
    @FunctionalInterface
    public static interface Callback {
        void call(Options options);
    }
    
    private static int DEFAULT_PERSON = 50;
    private Nifty nifty;
    private FlyByCamera flyCam;
    private InputManager inputManager;
    private Callback call;
    private Mask.MaskProtection prot;
    private Simulation sim;
    private Instant start;

    public StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, Callback call) {
        
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.call = call;
        this.start = Instant.now();
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
        TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        textField.setText("1");
        TextField textF = nifty.getScreen("start").findNiftyControl("txtNoMask", TextField.class);
        textF.setText("0");
        //add items to the dropDown
        DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);
        dropDown.addItem(Mask.MaskProtection.FP1);
        dropDown.addItem(Mask.MaskProtection.FP2);
        dropDown.addItem(Mask.MaskProtection.FP3);
        
        //nifty.getScreen("start").findNiftyControl("StartButton", Button.class).disable();
        
  
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
        Options options = new Options(DEFAULT_PERSON, DEFAULT_PERSON, prot);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        call.call(options);
        nifty.gotoScreen("hud");
    }
    
    public void loadBest(){
        prot = Mask.MaskProtection.FP3;
        Options options = new Options(DEFAULT_PERSON, 0, prot);
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        call.call(options);
        nifty.gotoScreen("hud");
    }
     
    public void startGame(String screen) {
        final TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        final TextField textNoM = nifty.getScreen("start").findNiftyControl("txtNoMask", TextField.class);
        final DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);
        final var text = textField.getRealText();
        prot = (Mask.MaskProtection) dropDown.getSelection();
        try{
            Options options = new Options(
                Integer.parseInt(text),
                Integer.parseInt(textNoM.getRealText()),
                prot
            );
            nifty.getScreen("start").findNiftyControl("StartButton", Button.class).enable();
            call.call(options);
            
            flyCam.setEnabled(true);
            flyCam.setDragToRotate(false);
            inputManager.setCursorVisible(false);
            nifty.gotoScreen(screen);

        }catch(Exception ex){}
    }
    
    //PauseScreen
    public void setLabelInf(int inf)
    {
        var txtInf = nifty.getScreen("pause").findNiftyControl("txtInf", TextField.class);
        txtInf.setText(Integer.toString(inf));
        txtInf.setEnabled(false);
       
    }

    public void setTime(){
    
         var txtTime = nifty.getScreen("pause").findNiftyControl("TimeSpentLabel", TextField.class);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toSeconds();
        System.out.println(Long.toString(timeElapsed));
        txtTime.setText(Long.toString(timeElapsed));
        txtTime.setEnabled(false);
    }

    public void setLabelInfMask()
    {
        var txtInfMask = nifty.getScreen("pause").findNiftyControl("txtMaskInf", TextField.class);
        txtInfMask.setText(prot.toString());
        txtInfMask.setEnabled(false);
    }

    public void quitGame() {
        System.exit(0);
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

    //modify screen
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
    }

    public void noInfected(){
        sim.resumeInfected();
    }

    public void cancel(){
        nifty.getScreen("edit").findNiftyControl("txtAdd", TextField.class).setText("");
        nifty.getScreen("edit").findNiftyControl("txtAddInf", TextField.class).setText("");
        nifty.gotoScreen("pause");}
}

