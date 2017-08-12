package pumkid.com.psheet.common;

import android.util.Log;

/**
 * Created by luna on 17/8/8.
 */

public class Debug {
    private static String TAG = "pumkid";
    private static boolean log_enable = true;

    public static void d(String msg)
    {
        if(log_enable)
            Log.d(TAG,msg);
    }
}
