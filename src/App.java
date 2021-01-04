import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import Simulation.Person;
import Simulation.InfectionImpl;


/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication /*implements ActionListener*/ {
    private BulletAppState bState;

    Person p;
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
        p = new Person(rootNode, assetManager, bState);
        flyCam.setMoveSpeed(100);
    }

    @Override
    public void simpleUpdate(float tpf) {
        p.move(new Vector3f(1*tpf,0,0));
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
