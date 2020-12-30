import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import Simulation.Person;

/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication /*implements ActionListener*/ {
    private BulletAppState bState;

    public App() {
        //super(new FlyCamAppState());
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    public void simpleInitApp() {
        bState = new BulletAppState();
        stateManager.attach(bState);
        bState.setDebugEnabled(true);
        Person p = new Person(rootNode, assetManager, bState);
        p.move(new Vector3f(1, 1, 1));
    }

    @Override
    public void simpleUpdate(float tpf) {
        p.move(1, 1, 1);
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
