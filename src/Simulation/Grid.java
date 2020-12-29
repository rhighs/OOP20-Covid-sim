// package Simulation;

// import java.util.List;
// import java.util.LinkedList;
// import java.util.ArrayList;

// final class Grid {
//     public static final int NUM_CELLS = 2;
//     public static final int CELL_SIZE = 10;
//     List<List<Person>> cells;
    
//     public int pos(int x, int y) {
//         return y*NUM_CELLS+x;
//     }
    
//     public Grid() {
//         cells = new ArrayList<>(NUM_CELLS*NUM_CELLS);
//         for (int x = 0; x < NUM_CELLS; x++) {
//             for (int y = 0; y < NUM_CELLS; y++) {
//                 cells.set(pos(x, y), new LinkedList<>());
//             }
//         }
//     }
    
//     void add(Person p) {
//         int cx = (int) (p.gx/CELL_SIZE);
//         int cy = (int) (p.gy/CELL_SIZE);
//         cells.get(pos(cx, cy)).add(p);
//     }
    
//     void handleCollision() {
//         for (int x = 0; x < NUM_CELLS; x++) {
//             for (int y = 0; y < NUM_CELLS; y++) {
//                 handleCell(cells.get(pos(x, y)));
//             }
//         }
//     }

//     void handleCell(List<Person> listp) {
//         for (var p : listp) {
//             for (var q : listp) {
//                 if (p.inRange(q)) {
//                     p.handleCollision(q);
//                 }
//             }
//         }
//     }

//     void move(Person p, float x, float y) {
//         int oldcx = (int)(p.gx / CELL_SIZE);
//         int oldcy = (int)(p.gy / CELL_SIZE);
//         int cx    = (int)(x / CELL_SIZE);
//         int cy    = (int)(y / CELL_SIZE);
//         p.gx = x;
//         p.gy = y;
//         if (oldcx == cx && oldcy == cy) {
//             return;
//         }
//         cells.get(pos(oldcx, oldcy)).remove(p);
//         cells.get(pos(cx, cy)).add(p);
//     }
// }
