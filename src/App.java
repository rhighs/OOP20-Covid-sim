
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
import Engine.movement.AIMovement;
import com.jme3.ai.navmesh.NavMesh;
import com.jme3.math.ColorRGBA;
import java.util.Timer;
import java.util.TimerTask;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.ai.navmesh.Path.Waypoint;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.Ray;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import jme3tools.optimize.GeometryBatchFactory;

/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication implements ActionListener {

    private BulletAppState bState;
    Node player;
    BetterCharacterControl pControl;
    MotionPath mp;

    NavMeshPathfinder navi;
    boolean naviOn = false;

    Vector3f target = new Vector3f(0, 0, 0);

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


        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        initSceneAndPlayer();
        initInput();

    }

    private void initInput() {
        inputManager.addMapping("Mouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, new String[]{"Mouse", "Space"});

    }

    private void initSceneAndPlayer() {
        Spatial scene = assetManager.loadModel("test_porcodio.j3o");
        bState.getPhysicsSpace().addAll(scene);
        bState.setDebugEnabled(true);

        rootNode.attachChild(scene);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        Node p = (Node) assetManager.loadModel("Models/Ninja/Ninja.mesh.xml").scale(0.05f);
        player = new Node("PlayerNode");
        player.setLocalTranslation(0, 10, 0);
        player.attachChild(p);

        pControl = new BetterCharacterControl(1.5f, 9f, 15);
        player.addControl(pControl);
        pControl.setGravity(new Vector3f(0, -10, 0));

        pControl.setJumpForce(new Vector3f(0, 30, 0));

        bState.getPhysicsSpace().add(pControl);
        bState.getPhysicsSpace().addAll(player);

        rootNode.attachChild(player);

        Node n = (Node) scene;
        Geometry geom = (Geometry) n.getChild("NavMesh");

        Mesh mesh = geom.getMesh();
        NavMesh navMesh = new NavMesh(mesh);
        System.out.println(player.getLocalTranslation());

        navi = new NavMeshPathfinder(navMesh);

        navi.computePath(target);
    }

    @Override
    public void simpleUpdate(float tpf) {
        System.out.println(naviOn);
        if (naviOn) {
            Waypoint waypoint = navi.getNextWaypoint();
            if (waypoint == null) {
                System.out.println("waypoint is null coglione");
                return;
            }

            Vector3f v = waypoint.getPosition().subtract(player.getLocalTranslation());
            pControl.setWalkDirection(v.mult(tpf).mult(100));
            if (player.getLocalTranslation().distance(waypoint.getPosition()) <= 4 && !navi.isAtGoalWaypoint()) {
                navi.goToNextWaypoint();
            }

            System.out.println(player.getLocalTranslation() + " posizione player    ");
            
            if (navi.getPath().getLast().getPosition().equals(player.getLocalTranslation())) {
                navi.clearPath();
                System.out.println(player.getLocalTranslation() + " BASTAAAAAAAAAA");   
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Space") && isPressed) {
            naviOn = !naviOn;
        }
        if (binding.equals("Mouse") && isPressed) {
            CollisionResults cr = new CollisionResults();
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            rootNode.collideWith(ray, cr);
            if (cr.size() != 0) {
                target = cr.getClosestCollision().getContactPoint();
                System.out.println(target);
                navi.setPosition(player.getLocalTranslation());
                navi.computePath(target);
            }
        }
    }
}
