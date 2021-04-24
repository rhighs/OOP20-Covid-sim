package GUI.Controllers;

import Simulation.Person;
import Simulation.Simulation;

public class SituationControl {



    public Simulation.Options getWorst() {
        var prot = Person.Mask.Protection.FP1;
        return new Simulation.Options(
                StartScreenController.DEFAULT_PERSON,
                StartScreenController.DEFAULT_PERSON,
                prot
        );
    }

    public Simulation.Options getBest() {
        var prot = Person.Mask.Protection.FP3;
        return new Simulation.Options(
                StartScreenController.DEFAULT_PERSON,
                0,
                prot
        );
    }
}
