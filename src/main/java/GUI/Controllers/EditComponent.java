package GUI.Controllers;

import GUI.Models.Controls;
import GUI.Models.Screens;
import Simulation.Simulation;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.*;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import javax.annotation.Nonnull;

/**
 * @author json
 * Logic for Edit screen
 */
public class EditComponent extends BaseAppState implements ScreenController {
    private Nifty nifty;
    private Simulation sim;

    /**
     * Creates a new instance of the class.
     *
     * @param nifty The nifty object.
     * @param simulation  The simulation object.
     */
    public EditComponent(Nifty nifty, Simulation sim){

        this.nifty = nifty;
        this.sim = sim;
        this.setEditComponent();
    }

    private void cleanEditComps() {
        var screen = getScreen(Screens.EDIT.getName());
        screen.findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class).setText("0");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
    }

    private void setEditComponent() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("0");
    }

    private Screen getScreen(String screeName) {
        return this.nifty.getScreen(screeName);
    }

    /**
     * Logic for cancel button
     */
    public void cancel() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("");
        nifty.gotoScreen(Screens.PAUSE.getName());
    }

    /**
     * Logic for Change state mask button
     * when mask state is changed set text of RepLabel
     */
    public void stateMask() {
        sim.changeMaskState();
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.REP_LABEL.getName(), Label.class)
                .setText("Switching mask state!");
    }

    /**
     * Logic for no infected button
     * resume the infected number to 1
     */
    public void noInfected() {
        sim.resumeInfected();
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.REP_LABEL.getName(), Label.class)
                .setText("Infected resumed!");
    }

    /**
     * Logic for apply button
     * get input from textField and try to add infected people
     */
    public void apply() {
        var realText = getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .getRealText();

        try {
            sim.setInfected(Integer.parseInt(realText));
        } catch (Exception ex) {

        }

        this.cleanEditComps();
        nifty.gotoScreen(Screens.PAUSE.getName());
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
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
