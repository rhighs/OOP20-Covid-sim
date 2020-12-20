/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

import com.jme3.*;


public class MoveTest{
    public ActionListener action;
    public AnalogListener analog; 
    
    Spatial g;
    
    public MoveTest(Spatial g){
        this.g = g;
        InitActionListener();
        InitAnalogListener();
    }
    
    private void InitActionListener(){
        this.action = new ActionListener(){
            @Override
            public void onAction(String name, boolean keyPressed, float tpf){
                if(name.equals("Right") && !keyPressed){
                    App.xpos+=10*tpf;
                    g.setLocalTranslation(new Vector3f(App.xpos, 0, 0));
                }else if(name.equals("Left") && !keyPressed){
                    App.xpos-=10*tpf;
                    g.setLocalTranslation(new Vector3f(App.xpos, 0, 0));
                }
            }
        };
    }
    
    private void InitAnalogListener(){
        this.analog = new AnalogListener(){

            @Override
            public void onAnalog(String name, float value, float tpf) {
                if(name.equals("Right")){
                    App.xpos+=10*tpf;
                    g.setLocalTranslation(new Vector3f(App.xpos, -1, 0));
                }else if(name.equals("Left")){
                    App.xpos-=10*tpf;
                    g.setLocalTranslation(new Vector3f(App.xpos, -1, 0));
                }            
            }
        };
    }
    
}
