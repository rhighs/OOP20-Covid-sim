package GUI.Controllers;

import Simulation.Person;
import Simulation.Simulation;

/**
 * @author json
 * Class for preset simulation start
 */
public class SituationComponent {

    public static final int DEFAULT_PEOPLE = 50;

    /**
     * Logic for load worst button
     * set the parameters to start simulation
     */
    public static Simulation.Options getWorst() {
        var prot = Person.Mask.Protection.FP1;
        return new Simulation.Options(
                DEFAULT_PEOPLE,
                DEFAULT_PEOPLE,
                prot
        );
    }

    /**
     * Logic for load best button
     * set the parameters to start simulation
     */
    public static Simulation.Options getBest() {
        var prot = Person.Mask.Protection.FP3;
        return new Simulation.Options(
                DEFAULT_PEOPLE,
                0,
                prot
        );
    }
}
