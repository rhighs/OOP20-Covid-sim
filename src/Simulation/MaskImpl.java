/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

/**
 *
 * @author simon
 */
public class MaskImpl implements Mask{
    
    private MaskProtection protection;
    private MaskStatus status;
    private MaskImpl(MaskProtection p, MaskStatus s){
        this.protection = p;
        this.status = s;
    }
    public static Mask create(MaskProtection p, MaskStatus s){
        return new MaskImpl(p,s);
    }
    @Override
    public MaskProtection getProtection(){
        return this.protection;
    }
    @Override
    public MaskStatus getStatus(){
        return this.status;
    }
    @Override
    public void maskDown(){
        this.status = MaskStatus.DOWN;
    }
}
