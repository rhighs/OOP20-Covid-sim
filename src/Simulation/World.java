package Simulation;

import java.util.List;
import java.util.ArrayList;

/* World contains the areas */
class WorldHandler {
    class Area {
        List<Person> personList; // array of references (pointers) to persons

        Area() {
            personList = new ArrayList<Person>();
        }
        
        void assign(Person p) {
            personList.add(p);
        }

        void moveAll() {
            for (Person p : personList) {
            }
        }
    }
    
    List<Area> areaList;
    Area topLeft, topRight, bottomLeft, bottomRight;
    
    WorldHandler() {
        areaList = new ArrayList<>();
    }

    void reassign(Person p) {
        for (Area a : areaList) {
                a.assign(p);
        }
    }
}

