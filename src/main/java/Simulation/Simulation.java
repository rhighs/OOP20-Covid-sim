package Simulation;

import java.util.List;

public interface Simulation {
    /**
     * Starts the simulation.
     * @param options the options with which to start the simulation.
     */
    public void start(Options options);

    /**
     * Updates the simulation.
     * @param tpf time per frame
     * @throws IllegalStateException
     * This method simply updates every person.
     */
    public void update();

    /**
     * Getter for person list.
     * @return The list of people.
     */
    public List<Person> getPersonList();

    /**
     * Getter for person count.
     * @return the person count, which corresponds to the total number of people.
     */
    public int getPersonCount();

    /**
     * Getter for infected person count.
     * @return the number of infected people
     */
    public int getInfectedNumb();

    /**
     * Changes the status of the masks for all people.
     * If the people who have masks were wearing them, by calling
     * this method the people will stop wearing them, and viceversa.
     */
    public void changeMaskState();

    /**
     * Resumes the spreading of the virus.
     */
    public void resumeInfected();

    /**
     * Force a number of people to be infected.
     * @param infected the number of people to be infected.
     */
    void setInfected(int infected);

    /**
     * Shutdowns any thread started by the simulation.
     */
    void shutdown();

    /**
     * This class holds the options for starting the simulation.
     * The simulation needs to know in advance the following things:
     * - starting number of people (@numPerson)
     * - starting number of people who use masks (@numMasks)
     * - what kind of protection the mask use. This is shared between
     *   every person who use a mask.
     */
    public static class Options {
        public final int numPerson;
        public final int numMasks;
        public final Person.Mask.Protection protection;

        public Options(int p, int m, Person.Mask.Protection pr) {
            numPerson = p;
            numMasks = m;
            protection = pr;
        }
    }
}

