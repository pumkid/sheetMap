package pumkid.com.psheet.Diagram.arrows;

import android.graphics.Rect;

import pumkid.com.psheet.common.Point2D;

/**
 * Created by pumkid on 2017/8/5.
 */

public class ArrowsUtil {

    public enum STYLE{
        COOR_ARR_X ,
        COOR_ARR_Y ,
        CUR_ARR,
    };

    public static ArrowsDrawer makeArrowsDrawer(STYLE style, Point2D sharpLocattion){
        Rect rect = null;
        double rota = 0.0;
        switch (style)
        {
            case COOR_ARR_X:
                rect = new Rect(0,0,60,60);
                rota =  - Math.PI / 2;
                return ArrowsDrawer.makeArrows(rect ,rota  , sharpLocattion , 2, 2 ,2);

            case COOR_ARR_Y:
                rect = new Rect(0,0,60,60);
                rota = Math.PI;
                return ArrowsDrawer.makeArrows(rect ,rota  , sharpLocattion , 2, 2 , 2);

            case  CUR_ARR:
                return null;

        }
        return null;
    }
}
