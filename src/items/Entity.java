import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.scene.Mesh;

class Entity {
    String name;
    Spatial sp;
    Material mat;
    Mesh mesh;  // box, circle, etc., can be null
    Vector3f pos;
    
    Entity(String name, Node parent) {
        this.name = name;
        parent.attachChild(sp);
    }

    Entity(String name, Node parent, String modelName) {
        this.name = name;
        parent.attachChild(sp);
        // figure out a way to access assetManager
        // sp = assetManager.loadModel(modelName);
    }

    Vector3f position() {
        return pos;
    }
    
    void move(Vector3f newPos) {
        sp.setLocalTranslation(newPos);
        pos = newPos;
    }
}
