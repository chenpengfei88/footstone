package org.footstone.utils;

import android.content.Context;

/**
 * Created by chenpengfei on 2017/5/23.
 */
public class DisplayUtils {


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
