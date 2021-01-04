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
public interface Mask {
    public enum MaskProtection{
        FFP1, FFP2, FFP3 
    }
    public enum MaskStatus {
        DOWN, UP
    }
    public MaskProtection getProtection();
    public MaskStatus getStatus();
    public void maskDown();
}
