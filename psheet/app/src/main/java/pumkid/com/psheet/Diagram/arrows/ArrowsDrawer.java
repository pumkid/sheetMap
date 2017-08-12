package pumkid.com.psheet.Diagram.arrows;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;

import pumkid.com.psheet.common.Point2D;

/**
 * Created by pumkid on 2017/8/5.
 */

public class ArrowsDrawer {
    private final boolean ENABLE_DUBUG = false;
    private final int P_SHARP = 0;
    private final int P_LEFT = 1;
    private final int P_BOTTOM = 2;
    private final int P_RIGHT = 3;

    private static final int DEFALUT_SPAN = 4;
    private static final int DEFALUT_DEEP = 4;

    private final int MAX_SPAN = 5;
    private final int MAX_DEEP = 5;

    private final int SCALE = 10;


    private Rect mRect;
    private Point2D location;
    private double mDegree;
    private int unit_X = 1;
    private int unit_Y = 1;

    private int mArrowsWidth = 0;

    private int mSpan = DEFALUT_SPAN;
    private int mDeep = DEFALUT_DEEP;

    private Point2D centerPoint;
    private Point2D sharpPoint;

    private Point2D[] shape = new Point2D[4];


    public ArrowsDrawer(Rect rect, double degree, Point2D point2D, int arrowsScale, int span, int deep) {
        location = point2D;
        int halfWith = rect.width() / 2;
        int halfHeight = rect.height() / 2;

        location.x -=halfWith;
        location.y -=halfHeight;
        mRect = rect;
        mDegree = degree;
        mArrowsWidth = arrowsScale;
        mSpan = span;
        mDeep = deep;

        if(mSpan > MAX_SPAN)
            mSpan = MAX_SPAN;

        if(mDeep > MAX_DEEP)
            mDeep = MAX_DEEP;

        if (mArrowsWidth > SCALE)
            mArrowsWidth = SCALE;

        centerPoint = new Point2D(0, 0);

        unit_X = rect.width() / SCALE;
        unit_Y = rect.width() / SCALE;

        if (mArrowsWidth == 0) {
            sharpPoint = new Point2D(centerPoint.x, centerPoint.y);

        } else if (mArrowsWidth == 1) {
            sharpPoint = new Point2D(centerPoint.x, centerPoint.y + 1);
        } else if (mArrowsWidth % 2 == 0) {
            int temp = mArrowsWidth / 2;
            sharpPoint = new Point2D(centerPoint.x, centerPoint.y + temp);
        } else {
            int temp = mArrowsWidth / 2;
            sharpPoint = new Point2D(centerPoint.x, centerPoint.y + temp + 1);
        }

        shape[P_SHARP] = sharpPoint;
        shape[P_LEFT] = new Point2D(-mSpan, -mDeep);
        shape[P_BOTTOM] = new Point2D(sharpPoint.x, sharpPoint.y - mArrowsWidth);
        shape[P_RIGHT] = new Point2D(mSpan, -mDeep);


        computeShape(degree);

    }

    private void computeShape(double degree) {
        if (shape[P_SHARP] == null)
            return;

        for (int i = 0; i < shape.length; i++) {
            float tempX = shape[i].x;
            float tempY = shape[i].y;
            shape[i].x = (float)(tempX * Math.cos(degree) - tempY * Math.sin(degree));
            shape[i].y = (float) (tempX * Math.sin(degree) + tempY * Math.cos(degree));
        }
    }


    public Point2D getOrgPoint(Point2D center) {
        center.x = unit_X * (center.x + 5);
        center.y = unit_Y * (center.y + 5);
        return center;
    }

    public void draw(Canvas canvas, Paint p) {
        Path pathRange;
        Path path = new Path();
        int i = 0;

        if(ENABLE_DUBUG)
        {
            pathRange = new Path();
            pathRange.moveTo(location.x,location.y);
            pathRange.lineTo(mRect.width(),location.y);
            pathRange.lineTo(mRect.width(),mRect.height());
            pathRange.lineTo(location.x,mRect.height());
            pathRange.lineTo(location.x,location.y);
        }
        for (Point2D point : shape) {
            Point2D tempP = getOrgPoint(point);

            if (point == shape[P_SHARP]) {
                path.moveTo(location.x + tempP.x, location.y + tempP.y);
            } else
                path.lineTo(location.x + tempP.x, location.y + tempP.y);

            if(ENABLE_DUBUG) {
                Log.d("pumkid", " shape [" + (i) + "]:  x = " + tempP.x + " , y = " + tempP.y);
                Log.d("pumkid", " shape [" + (i++) + "]:  x = " + (location.x + tempP.x) + " , y = " + (location.y + tempP.y));
            }
        }

        canvas.drawPath(path, p);

        if(ENABLE_DUBUG) {
            p.setStyle(Paint.Style.STROKE);
            canvas.drawPath(pathRange, p);
        }
    }

    public static ArrowsDrawer makeArrows(Rect rect, double degree, Point2D point2D, int arrowsWidth) {
        return new ArrowsDrawer(rect, degree, point2D, arrowsWidth,DEFALUT_SPAN,DEFALUT_DEEP);
    }

    public static ArrowsDrawer makeArrows(Rect rect, double degree, Point2D point2D, int arrowsWidth, int span, int deep) {
        return new ArrowsDrawer(rect, degree, point2D, arrowsWidth, span ,deep);
    }



}
