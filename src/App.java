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
import Engine.Assets;

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
        /*
        // Load a model from test_data (OgreXML + material + texture)
        Spatial ninja = assetManager.loadModel("Models/Ninja/Ninja.mesh.xml");
        ninja.scale(0.05f, 0.05f, 0.05f);
        ninja.rotate(0.0f, -3.0f, 0.0f);
        ninja.setLocalTranslation(0.0f, -5.0f, -2.0f);
        rootNode.attachChild(ninja);*/
        // You must add a light to make the model visible

        bState = new BulletAppState();
        stateManager.attach(bState);
        bState.setDebugEnabled(true);
        Assets.loadAssets(assetManager);
        
        Wall wall = new Wall(2.5f, 2.5f, 1.0f, rootNode, bState);
        wall.setPosition(new Vector3f(2.0f, -2.5f, 0.0f));
        // Box box = new Box();
        // Spatial wall = new Geometry("Box", box );
        // Material mat_brick = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        // mat_brick.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
        // wall.setMaterial(mat_brick);
        // wall.setLocalTranslation();
        // rootNode.attachChild(wall);

        /*
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        p = new Person(rootNode, assetManager, bState);
        */
        flyCam.setMoveSpeed(100);
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
    }

    @Override
    public void simpleUpdate(float tpf) {
        p.update(tpf);
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
