package pumkid.com.psheet.common;

/**
 * Created by luna on 17/8/8.
 */

public class Point2D {
    public float x;
    public float y;

    public Point2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Point2D()
    {

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Point2D)
        {
            Point2D target = (Point2D)obj;

            return target.x == this.x && target.y == this.y;
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return "(x="+x+",y="+y+")";
    }
}
