package pumkid.com.psheet.common;

import android.graphics.Color;

/**
 * Created by HP on 2017-8-14.
 */

public class DashLine {
    public static int DEFAULT_COLOR = Color.GRAY;
    public static int DEFAULT_WIDTH = 1;
    public Point2D startPoint;
    public Point2D endPoint;
    public int color = DEFAULT_COLOR;
    public int width = DEFAULT_WIDTH;

    public DashLine(Point2D startPoint, Point2D endPoint)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
