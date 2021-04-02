/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;


/**
 *
 * @author rob
 */
public class SimulationCamera {
    private Camera cam;
    //FlyByCamera refers to all the dynamic concepts of a camera
    private FlyByCamera flyCam;
    private Entity attachedEntity;
    
    public SimulationCamera(final Camera cam, final FlyByCamera flyCam){
        this.cam = cam;
        this.flyCam = flyCam;
    }
    
    public void update(float tpf){
        followEntity();
    }
    
    public Vector3f getLocation(){
        return cam.getLocation();
    }
    
    public Vector3f getDirection(){
        return cam.getDirection();
    }
    
    /*
        this method sets the cam position as the
        same given by the entity.
    */
    public void attachToEntity(final Entity entity){
        this.attachedEntity = entity;
    }
    
    public void detachEntity(){
        if(this.attachedEntity != null){
            this.attachedEntity = null;
        }
    }
    
    private void followEntity(){
        if(attachedEntity == null){
            return;
        }
        
        var pos = attachedEntity.getPosition();
        cam.setLocation(pos);
    }
    
}
