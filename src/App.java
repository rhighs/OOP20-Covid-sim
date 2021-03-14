import Components.Lightning;
import Components.PathCalculator;
import java.util.ArrayList;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.renderer.RenderManager;

import Components.PathGenerator;
import Simulation.Assets;
import Simulation.Person;
import Simulation.Picker;
import Simulation.Virus;
import com.jme3.font.BitmapText;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication {
    // constants
    int numPerson = 0;
    private Nifty nifty;
    private BulletAppState bState;
    private List<Person> crowd = null;
    Virus v;
    BitmapText textPerson;
    BitmapText textFlu;
    StartScreenController startScreenState;
    private Node scene;
    public App() {
        //super(new FlyCamAppState());
    }

    public void simpleInitApp() {
        
        textPerson = new BitmapText(guiFont, false);
        textFlu = new BitmapText(guiFont, false);

        //set cursor visible on init GUI
        flyCam.setEnabled(false);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        //stateManager.attach(startScreenState);
            NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        
        nifty = niftyDisplay.getNifty();
        startScreenState= new StartScreenController(nifty, flyCam, inputManager, numPerson, this);
        nifty.fromXml("Interface/screen.xml", "start", startScreenState);
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);   
        // this is the command to switch GUI nifty.gotoScreen("hud");
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState = new BulletAppState();
        bState.setDebugEnabled(true);
        stateManager.attach(bState);
        Assets.loadAssets(assetManager);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        createScene();
        
    }

    public static void main(String[] args) {
        new App().start();
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        
        textPerson.setText("Person: " + numPerson);
        textFlu.setText("Infected: " + 0);
        if(crowd != null){
            for (var p: crowd) {
                p.update(tpf);
            }
        }
        
        
        
        //var pos = crowd.get(1).getPosition();
        //cam.setLocation(new Vector3f(pos.x, pos.y + 3, pos.z));
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private void createScene() {
        // load city
        scene = (Node) assetManager.loadModel("Scenes/test" + ".j3o");
        scene.setName("Simulation_scene");
        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        //setting textPerson
        textPerson.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        textPerson.setColor(ColorRGBA.Black);                             // font color
        textPerson.setLocalTranslation(0,settings.getHeight(), 1); // position
        guiNode.attachChild(textPerson);
        
        /*setting textFlu
        textFlu.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        textFlu.setColor(ColorRGBA.Black);                      // font color
        textFlu.setLocalTranslation(settings.getWidth(),settings.getHeight(), 1); // position
        guiNode.attachChild(textFlu);
        */
        bState.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);
        //v = new Virus(crowd, 2);
        
    }
    
    public void startApp(){
        numPerson = startScreenState.loadP(); 
        var pathCalc = new PathCalculator((Node)rootNode.getChild("Simulation_scene"));
        // create an array of Person and fill it with N Person
        // every Person starts from a random point inside path generator
        crowd = new ArrayList<Person>();
        var pg = new PathGenerator(scene);
        for (int i = 0; i < numPerson; i++) {
            Person p = new Person(scene, pg.getRandomPoint(), this, pathCalc, startScreenState.getMaskP());
            crowd.add(p);
            }
        Thread t = new Virus(crowd, 2);
        t.start();
        System.out.println(numPerson);
        System.out.println(startScreenState.getMaskP());
        var a = new Lightning(this);
        a.setLight();
    }
}
