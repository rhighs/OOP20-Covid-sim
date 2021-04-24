package Components.Movement;

/**
 * An implementation of the stuck manager that uses the system timer.
 */
public class SystemTimeStuckManager implements StuckManager {

    /**
     * Stuck tolerance in seconds.
     */
    public static final int STUCK_TOLERANCE = 10;

    private Long stuckStartTime;

    @Override
    public Boolean isStuck() {
        if (stuckStartTime == null) {
            return false;
        }

        var currentTime = System.currentTimeMillis();
        var timeSpan = currentTime - stuckStartTime;

        return timeSpan >= STUCK_TOLERANCE * 1000;
    }

    @Override
    public void toggle() {

        if (stuckStartTime != null) {
            return;
        }

        stuckStartTime = System.currentTimeMillis();
    }

    @Override
    public void reset() {
        stuckStartTime = null;
    }
}
