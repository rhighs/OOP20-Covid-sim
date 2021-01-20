
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import Simulation.Person;
import Simulation.Wall;
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
import Engine.movement.PathGenerator;

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
import com.jme3.math.Matrix3f;
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
    List<Person> crowd;

    NavMeshPathfinder navi;
    boolean naviOn = false;

    Vector3f target = new Vector3f(10, 2, 35);

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
        Spatial scene = assetManager.loadModel("Models/city" + ".j3o");
        crowd = new ArrayList<Person>();

        var pg = new PathGenerator(scene);

        for (int i = 0; i < 100; i++) {
            crowd.add(new Person(scene, pg.getRandomPoint(), this));
        }

        for (var c : crowd) {
            c.randMov();
        }

        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        bState.getPhysicsSpace().addAll(scene);
        bState.setDebugEnabled(false);

        rootNode.attachChild(scene);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);

        Node p = (Node) assetManager.loadModel("Models/u.j3o").scale(1);

        player = new Node("PlayerNode");
        player.setLocalTranslation(0, 10, 0);
        player.attachChild(p);

        pControl = new BetterCharacterControl(0.5f, 9f, 15);
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

        navi = new NavMeshPathfinder(navMesh);

        navi.computePath(target);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
        System.out.println(Runtime.getRuntime().freeMemory());

        for (var p : crowd) {
            p.update(tpf);
        }

        if (naviOn) {
            Waypoint waypoint = navi.getNextWaypoint();
            if (waypoint == null) {
                return;
            }

            Vector3f v = waypoint.getPosition().subtract(player.getLocalTranslation());
            pControl.setWalkDirection(v.normalize().mult(50));
            pControl.setViewDirection(v.negate());
            if (player.getLocalTranslation().distance(waypoint.getPosition()) <= 1 && !navi.isAtGoalWaypoint()) {
                navi.goToNextWaypoint();
            }

            if (navi.isAtGoalWaypoint()) {
                navi.clearPath();
                naviOn = false;
                pControl.setWalkDirection(Vector3f.ZERO);
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
