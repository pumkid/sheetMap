package pumkid.com.psheet.common;

import android.view.View;

import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/6.
 */

public class CommonUtil {
    public static int measureWidth(int measureSpec, int viewGroupWidth) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                /* 将剩余宽度和所有子View + padding的值进行比较，取小的作为ViewGroup的宽度 */
                result = Math.min(viewGroupWidth, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    public static int measureHeight(int measureSpec, int viewGroupHeight) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = specSize;
                break;
            case View.MeasureSpec.AT_MOST:
                /* 将剩余高度和所有子View + padding的值进行比较，取小的作为ViewGroup的高度 */
                result = Math.min(viewGroupHeight, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    public static float CoorTransformX(Point2D zero, Point2D target, int max , int maxValue, float scale)
    {
        float xf = (zero.x + target.x/maxValue * max * scale) ;
            return xf;
    }

    public static float CoorTransformY(Point2D zero, Point2D target ,int max , int maxValue, float scale)
    {

        float yf = (zero.y - target.y/maxValue * max * scale) ;
        return yf;
    }
}
