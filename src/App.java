
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import Simulation.Person;
import Simulation.Wall;
import Simulation.Movements;
import Simulation.InfectionImpl;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import Engine.Assets;
import com.jme3.math.ColorRGBA;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication /*implements ActionListener*/ {

    private BulletAppState bState;
    private Person p, a, b, d;

    public App() {
        //super(new FlyCamAppState());
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState = new BulletAppState();
        stateManager.attach(bState);
        Assets.loadAssets(assetManager);

        Wall wall = new Wall(2.5f, 2.5f, 1.0f, rootNode, bState);
        wall.setPosition(new Vector3f(2.0f, -2.5f, 0.0f));
        p = new Person(rootNode, assetManager, bState);
        a = new Person(rootNode, assetManager, bState);
        b = new Person(rootNode, assetManager, bState);
        d = new Person(rootNode, assetManager, bState);



        flyCam.setMoveSpeed(100);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {
        p.update(tpf);
        a.update(tpf);
        b.update(tpf);
        d.update(tpf);
        p.getM().getPath().enableDebugShape(assetManager, rootNode);
    }

    /*
    @Override
    public void simpleRender(RenderManager rm) {

    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
    }
     */
}
