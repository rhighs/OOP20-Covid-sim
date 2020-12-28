package Simulation;

import java.util.List;
import java.util.ArrayList;
//import items.Point2d;
//mport items.Rect2d;
//import items.Geometric;

/* World contains the areas */
class WorldHandler {
    class Area {
       // Rect2d  area;
        List<Person> personList; // array of references (pointers) to persons

        Area() {
            personList = new ArrayList<Person>();
        }
        
        void assign(Person p) {
            personList.add(p);
        }

        void moveAll() {
            for (Person p : personList) {
                //p.move();
                //if (!Geometric.inside(p.position(), area)) {
                  //  reassign(p);
                    // and then delete p
                    // if you use a filter here instead you're cool
                //}
            }
        }
    }
    
    List<Area> areaList;
    Area topLeft, topRight, bottomLeft, bottomRight;
    
    WorldHandler() {
        areaList = new ArrayList<>();
    }

    void reassign(Person p) {
        // find the correct area
        for (Area a : areaList) {
            //if (Geometric.inside(p.position(), a.area)) {
                a.assign(p);
            }
        }
        // deletion from the previous area must be done by the area itself
    }
//}

