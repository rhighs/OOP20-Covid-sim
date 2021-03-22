package GUI;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * @author juri, chri
 */
public class StartScreenController extends BaseAppState implements ScreenController{

    @FunctionalInterface
    public static interface Callback {
        void call(int numPerson);
    }

    private final Nifty nifty;
    private final FlyByCamera flyCam;
    private final InputManager inputManager;
    private final Callback callback;
    private TextField numPersonField = null;

    public StartScreenController(Nifty nifty, FlyByCamera flyCam, InputManager inputManager, Callback call) {
        this.nifty = nifty;
        this.flyCam = flyCam;
        this.inputManager = inputManager;
        this.callback = call;
    }

    @Override
    public void onStartScreen() {
        this.numPersonField = nifty.getScreen("start").findNiftyControl("textPerson", TextField.class);
        this.numPersonField.setText("5");
    }

    public void startGame(String screen) {
        flyCam.setEnabled(true);
        flyCam.setDragToRotate(false);
        inputManager.setCursorVisible(false);
        int numPerson = Integer.parseInt(numPersonField.getRealText());
        callback.call(numPerson);
        nifty.gotoScreen(screen);
    }

    /*
    public void quitGame() {
        getApplication().stop();
    }
    */

    // Mostly useless methods ahead, ignore...

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
    public void onEndScreen() {
    }
}
