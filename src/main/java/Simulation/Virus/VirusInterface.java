package Simulation.Virus;

import java.util.List;
import Simulation.Person;

public interface VirusInterface extends Runnable {

    /**
     * Shutdown the virus. As the virus is a thread
     * that always runs, this is needed.
     */
    public void shutdown();

    /**
     * Force infection of a single person.
     * @param victim the person to infect.
     */
    void forceInfection(Person victim);

    /**
     * Heal every person who has been infected.
     * After this method is called, there is always at least
     * one person who is still infected (this is needed to keep
     * the virus running)
     */
    void resumeInfected();

    /**
     * Stop the spreading of the virus.
     * No new people will be infected after calling this method.
     */
    void stopSpreading();

    /**
     * Resumes the spreading of the virus.
     */
    void resumeSpreading();

    /**
     * Gets the number of infected people
     */
    int getInfectedNumb();

    /**
     * Updates the list of people.
     * This should only be called from Simulation to keep Virus
     * synchronized with the real list.
     */
    void updateCrowd(List<Person> p);
}
