package id.codigo.seedroid.view.utils;

import android.content.res.Resources;

/**
 * Created by papahnakal on 14/09/17.
 */

public class Utils {
    /**
     * this function get dp based value and convert it to px
     * @param dp value based on dp metric
     * @return return value based on px metric
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
