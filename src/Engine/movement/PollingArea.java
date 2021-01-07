package Engine.movement;

import static com.jme3.math.FastMath.isPowerOfTwo;
import static com.jme3.math.FastMath.sqrt;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author rob
 */
public class PollingArea {

    /* just some 5am madness, i'll explain everything, no worries...*/
    static final Random rand = new Random();
    final private int sideTiles;
    final private List<List<SubArea>> sec;
    final private Rectangle area;
    final private int totalCells;

    public PollingArea(final Rectangle area, final int sideTiles) {
        sec = new ArrayList<>();
        this.area = area;
        this.sideTiles = sideTiles;
        this.totalCells = sideTiles*sideTiles;        

        initSectors();
    }

    private class SubArea {

        final public Vector2f coordsOffset;
        final public Vector2f constantOffset;
        final public Vector2f areaIndex;
        final public Rectangle area; 

        public SubArea(final Vector2f areaIndex, final Rectangle area) {
            this.area = area;
            this.areaIndex = areaIndex;
            this.constantOffset = new Vector2f();
            constantOffset.zero();
            this.constantOffset.x = area.width / (float) PollingArea.this.sideTiles;
            this.constantOffset.y = area.height / (float) PollingArea.this.sideTiles;
            coordsOffset = Vector2f.ZERO;
        }

        public Vector2f assingOffset() {
            float centerDistanceX = (sideTiles / 2) - areaIndex.x;
            float centerDistanceY = (sideTiles / 2) - areaIndex.y;

            this.coordsOffset.x += (centerDistanceX) * constantOffset.x;
            this.coordsOffset.y += (centerDistanceY) * constantOffset.y;

            this.coordsOffset.x *= (this.areaIndex.x < sideTiles / 2) ? -1 : 1;
            this.coordsOffset.y *= (this.areaIndex.y < sideTiles / 2) ? -1 : 1;
            
            return coordsOffset;
        }

    }

    public Vector3f getRandomOffset() {
        final int cellNumber = rand.nextInt(totalCells);

        int row = cellNumber / sideTiles;
        int column = (cellNumber) % sideTiles;
        
        System.out.println(row + " -- " + column);

        Vector2f polled = sec.get(row).get(column).assingOffset();
        return new Vector3f(polled.x, 0, polled.y);
    }
    
    private void initSectors(){
        for (int i = 0; i < sideTiles; i++) {
            sec.add(new ArrayList<>());
            for (int t = 0; t < sideTiles; t++) {
                sec.get(i).add(new SubArea(new Vector2f(i, t), area));
            }
        }
    }

}
