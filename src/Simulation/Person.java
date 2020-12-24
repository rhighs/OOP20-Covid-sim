package Simulation;

class Person {
    protected Grid grid;
    //private GraphicsComponent gfx;
    //private PhysicsComponent  phyc;
    protected float gx, gy;

    public Person(Grid grid, float x, float y) {
        this.gx   = x;
        this.gy   = y;
        this.grid = grid;
        this.grid.add(this);
    }

    public void move(float x, float y) {
        grid.move(this, x, y);
    }

    public boolean inRange(Person p) {
        return false;
    }
    
    public void handleCollision(Person p) {
        
    }
}

