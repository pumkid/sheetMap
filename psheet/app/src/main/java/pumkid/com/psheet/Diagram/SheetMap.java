package pumkid.com.psheet.Diagram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import pumkid.com.psheet.Diagram.gcoordinate.ICoordinateView;
import pumkid.com.psheet.Diagram.gcoordinate.ICurveView;
import pumkid.com.psheet.common.CommonUtil;
import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/8.
 */

public class SheetMap extends ViewGroup{
    private ICoordinateView coordinateView = null;
    private List<ICurveView> curveViews;
    private Context mContext;

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int viewsWidth;//所有子View的宽度之和（在该例子中仅代表宽度最大的那个子View的宽度）
    private int viewsHeight;//所有子View的高度之和
    private int viewGroupWidth = 0;//ViewGroup算上padding之后的宽度
    private int viewGroupHeight = 0;//ViewGroup算上padding之后的高度

    private int[] axialY;
    private int[] axialX;

    public void setAxials(int[] caxialx, int[] caxialy)
    {
        axialX = caxialx;
        axialY = caxialy;

        if(coordinateView != null)
            coordinateView.setAxials(axialX,axialY);
    }

    public SheetMap(Context context) {
        super(context);
        init(context);
    }

    public SheetMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SheetMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        this.mContext = context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int viewCount = getChildCount();
        for(int i = 0 ; i < viewCount; i++)
        {
            if(getChildAt(i) instanceof ICoordinateView)
            {
                coordinateView = (ICoordinateView) getChildAt(i);
                coordinateView.setAxials(axialX,axialY);
            }else if(getChildAt(i) instanceof ICurveView )
            {
                addCurve((ICurveView)getChildAt(i));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed)
        for(int i = 0 ; i < getChildCount() ; i ++)
        {
            getChildAt(i).layout(l,t,r,b);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewsWidth = 0;
        viewsHeight = 0;
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            viewsHeight += childView.getMeasuredHeight();
            viewsWidth = Math.max(viewsWidth, childView.getMeasuredWidth());
        }
        /* 用于处理ViewGroup的wrap_content情况 */
        viewGroupWidth = paddingLeft + viewsWidth + paddingRight;
        viewGroupHeight = paddingTop + viewsHeight + paddingBottom;
        setMeasuredDimension(CommonUtil.measureWidth(widthMeasureSpec, viewGroupWidth), CommonUtil.measureHeight
                (heightMeasureSpec, viewGroupHeight));
    }

    public void addCurve(ICurveView view)
    {
        if(curveViews == null)
            curveViews = new LinkedList<>();

        curveViews.add(view);
    }

    public void attachCurve(LinkedList<Point2D> point2Ds, int color)
    {
        ICurveView curveView = new ICurveView(mContext);
        curveView.setList(point2Ds);
        curveView.setColor(color);

        if(coordinateView != null)
            curveView.bind(coordinateView);
        this.addView(curveView);
        addCurve(curveView);
    }

}
