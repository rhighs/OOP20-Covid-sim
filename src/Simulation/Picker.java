    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package Simulation;

    import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
    import com.jme3.collision.CollisionResults;
    import com.jme3.input.InputManager;
    import com.jme3.input.MouseInput;
    import com.jme3.input.controls.ActionListener;
    import com.jme3.input.controls.MouseButtonTrigger;
    import com.jme3.math.Ray;
import com.jme3.scene.Node;
import java.util.List;
import java.util.stream.Collectors;

    /**
     *
     * @author rob
     */
    public class Picker implements ActionListener{

        private InputManager input;
        private CollisionResults results;
        private SimpleApplication app;
        private List<Entity> entities;

        public Picker(SimpleApplication app, List<Entity> entities){
            input = app.getInputManager();
            input.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            input.setCursorVisible(true);
            this.app = app;
            this.entities = entities;
        }


        public boolean pickObject(){
            results = new CollisionResults();
            
            Ray ray = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());
            
            var tempNode = new Node("tmep");
            entities.stream().map( e -> e.getSpatial()).forEach(o -> tempNode.attachChild(o));
            
            tempNode.collideWith(ray, results);
            
            var found = results.getClosestCollision();
            var node = found.getGeometry().getParent();
            var entity = (Entity) node.getUserData("Entity");
            
            return false;
        }
        
        

        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if(name.equals("Shoot") && !keyPressed){
                pickObject();
            }
        }

    }
