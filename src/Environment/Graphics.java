package Environment;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 *
 * @author Rob
 */
public class Graphics {
    private AssetManager assetManager;
    private Node rootNode;
    
    public Graphics(AssetManager assetManager, Node rootNode){  
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }
    
    public void addToScene(final Node elem){
        rootNode.attachChild(elem);
    }
    
}
