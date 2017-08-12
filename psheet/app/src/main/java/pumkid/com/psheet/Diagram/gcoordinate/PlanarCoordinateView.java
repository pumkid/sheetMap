package pumkid.com.psheet.Diagram.gcoordinate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import pumkid.com.psheet.Diagram.arrows.ArrowsDrawer;
import pumkid.com.psheet.Diagram.arrows.ArrowsUtil;
import pumkid.com.psheet.R;
import pumkid.com.psheet.common.CommonUtil;
import pumkid.com.psheet.common.Debug;
import pumkid.com.psheet.common.PaintTools;
import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/9.
 */

public class PlanarCoordinateView extends ICoordinateView {
    final int START_X = 0;
    final int START_Y = 1;
    final int END_X = 2;
    final int END_Y = 3;

    private boolean ratio = false;
    private float fingerHight = 2;
    private int scales = 10;
    private int mWidth = 0;
    private int mHeight = 0;
    private int llX = 0;
    private int llY = 0;
    private int ulX = 0;
    private int ulY = 0;

    float scaleP[] = {0,0,0,0};
    float axial_scale = 0;
    private PaintTools paintTools;
    int[] mValues ;
    private float padding_surround = 0;

    private float scaleY = 0;
    private float scaleX = 0;

    private boolean drawDashGird = true;


    public PlanarCoordinateView(Context context) {
        super(context);
        init();
    }

    public PlanarCoordinateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttributeSet(attrs);
    }

    public PlanarCoordinateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void initAttributeSet(AttributeSet attrs)
    {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.coor);
        padding_surround = ta.getDimension(R.styleable.coor_padding_surround, 0);
        axial_scale = ta.getFloat(R.styleable.coor_axial_scale, 0f);
        fingerHight = ta.getDimension(R.styleable.coor_axial_finger,2);
        ta.recycle();
    }

    private void init()
    {
        paintTools = new PaintTools();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = CommonUtil.measureWidth(widthMeasureSpec,widthMeasureSpec);
        mHeight = CommonUtil.measureHeight(heightMeasureSpec,heightMeasureSpec);
        Debug.d("onMeasure mWidth = "+mWidth + "  mHeight = "+mHeight); setMeasuredDimension(mWidth,mHeight);

        computeZeroPoint();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void computeZeroPoint()
    {
        int xEffective = maxX - minX;
        int yEffective = maxY - minY;

        float widthEffective = mWidth - 2 * padding_surround;
        float heightEffective = mHeight - 2 * padding_surround;

        llX = (int)padding_surround;
        llY = (int)(mHeight - padding_surround);
        ulX = (int)(mWidth - padding_surround);
        ulY = (int)padding_surround;


        if(!ratio)
        {
            scaleY = heightEffective / yEffective;
            scaleX = widthEffective / xEffective;
        }else {
            float maxEffective = heightEffective>widthEffective ? heightEffective : widthEffective;
            scaleY = maxEffective / yEffective;
            scaleX = maxEffective / xEffective;
        }

        //only positive
        float zeroX = llX;
        float zeroY = llY;

        //positive and negative
        if(xn && xp)
            zeroX = (0 - minX) * scaleX;
        //only negative
        else if(xn && !xp)
            zeroX = ulX;

        if(yn && yp)
            zeroY = heightEffective - (0 - minY) * scaleY ;
        else if(yn && !yp)
            zeroY = ulY;

         mValues = new int[]{maxY,maxX,minX,minY};

        zeroPoint = new Point2D(zeroX, zeroY);
        Debug.d("zx =" +zeroX +" zy ="+zeroY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Debug.d("onDraw");
        drawsAxials(canvas);
        drawsCells(canvas);
        drawArrow(canvas);


    }

    private void drawArrow(Canvas canvas)
    {
        ArrowsDrawer arrowX = ArrowsUtil.makeArrowsDrawer(ArrowsUtil.STYLE.COOR_ARR_X, new Point2D(ulX, zeroPoint.y ));
        ArrowsDrawer arrowY = ArrowsUtil.makeArrowsDrawer(ArrowsUtil.STYLE.COOR_ARR_Y, new Point2D(zeroPoint.x, ulY ));
        arrowX.draw(canvas,paintTools.gaintPaint(PaintTools.PAINT_ARR,2));
        arrowY.draw(canvas,paintTools.gaintPaint(PaintTools.PAINT_ARR,2));
    }

    private void drawsCells(Canvas canvas)
    {
        Paint pText = paintTools.gaintPaint(PaintTools.PAINT_TEXT,25);

        for(int i = 0 ; i < axialY.length ; i++)
        {
            float[] plScale =  getLine(axialY, i, zeroPoint.x, llY);
            if(plScale == null) continue;

            //draw  x finger
            canvas.drawLine(plScale[START_X], plScale[START_Y], plScale[END_X],plScale[END_Y],paintTools.gaintPaint(PaintTools.PAINT_SCALE,2));
            if(drawDashGird)
                drawDashLine(canvas,llX ,plScale[START_Y],ulX ,plScale[END_Y]);
            int text_width = (int) pText.measureText(""+axialY[i]);

        //    if(i != 0)
            canvas.drawText(""+axialY[i],plScale[START_X]+fingerHight+1, plScale[START_Y] + pText.getTextSize()/2,pText);
            Debug.d( "scale index = "+i+" plScale[START_X]:"
              +plScale[START_X]+" plScale[START_Y]:"+plScale[START_Y]
              +" plScale[END_X]:"+ plScale[END_X] + " plScale[END_Y]:"+ plScale[END_Y]);
        }

        for(int i = 0 ; i < axialX.length ; i++)
        {
            float[] plScale =  getLine(axialX, i, llX, zeroPoint.y);
            if(plScale == null) continue;

            if(axialX[i] != 0) //ignore zero point finger
            //draw  x finger
            canvas.drawLine(plScale[START_X], plScale[START_Y],plScale[END_X],plScale[END_Y],paintTools.gaintPaint(PaintTools.PAINT_SCALE,2));

            int text_width = (int) pText.measureText(""+axialX[i]);
            if(drawDashGird)
                drawDashLine(canvas,plScale[START_X],llY,plScale[END_X], ulY);
            float startx = plScale[START_X] - text_width/2;
            //the zero stay a little right from axial y
            if(axialX[i] == 0)
                startx = plScale[START_X] - text_width - 2;

            canvas.drawText(""+axialX[i],startx, plScale[END_Y] + pText.getTextSize(),pText);

        }

    }

    private void drawDashLine(Canvas canvas)
    {

    }

    public void drawDashLine(Canvas canvas,float startx ,float starty, float endx ,float endy){
        float totalWidth = 0;
        canvas.save();
        Path p = new Path();
        p.moveTo(startx,starty);
        p.lineTo(endx,endy);
        canvas.drawPath(p, paintTools.gaintPaint(PaintTools.PAINT_DASH,2));
        canvas.restore();
    }


    private float[] getLine(int[] arr, int index , float startX, float startY) {
        float base = startX;

        if(arr.length <= index)
            return scaleP;

        int curValue = arr[index];
        float relativeBase;
        float  vector = 1;

        //Scale and set vector
        if(curValue < 0)
            vector = -1;
        else
            vector = 1;

        vector = vector * axial_scale;

        float curTap = 0;
        int len;

        if (arr == axialY)
        {
            relativeBase = zeroPoint.y;

            curTap = Math.abs(curValue)/(float)(maxY - minY);

            len = ulY - llY;
            scaleP[START_X] = (int)startX;
            scaleP[START_Y]  = (int)((len * curTap * vector  + relativeBase));
            scaleP[END_X] = startX + fingerHight;
            scaleP[END_Y] = (int)((len * curTap * vector  + relativeBase));
            Debug.d("scaleP[START_Y]:  "+scaleP[START_Y]+" llY: "+llY );
            if(scaleP[START_Y] > llY)
                return null;

        }else if (arr == axialX)
        {
            relativeBase = zeroPoint.x;
            curTap = Math.abs(curValue)/(float)(maxX - minX);

            len = ulX - llX;

            scaleP[START_X] = (int)((len * curTap  * vector + relativeBase) );
            scaleP[START_Y]  = startY - fingerHight;
            scaleP[END_X] = (int)((len * curTap  * vector+ relativeBase) );
            scaleP[END_Y] = startY;
            if(scaleP[START_X] < llX)
                return null;
        }


        return scaleP;

    }


    private void drawsAxials(Canvas canvas)
    {
        //draw x
        canvas.drawLine(llX,zeroPoint.y , ulX, zeroPoint.y, paintTools.gaintPaint(PaintTools.PAINT_COR,2));

        //draw y
        canvas.drawLine(zeroPoint.x ,llY , zeroPoint.x, ulY,paintTools.gaintPaint(PaintTools.PAINT_COR,2));
    }

    private void drawsＧrid(Canvas canvas)
    {

    }

    private void drawsAxialArrows(Canvas canvas)
    {

    }

}
