package pumkid.com.psheet.Diagram.gcoordinate;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import pumkid.com.psheet.common.Point2D;

/**
 * Created by luna on 17/8/8.
 */

public class ICurveView extends View{
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

    protected void init(Context context)
    {
        mContext = context;
    }

    protected void init(Context context , AttributeSet attrs)
    {
        init(context);
    }


}
