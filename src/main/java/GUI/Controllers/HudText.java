package GUI.Controllers;

import Environment.Locator;
import GUI.Models.Screens;
import Simulation.*;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author json
 * Class to manage the HUD
 */
public class HudText extends BaseAppState implements ScreenController {

    private BitmapText personText;
    private BitmapText timeText;
    private BitmapText infText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText = new ArrayList<>();
    private Picture pic;
    private Simulation sim;
    private final Instant start;
    private Person.Mask.Protection prot;
    private AssetManager assetManager;
    private AppSettings settings;
    private Node guiNode;
    private BitmapFont guiFont;
    private final Simulation.Options options;
    private Nifty nifty;

    /**
     * Creates a new instance of the class.
     *
     * @param sim The application.
     * @param assetManager  The assset manager to attach BitmapText and Picture.
     * @param settings Screen settings.
     * @param world The locator.
     * @param nifty The nifty object.
     * @param options The start options.
     */
    public HudText(Simulation sim, AssetManager assetManager, AppSettings settings, Locator world, Nifty nifty, Simulation.Options options){
        this.sim = sim;
        this.assetManager = assetManager;
        this.settings = settings;
        this.guiNode = world.getGuiNode();
        this.options = options;
        this.nifty = nifty;
        this.start = Instant.now();
        this.guiFont = assetManager.loadFont("Interface/Fonts/PhetsarathOT.fnt");
        this.setHudText();
    }

    /**
     * Detach all elements of the HUD
     */
    public void detachAll(){
        hudText.forEach(i -> guiNode.detachChild(i));
        guiNode.detachChild(pic);
    }

    /**
     * attach all elements of the HUD
     * when return to HUD screen
     */
    public void exitPauseScreen() {
        nifty.gotoScreen(Screens.HUD.getName());
        hudText.forEach(i -> guiNode.attachChild(i));
        guiNode.attachChild(pic);
    }


    private void setHudText() {
        this.initHudText();
        this.setHudImage();
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

    private void initHudText() {

        personText = new BitmapText(guiFont, false);
        timeText = new BitmapText(guiFont, false);
        infText = new BitmapText(guiFont, false);
        maskTypeText = new BitmapText(guiFont, false);
        hudText = new ArrayList<>();
        hudText.addAll(Arrays.asList(personText, timeText, infText, maskTypeText));
    }

    /**
     * Update HUD text with updated values
     */
    public void updateText() {
        Long time = this.getTime();
        personText.setText("Person: " + sim.getPersonCount());
        infText.setText("Infected: " + sim.getInfectedNumb());
        maskTypeText.setText("Mask Type: " + options.protection);
        timeText.setText(time != null ? "Time: " + time : "Time:");
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

    private void setHudImage() {
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, StartScreenController.HUD_IMAGE_PATH, true);
        pic.setWidth(settings.getWidth() / 4);
        pic.setHeight(settings.getHeight() / 4);
        pic.setPosition(0, 0);
        pic.move(0f, 0f, -1);
        guiNode.attachChild(pic);
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

