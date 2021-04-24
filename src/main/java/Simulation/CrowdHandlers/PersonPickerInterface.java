package Simulation.CrowdHandlers;

import java.util.Optional;
import Simulation.Person;

public interface PersonPickerInterface {
    /**
     * Picks the person in front of the camera.
     * @return the person picked, or empty if no person
     * was found in front of the camera.
     */
    Optional<Person> pickPerson();
}
