package Environment.Services.InputHandling;

/**
 * @author rob
 */
public interface InputAction extends Runnable {

    /**
     *  Function to call when its corresponding key is pressed at runtime.
     */
    void run();
}