package Simulation;

import java.util.Set;

public interface Person extends Entity {
    /**
     * Sets the person's infection status.
     */
    void infect();

    /**
     * Unsets the person's infection status.
     */
    void heal();

    /**
     * @return whether the person is infected.
     */
    boolean isInfected();

    /**
     * @return a representation of the person's mask
     */
    public Mask getMask();

    /**
     * Sets the person's mask status the down. This has the effect
     * of the person "removing" the mask.
     */
    void maskDown();

    /**
     * Switches the person's mask state.
     */
    void switchMaskState();

    /**
     * Gets all the people currently near or adjacent to this person.
     */
    Set<Person> getAdjacentPeople();

    /**
     * Gets the already memorized people. These are not necessarily the
     * currently adjacent people.
     */
    Set<Person> getLastAdjacentPeople();

    /**
     * Memorizes the people in set people as the people adjacent to this person.
     * Ideally, these should be set to the result of getAdjacentPeople().
     * @param people the set of people to memorize
     */
    void setLastAdjacentPeople(Set<Person> people);

    /**
     * A class that models a mask.
     * - protection: this indicates how much protection a mask offers. It is
     *   modelled after real world mask protection. The better the
     *   protection is, the less probability a person will have to get
     *   infected.
     * - status: indicates whether the person is using the mask or not. A
     *   person who doesn't have a mask won't get any protection.
     */
    public static class Mask {
        private final Protection protection;
        private Status status;

        /**
         * @param protection
         * @param status
         */
        public Mask(Protection protection, Status status) {
            this.status = status;
            this.protection = protection;
        }

        /**
         * Returns the status of the mask.
         */
        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        /**
         * Return the mask protection.
         */
        public Protection getProtection() {
            return protection;
        }

        public enum Status {
            UP,
            DOWN
        }

        public enum Protection {
            FP1,
            FP2,
            FP3,
        }
    }
}

