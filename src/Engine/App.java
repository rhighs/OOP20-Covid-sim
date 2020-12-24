package Engine;

import Engine.items.BoxEntity;
import Engine.items.Entity;
import com.jme3.app.FlyCamAppState;
import com.jme3.math.Vector3f;
import com.jme3.light.*;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.*;
import com.jme3.scene.*;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.MaterialDef;
import java.util.stream.*;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class App extends SimpleApplication implements ActionListener {

    public App() {
        super(new FlyCamAppState());
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

    //coliision driven objects
    private Spatial sceneModel;
    private BulletAppState bulletAppState;
    private RigidBodyControl landscape;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;

    //Temporary vectors used on each frame.
    //They here to avoid instanciating new vectors on each frame
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private CharacterControl player;
    private CharacterControl movingPlane;

    //Image i = new Image(BACKGROUND_IMAGE);
    Node pivot = new Node("pivot");
    static Node spherePivot = new Node("spivot");
    final Box b = new Box(200f, .1f, 160f);
    final Sphere s = new Sphere(100, 100, 1);
    final Spatial geom = new Geometry("sku", s);
    final Spatial geom2 = new Geometry("cubo2", s);
     float xpos = 0, xRotation = 0;
    static final Vector3f vs = new Vector3f(4, 0, 0);
    static final Vector3f vs1 = new Vector3f(-4, 0, 0);
    static final Vector3f c = new Vector3f(0, -7, -5);
    final float multiplier = 1f / 255;
    final ColorRGBA bgcolor = new ColorRGBA().set(40f * multiplier, 188f * multiplier, 211f * multiplier, 1.0f);
    final CollisionResults results = new CollisionResults();
    Entity wb;
    Entity plane;
    Entity plane1;

    public void simpleInitApp() {
        flyCam.setMoveSpeed(100);
        viewPort.setBackgroundColor(bgcolor);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");        

        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Red);

        Material sphereMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setColor("Ambient", ColorRGBA.Red);
        sphereMat.setColor("Diffuse", ColorRGBA.Red);
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Specular", ColorRGBA.White);
        sphereMat.setFloat("Shininess", 70f);  // [0..128]

        wb = new BoxEntity(new Box(1,2,3), sphereMat, 1);
        plane = new BoxEntity(new Box(20, .1f, 20), sphereMat, new Vector3f(10, -1, 1), 1);
        plane1 = new BoxEntity(new Box(20, .1f, 20), sphereMat, new Vector3f(30, 3, 1), 1);


        bulletAppState = new BulletAppState();

        bulletAppState.setDebugEnabled(true);

        stateManager.attach(bulletAppState);

        CollisionShape cs = new CollisionShapeFactory().createMeshShape(plane.getGeometry());
        CollisionShape cs1 = new CollisionShapeFactory().createMeshShape(plane1.getGeometry());

        landscape = new RigidBodyControl(cs, 0 /*mass index*/);
        RigidBodyControl landscape1 = new RigidBodyControl(cs1, 0 /*mass index*/);

        plane.getGeometry().addControl(landscape);
        plane1.getGeometry().addControl(landscape1);

        CapsuleCollisionShape cap = new CapsuleCollisionShape(1.5f, 6f, 1);
        CollisionShape c = new BoxCollisionShape(new Vector3f(10.0f, -1.0f, 1.0f));
        movingPlane = new CharacterControl(cs, 0.5f);
        player = new CharacterControl(cap, 0.05f);

        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(new Vector3f(0, -40f, 0));

        player.setPhysicsLocation(new Vector3f(0, 10, 0));

        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(landscape1);
        bulletAppState.getPhysicsSpace().add(player);
        bulletAppState.getPhysicsSpace().add(movingPlane);

        setUpKeys();

        geom.setMaterial(sphereMat);
        geom2.setMaterial(sphereMat);
        plane.setMaterial(sphereMat);
        plane1.setMaterial(sphereMat);

        wb.setMaterial(sphereMat);
        geom2.setLocalTranslation(vs1);
        geom.setLocalTranslation(vs);

        //inputManager.addListener(new MoveTest(cube).action, "Right", "Left");
        //inputManager.addListener(new MoveTest(plane.getGeometry()).analog, "Right", "Left");
        setLight();
        setKeysMapping();

        spherePivot.attachChild(geom);
        spherePivot.attachChild(geom2);
        plane.attachToNode(rootNode);
        plane1.attachToNode(rootNode);

        wb.attachToNode(rootNode);
        rootNode.attachChild(pivot);
        rootNode.attachChild(spherePivot);
        //rootNode.attachChild(cube);
        //rootNode.attachChild(pivot);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        xRotation++;
        //spherePivot.rotate(0, xRotation, 0);
        plane.move(2*tpf,0,0);
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    public void setLight() {
        DirectionalLight sunn = new DirectionalLight();
        sunn.setColor(ColorRGBA.White);
        sunn.setDirection(new Vector3f(10f, -10f, -10f));

        rootNode.addLight(sunn);
    }

    public void setKeysMapping() {
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
    }

    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right = isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down")) {
            down = isPressed;
        } else if (binding.equals("Jump")) {
            if (isPressed) {
                player.jump();
            }
        }
    }
}
