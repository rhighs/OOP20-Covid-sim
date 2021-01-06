package Simulation;

import com.jme3.cinematic.MotionPath;
import java.util.function.Function;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* This class is a container for many classes which implement some kind of
 * movement algorithm. (think of it as a namespace containing many free functions.
 * To add a new movement algorithm, simply create a new nested class here.
 * It must implement Function<Vector3f, Vector3f>.
 * Too many nested classes? We'll care about that later.
 */
public class MovementComponent {
    /* SimpleWalk simply makes the person walk to one direction. */
    private final Vector3f position;
    private final Rectangle movingArea;
    private final Random rand;
    
    private class Rectangle{
        final float w;
        final float h;
        
        public Rectangle(final float width, final float height){
            w = width;
            h = height;
        }
        
        final float getWidth(){
            return w;
        }
        
        final float getHeight(){
            return h;
        }
    }
    
    public MovementComponent(final Vector3f position){
        this.position = position;
        movingArea = new Rectangle(100, 100);
        rand = new Random();
    }
    
    public Vector3f getPosition(){
        return position;
    }
    
    public Vector3f getNextPoint(){
        final float xCoord = rand.nextFloat() % movingArea.getWidth();
        final float zCoord = rand.nextFloat() % movingArea.getHeight();
        
        final Vector3f offset = new Vector3f(xCoord, 0, zCoord);
        
        return position.add(offset);
    }
    
}