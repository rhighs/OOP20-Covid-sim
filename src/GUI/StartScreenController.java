package GUI;

import Simulation.Mask;
import de.lessvoid.nifty.Nifty;
import com.jme3.app.Application;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import de.lessvoid.nifty.screen.Screen;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.ScreenController;

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

    private Nifty nifty;
    private FlyByCamera flyCam;
    private InputManager inputManager;
    private Callback call;

    public StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, Callback call) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.call = call;
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
        
        nifty.getScreen("start").findNiftyControl("StartButton", Button.class).disable();
    }

    @Override
    public void onEndScreen() {
    }
    
    public void load(){
        final TextField textField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        final TextField textNoM = nifty.getScreen("start").findNiftyControl("txtNoMask", TextField.class);
        final DropDown dropDown = nifty.getScreen("start").findNiftyControl("dropMask", DropDown.class);
        final var text = textField.getRealText();

        try{
            Options options = new Options(
                Integer.parseInt(text),
                Integer.parseInt(textNoM.getRealText()),
                (Mask.MaskProtection) dropDown.getSelection()
            );
            nifty.getScreen("start").findNiftyControl("StartButton", Button.class).enable();
            call.call(options);

        }catch(Exception ex){}
        
    }
    public void startGame(String screen) {
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        // get number of person
        
        nifty.gotoScreen(screen);
    }
    
    //PauseScreen
    public void setLabelInf(int inf)
    {
        var txtInf = nifty.getScreen("pause").findNiftyControl("txtInf", TextField.class);
        txtInf.setText(Integer.toString(inf));
        txtInf.setEnabled(false);
    }
    public void quitGame() {
        getApplication().stop();
    }
}

