package Engine.movement;

import com.jme3.cinematic.MotionPath;
import java.util.function.Function;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;

/* This class is a container for many classes which implement some kind of
 * movement algorithm. (think of it as a namespace containing many free functions.
 * To add a new movement algorithm, simply create a new nested class here.
 * It must implement Function<Vector3f, Vector3f>.
 * Too many nested classes? We'll care about that later.
 */
public class MovementComponent {
    
    /*
        Giving this class a Rectangle as such -> this(pos, --new Rectangle(50, 50)--) means that,
        whenever we generate a new point, that point is choosen inside a 100x100 "rectangle".
        As for now we only use rectangles to find new points given a point 'pos'.
        In future implementations we'll see them in use for walls detection and stuff...
    */
    static private final Rectangle DEFAULT_AREA = new Rectangle(50, 50);
    private Vector3f position;
    private final Rectangle area;
    private final MotionPath path;
    private final MotionEvent motionControl;
    private final Spatial spatial;
    
    public MovementComponent(final Spatial spatial, final Vector3f position, final Rectangle area){
        this.position = position;
        this.area = area;
        path = new MotionPath();
        this.spatial = spatial;
        
        motionControl = new MotionEvent(spatial, path);
        motionControl.setDirectionType(MotionEvent.Direction.PathAndRotation);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(2f);
        motionControl.setSpeed(0.1f);
    }
    
    public MovementComponent(final Spatial spatial, final Vector3f position){
        this(spatial, position, DEFAULT_AREA);
    }
    
    public Vector3f getPosition(){
        return position;
    }
    
    public Vector3f getNextPoint(){
        //haha
        PollingArea pArea = new PollingArea(area, 6);
        this.position = this.position.add(pArea.getRandomOffset());
        
        return this.position;
    }
    
    public void randomMove(final int steps){
        for(int i = 0; i<steps; i++){
            
            Vector3f a = new Vector3f(getNextPoint());
            path.addWayPoint(a);
        }
        
        System.out.println(path.getLength());
        motionControl.setSpeed(40/path.getLength());
        motionControl.play();
    }
    
    public MotionPath getPath(){
        return this.path;
    }
    
}