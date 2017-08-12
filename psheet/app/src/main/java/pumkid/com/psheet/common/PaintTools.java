package pumkid.com.psheet.common;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;

/**
 * Created by luna on 17/8/8.
 */

public class PaintTools {
    public static final int PAINT_COR = 0;
    public static final int PAINT_SCALE = 1;
    public static final int PAINT_ARR = 2;
    public static final int PAINT_TEXT = 3;
    public static final int PAINT_DASH = 4;


    public static final int PAINT_BUTT = 5;

    private Paint[] paints = new Paint[PAINT_BUTT];


    public Paint gaintPaint(final int pstyle, float size)
    {
        switch (pstyle)
        {
            case PAINT_COR:
                return gaintPaintCOR(size);
            case PAINT_SCALE:
                return gaintPaintSCALE(size);
            case PAINT_ARR:
                return gaintPaintARR();
            case PAINT_TEXT:
                return gaintPaintText(size);
            case PAINT_DASH:
                return gaintPaintDash();
            default:
                return gaintPaintCOR(size);
        }

    }

    private Paint gaintPaintText(float sheet_text_size)
    {
        Paint p = paints[PAINT_TEXT];
        if(p != null) {
            p.setTextSize(sheet_text_size);
            return p;
        }
        p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.BLACK);
        p.setTextSize(sheet_text_size);
        return  p;
    }

    private Paint gaintPaintARR()
    {
        Paint p = paints[PAINT_ARR];
        if(p != null)
            return p;
        p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(1);
        return  p;
    }

    private Paint gaintPaintDash()
    {
        Paint paint = paints[PAINT_DASH];
        if(paint == null)
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(1);
        PathEffect effects = new DashPathEffect(new float[] { 20,20}, 1);
        paint.setPathEffect(effects);
        return paint;
    }


    private Paint gaintPaintCOR(float axialStroke)
    {
        Paint p =  paints[PAINT_COR];
        if(p != null)
        {
            p.setStrokeWidth(axialStroke);
            return p;
        }
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(axialStroke);
        return  p;
    }

    private Paint gaintPaintSCALE(float axialStroke)
    {
        Paint p =   paints[PAINT_SCALE];
        if(p != null){
            p.setStrokeWidth(axialStroke);
            return  p;
        }
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(axialStroke);
        return  p;
    }




}
