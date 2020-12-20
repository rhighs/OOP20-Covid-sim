package Simulation;

interface Person {
    // called every frame, this is the "logic"
    void    update();
    // also called every frame, these are drawing directives
    void    draw();
    void    move(long x, long y, long z);
    void    infect();
    boolean isInfected();
}

