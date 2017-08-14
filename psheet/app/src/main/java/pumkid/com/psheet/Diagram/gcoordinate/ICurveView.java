package pumkid.com.psheet.Diagram.gcoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;

import pumkid.com.psheet.common.CommonUtil;
import pumkid.com.psheet.common.Debug;
import pumkid.com.psheet.common.PaintTools;
import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/8.
 */

public class ICurveView extends View{
    public final int MAX_X = 0;
    public final int MAX_Y = 1;
    public final int MIN_X = 2;
    public final int MIN_Y = 3;


    private ICoordinateView mCoordinatView;
    private Paint mPaint;

    private int[] rangeValue;
    private int[] rangePosition;
    private Point2D zero;
    private float scaleX;
    private float scaleY;
    private float axial_scale;

    private LinkedList<Point2D> pList;

    protected Context mContext;

    public ICurveView(Context context) {
        super(context);
        init(context);
    }
    public ICurveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }
    public ICurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void bind(ICoordinateView coordinateView)
    {
        mCoordinatView = coordinateView;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         int mWidth = CommonUtil.measureWidth(widthMeasureSpec,widthMeasureSpec);
        int mHeight = CommonUtil.measureHeight(heightMeasureSpec,heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void setList(LinkedList<Point2D> list)
    {
        pList = list;
    }

    public void setColor(int color)
    {
        if(mPaint != null)
            mPaint.setColor(color);
    }

    /**
     * invoke in onDraw
     */
    private void getRange()
    {
        assert mCoordinatView != null;

        rangeValue = mCoordinatView.getRangeValue();
        rangePosition = mCoordinatView.getRangePosition();
        zero = mCoordinatView.getZero();
        axial_scale = mCoordinatView.getAxial_scale();


        scaleX = axial_scale * (rangePosition[MAX_X] - rangePosition[MIN_X])/(rangeValue[MAX_X] - rangeValue[MIN_X]);
        scaleY = axial_scale * (rangePosition[MAX_Y] - rangePosition[MIN_Y])/(rangeValue[MAX_Y] - rangeValue[MIN_Y]);

        Debug.d("axial_scale: "+ axial_scale+" scaleX "+scaleX+" scaleY "+scaleY);

    }

    private Point2D computPoint(Point2D source)
    {
        float fromX = source.x;
        float fromY = source.y;

        Point2D p = new Point2D(
                zero.x + (fromX * scaleX),
                zero.y + (fromY * scaleY)
        );

        return p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
   //     super.onDraw(canvas);
        getRange();
        Debug.d("onDraw ---------------------------------------");
        Path path = new Path();
        for(int i =0; i < pList.size() ; i++)
        {
            Point2D pFixed = computPoint(pList.get(i));
            if(i == 0)
                path.moveTo(pFixed.x,pFixed.y);
            else
                path.lineTo(pFixed.x,pFixed.y);
        }
        canvas.drawPath(path, mPaint);
    }

    protected void init(Context context)
    {
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
    }

    protected void init(Context context , AttributeSet attrs)
    {
        init(context);

    }






}
