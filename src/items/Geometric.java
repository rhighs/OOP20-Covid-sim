import com.jme3.math.Rectangle

class Point2d {
    int x, y;
}

class Rect2d {
    public int x, y, xlen, ylen;
}

/* static class for geometric helper functions */
class Geometric {
    boolean inside(Point2d p, Rect2d rect) {
        return p.x > rect.x && p.x < rect.x + rect.xlen &&
               p.y > rect.y && p.y < rect.y + rect.ylen;
    }
}
