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
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author rob
 */
public class Picker implements ActionListener {

    private InputManager input;
    private CollisionResults results;
    private SimpleApplication app;
    private List<Entity> entities;
    private Camera cam;
    private Node rootNode;

    public Picker(SimpleApplication app, List<Entity> entities) {
        input = app.getInputManager();
        input.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        rootNode = app.getRootNode();
        
        app.getInputManager().addListener(this, "Shoot");

        cam = app.getCamera();

        this.app = app;
        this.entities = entities;
    }

    public Optional<Entity> pickEntity() {
        results = new CollisionResults();
        Spatial node;

        Ray ray = new Ray(app.getCamera().getLocation(), app.getCamera().getDirection());

        rootNode.collideWith(ray, results);

        var found = results.getClosestCollision();
        
        try{
            node = found.getGeometry();
        }catch (Exception ex){
            var err = ex.toString();
            return Optional.empty();
        }
        
        if (node == null) {
            return Optional.empty();
        }
        
        var userData = node.getUserData("entity");

        if (userData == null) {
            return Optional.empty();
        }

        return Optional.of((Entity) userData);
    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        switch(name){
            case "Shoot":
                if(!keyPressed){
                    moveCamToPerson();
                }
                break;
            default:
                break;
        }
    }
    
    private void moveCamToPerson(){
        Person p = null;
        
        try{
            p = (Person) pickEntity().get();
        }catch(Exception ex){
            var err = ex.toString();
        }
            
        if (p != null) {
            cam.setLocation(p.getPosition());
        }
    }

}
