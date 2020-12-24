package Engine;

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
import Engine.items.MeshEntity;

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
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
    
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
    }
}
