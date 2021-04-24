package GUI.Controllers;

import GUI.Models.Controls;
import GUI.Models.Screens;
import Simulation.Simulation;
import de.lessvoid.nifty.*;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;

public class EditControl {
    private Nifty nifty;
    private Simulation sim;

    public EditControl(Nifty nifty){

        this.nifty = nifty;

    }
    public void cleanEditComps() {
        var screen = getScreen(Screens.EDIT.getName());
        screen.findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class).setText("0");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
        screen.findNiftyControl(Controls.REP_LABEL.getName(), Label.class).setText("");
    }

    public void setEditComponent() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("0");
    }

    public void cancel() {
        getScreen(Screens.EDIT.getName())
                .findNiftyControl(Controls.ADD_INFECTED.getName(), TextField.class)
                .setText("");
        nifty.gotoScreen(Screens.PAUSE.getName());
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

    public void setSim(Simulation sim) {
        this.sim = sim;
    }

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

        this.cleanEditComps();
        nifty.gotoScreen(Screens.PAUSE.getName());
    }

    private Screen getScreen(String screeName) {
        return this.nifty.getScreen(screeName);
    }
}
