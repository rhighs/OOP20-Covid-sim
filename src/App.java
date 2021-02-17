import java.util.ArrayList;
import java.util.List;

import com.jme3.ai.navmesh.NavMesh;
import com.jme3.ai.navmesh.NavMeshPathfinder;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.renderer.RenderManager;

import Components.PathGenerator;
import Simulation.Assets;
import Simulation.Person;

/**
 * @author chris, rob, jurismo, savi
 */
public class App extends SimpleApplication implements ActionListener {
    // constants
    final int NUM_PERSON = 2;

    private BulletAppState bState;
    private List<Person> crowd;
    // navi stuff
    private NavMeshPathfinder navi;
    // player stuff
    private Node player;
    private boolean naviOn = false;
    private BetterCharacterControl pControl;
    private Vector3f target = new Vector3f(10, 2, 35);

    public App() {
        //super(new FlyCamAppState());
    }

    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        bState = new BulletAppState();
        stateManager.attach(bState);
        Assets.loadAssets(assetManager);
        flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(20, 20, 5));
        createScene();
        initInput();
    }

    public static void main(String[] args) {
        new App().start();
    }

    @Override
    public void simpleUpdate(float tpf) {
        for (var p: crowd) {
            p.update(tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        /*
        if (binding.equals("Space") && isPressed)
            naviOn = !naviOn;
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
        */
    }

    private void initInput() {
        inputManager.addMapping("Mouse", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, new String[]{"Mouse", "Space"});
    }

    private void createScene() {
        // load city
        Node scene = (Node) assetManager.loadModel("Models/city" + ".j3o");
        scene.setLocalTranslation(new Vector3f(2, -10, 1));
        bState.getPhysicsSpace().addAll(scene);
        rootNode.attachChild(scene);

        // create the path finder
        navi = new NavMeshPathfinder(createNavMesh(scene));
        navi.computePath(target);

        // create an array of Person and fill it with 100 Person
        // every Person starts from a random point inside path generator
        crowd = new ArrayList<Person>();
        var pg = new PathGenerator(scene);
        for (int i = 0; i < NUM_PERSON; i++) {
            Person p = new Person(scene, pg.getRandomPoint(), this);
            crowd.add(p);
        }

        // create a light so we can actually see the ninjas
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    private void initPlayer() {
        Node p = (Node) assetManager.loadModel("Models/u.j3o").scale(1);
        player = new Node("PlayerNode");
        player.setLocalTranslation(0, 10, 0);
        player.attachChild(p);
        pControl = new BetterCharacterControl(0.5f, 9f, 15);
        pControl.setGravity(new Vector3f(0, -10, 0));
        pControl.setJumpForce(new Vector3f(0, 30, 0));
        player.addControl(pControl);
        bState.getPhysicsSpace().add(pControl);
        bState.getPhysicsSpace().addAll(player);
        rootNode.attachChild(player);
    }

    private NavMesh createNavMesh(Node scene) {
        Geometry geom = (Geometry) scene.getChild("NavMesh");
        return new NavMesh(geom.getMesh());
    }

    private void updatePlayer(float tpf) {
        System.out.println(Runtime.getRuntime().freeMemory());
        // if (naviOn) {
        //     Waypoint waypoint = navi.getNextWaypoint();
        //     if (waypoint == null)
        //         return;
        //     Vector3f v = waypoint.getPosition().subtract(player.getLocalTranslation());
        //     pControl.setWalkDirection(v.normalize().mult(50));
        //     pControl.setViewDirection(v.negate());
        //     if (player.getLocalTranslation().distance(waypoint.getPosition()) <= 1 && !navi.isAtGoalWaypoint()) {
        //         navi.goToNextWaypoint();
        //     }
        //     if (navi.isAtGoalWaypoint()) {
        //         navi.clearPath();
        //         naviOn = false;
        //         pControl.setWalkDirection(Vector3f.ZERO);
        //     }
        // }
    }
}
