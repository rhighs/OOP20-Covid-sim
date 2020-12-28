package Simulation;

import Engine.items.Entityp;

public class Person {
    //protected Grid grid;
    protected Entityp entity;
    protected boolean infection;
    //private GraphicsComponent gfx;
    //private PhysicsComponent  phyc;
    protected float gx, gy;

    public Person(float x, float y, boolean infection) {
        this.gx   = x;
        this.gy   = y;
        //this.grid = grid;
        //this.grid.add(this);
        this.infection = infection;
        
        entity.setUserObject(this);
    }
    
    public void infect(){
        if(!infection){
            infection = true;
        }
    }
    
    public boolean isInfected(){
        return infection;
    }
    
    public Entityp getEntity(){
        return entity;
    }

    public void move(float x, float y) {
        //grid.move(this, x, y);
        entity.moveOnPlane(x, y);
    }

    public boolean inRange(Person p) {
        return false;
    }
    
    public void handleCollision(Person p) {
        
    }
}