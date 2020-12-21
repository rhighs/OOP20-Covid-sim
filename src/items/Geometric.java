package items;

import items.Point2d;
import items.Rect2d;
// import com.jme3.math.Rectangle;

/* static class for geometric helper functions */
public class Geometric {
    public static boolean inside(Point2d p, Rect2d rect) {
        return p.x > rect.x && p.x < rect.x + rect.xlen &&
               p.y > rect.y && p.y < rect.y + rect.ylen;
    }
}
