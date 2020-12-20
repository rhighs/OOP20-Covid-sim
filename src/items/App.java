package items;

import com.jme3.app.FlyCamAppState;
import com.jme3.math.Vector3f;
import com.jme3.light.*;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
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

    //collision driven objects
    private BulletAppState ba;
    private RigidBodyControl landscape;
    private CharacterControl player; //the invisible fp element
    private Vector3f walkDirection = new Vector3f();

    //Temporary vectors used on each frame.
    //They here to avoid instanciating new vectors on each frame
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();

    //Image i = new Image(BACKGROUND_IMAGE);
    Node pivot = new Node("pivot");
    Spatial sceneModel;
    static Node spherePivot = new Node("spivot");
    final Box b = new Box(200f, .1f, 160f);
    final Sphere s = new Sphere(100, 100, 1);
    final Spatial geom = new Geometry("sku", s);
    final Spatial geom2 = new Geometry("cubo2", s);
    static float xpos = 0, xRotation = 0;
    static final Vector3f vs = new Vector3f(4, 0, 0);
    static final Vector3f vs1 = new Vector3f(-4, 0, 0);
    static final Vector3f c = new Vector3f(0, -7, -5);
    final float multiplier = 1f / 255;
    final ColorRGBA bgcolor = new ColorRGBA().set(40f * multiplier, 188f * multiplier, 211f * multiplier, 1.0f);
    final CollisionResults results = new CollisionResults();
    ItemWrapper wb, plane;

    public void simpleInitApp() {
        viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f));        
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        wb = new WBox("cubetto", 1, 1, 1, new Vector3f(10, 1, 1));
        plane = new WBox("piano", 20, 0.1f, 20, new Vector3f(10, -1, 1));

        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Red);
        mat.setColor("Diffuse", ColorRGBA.Red);

        Material sphereMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        sphereMat.setColor("Ambient", ColorRGBA.Red);
        sphereMat.setColor("Diffuse", ColorRGBA.Red);
        sphereMat.setBoolean("UseMaterialColors", true);
        sphereMat.setColor("Specular", ColorRGBA.White);
        sphereMat.setFloat("Shininess", 70f);  // [0..128]

        geom.setMaterial(sphereMat);
        geom2.setMaterial(sphereMat);
        plane.setMaterial(sphereMat);
        flyCam.setMoveSpeed(100);
        
        ba = new BulletAppState();
        stateManager.attach(ba);
        
        CollisionShape cs = new CollisionShapeFactory().createMeshShape(plane.getGeometry());
        CapsuleCollsionShape capsule = new CapsuleCollsion 
        landscape = new RigidBodyControl(cs, 0);
        plane.getGeometry().addControl(landscape);

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
        wb.attachToNode(rootNode);
        rootNode.attachChild(pivot);
        rootNode.attachChild(spherePivot);
        //rootNode.attachChild(cube);
        //rootNode.attachChild(pivot);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        xRotation = 4 * tpf;
        wb.move(2 * tpf, 0, 0);
        spherePivot.rotate(0, xRotation, 0);
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

    @Override
    public void onAction(String arg0, boolean arg1, float arg2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
