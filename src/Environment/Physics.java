/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Environment;
import com.jme3.bullet.BulletAppState;

/**
 *
 * @author rob
 */
class Physics {
    private BulletAppState bullet;
    
    public Physics(BulletAppState bullet){
        this.bullet = bullet;
    }
}
