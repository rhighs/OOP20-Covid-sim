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
public interface Person {
    public enum Mask {
        UP, DOWN
    }
    public Mask getMask();
    public void maskDown();
    public boolean isInfected();
    public void infect();
}
