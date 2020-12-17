class PersonaImpl implements Persona {
    private long x;
    private long y;
    private long z;
    private boolean infected;

    static enum Protection {
        MASK, NONE
    }

    private int id;   // suggested by juri
    private int yolo; // suggested by savini

    // graphics information, such as mesh, models, whatever

    public void move(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void infect() {
        infected = true;
    }

    public boolean isInfected() {
        return infected;
    }

    public void update() {

    }

    public void draw() {

    }
}
