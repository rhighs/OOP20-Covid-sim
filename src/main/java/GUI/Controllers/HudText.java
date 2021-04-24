package GUI.Controllers;

import Simulation.*;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author json
 */
public class HudText {

    private BitmapText personText;
    private BitmapText timeText;
    private BitmapText infText;
    private BitmapText maskTypeText;
    private List<BitmapText> hudText = new ArrayList<>();
    Picture pic;


    /*public List<BitmapText> attach() {
        int count = text.size();
        for (var t : text) {
            t.setColor(ColorRGBA.White);
            t.setLocalTranslation(0, backY / count++, 0);
            t.setText("Test");
        }

        return text;
    }*/

    public void detachAll(Node guiNode){
        hudText.forEach(i -> guiNode.detachChild(i));
        guiNode.detachChild(pic);
    }

    public void setHudText(AppSettings settings, BitmapFont guiFont, Node guiNode) {
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

    public void initHudText(BitmapFont guiFont) {
        //var textRight = new BitmapText(guiFont, true);
        //var textLeft = new BitmapText(guiFont, false);

        personText = new BitmapText(guiFont, false);
        timeText = new BitmapText(guiFont, false);
        infText = new BitmapText(guiFont, false);
        maskTypeText = new BitmapText(guiFont, false);
        hudText = new ArrayList<>();
        hudText.addAll(Arrays.asList(personText, timeText, infText, maskTypeText));
    }

    public void updateText(int nPerson, int nInfected, Long time, Person.Mask.Protection prot) {
        personText.setText("Person: " + nPerson);
        infText.setText("Infected: " + nInfected);
        maskTypeText.setText("Mask Type: " + prot);
        timeText.setText(time != null ? "Time: " + time : "Time:");
    }

    public void setHudImage(AssetManager assetManager, AppSettings settings, Node guiNode) {
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, StartScreenController.HUD_IMAGE_PATH, true);
        pic.setWidth(settings.getWidth() / 4);
        pic.setHeight(settings.getHeight() / 4);
        pic.setPosition(0, 0);
        pic.move(0f, 0f, -1);
        guiNode.attachChild(pic);
    }
}

