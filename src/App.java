import com.jme3.renderer.RenderManager;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.math.Plane;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
 
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.*;
import com.jme3.bullet.control.RigidBodyControl;
 
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
 
/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 */
public class App extends SimpleApplication {
 
    static class Entityo implements PhysicsCollisionListener {
        String name;
        Mesh mesh; // box, circle, etc.
        Spatial geom;
        Material mat;
        float x = 1, y = 1, z = 1; // initialized to 0
 
        Entityo(Node n, Material m, RigidBodyControl g) {
            name = "ghost controlled";
            mesh = new Box(x, y, z);
            geom = new Geometry(name, mesh);
            mat  = m;
            mat.setColor("Color", ColorRGBA.Blue);
            geom.setMaterial(mat);
            n.attachChild(geom);
            geom.addControl(g);
        }
 
        void move(float x, float y, float z) {
            this.x += x;
            this.y += y;
            this.z += z;
            geom.setLocalTranslation(this.x, this.y, this.z);
            //System.out.format("moved to: %f %f %f\n", (double) this.x, (double) this.y, (double) this.z);
        }
 
        public void collision(PhysicsCollisionEvent event) {
            System.out.println("coll");
        }
    }
 
    private BulletAppState bulletAppState;
    private Entityo e, e2;
 
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
 
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        RigidBodyControl ghost1 = new RigidBodyControl(new BoxCollisionShape(new Vector3f(1.5f, 1.5f, 1.5f)));
        RigidBodyControl ghost2 = new RigidBodyControl(new BoxCollisionShape(new Vector3f(1.5f, 1.5f, 1.5f)));
        e = new Entityo(rootNode, new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"), ghost1);
        e.move(3, 0, 0);
        e2 = new Entityo(rootNode, new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"), ghost2);
        e2.move(-1, 0, 0); 
        getPhysicsSpace().add(ghost1);
        getPhysicsSpace().add(ghost2);
        getPhysicsSpace().addCollisionListener(e2);
    }
 
    @Override
    public void simpleUpdate(float tpf) {
        e2.move(1 * tpf, 0, 0);
    }
 
    @Override
    public void simpleRender(RenderManager rm) {
        
    }
 
    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
}
 