package pumkid.com.psheet.Diagram.gcoordinate;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import pumkid.com.psheet.common.Debug;
import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/8.
 */

public class ICoordinateView extends View {
    protected Context mContext;
    protected Point2D zeroPoint;
    protected Point2D zeroPointValue;
    protected boolean yp = false;
    protected boolean yn = false;
    protected boolean xp = false;
    protected boolean xn = false;
    protected float axial_scale = 0;
    protected int[] axialY;
    protected int[] axialX;
    protected int maxY = 0, minY = 0;
    protected int maxX = 0, minX = 0;

    protected int llX = 0;
    protected int llY = 0;
    protected int ulX = 0;
    protected int ulY = 0;

    public ICoordinateView(Context context) {
        super(context);
        init(context);
    }
    public ICoordinateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public ICoordinateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    protected void init(Context context)
    {
        mContext = context;

    }

    protected void init(Context context , AttributeSet attrs)
    {
        init(context);
    }

    public Point2D getZero()
    {
        return zeroPoint;
    }

    public void setAxials(int[] axialx, int[] axialy)
    {
        axialX = axialx;
        axialY = axialy;
        computeZeroValue();
    }

    public int[] getRangeValue()
    {
        return new int[]{maxX,maxY,minX,minY};
    }

    public int[] getRangePosition()
    {
        return new int[]{ulX,ulY,llX,llY};
    }
    public float getAxial_scale()
    {
        return axial_scale;
    }

    private void computeZeroValue()
    {
        for(int i = 0; axialY != null && i < axialY.length ; i++)
        {
            if(i == 0)
                maxY = minY = axialY[i];

            if(axialY[i] > maxY)
                maxY = axialY[i];

            if(axialY[i] < minY)
                minY = axialY[i];
        }

        for(int i = 0; axialX != null && i < axialX.length ; i++)
        {
            if(i == 0)
                maxX = minX = axialX[i];

            if(axialX[i] > maxX)
                maxX = axialX[i];

            if(axialX[i] < minX)
                minX = axialX[i];
        }

        if(maxY > 0)
            yp = true;
        if(minY < 0)
            yn = true;

        if(maxX > 0)
            xp = true;
        if(minX < 0)
            xn = true;

        if(zeroPointValue == null)
            zeroPointValue = new Point2D((maxX-minX)/2, (maxY-minY)/2);


    }


    public void reset()
    {
        axialX = null;
        axialY = null;
        yp = yn = xn = xp = false;
    }
}
