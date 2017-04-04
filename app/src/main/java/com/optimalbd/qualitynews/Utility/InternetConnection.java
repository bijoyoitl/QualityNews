package com.optimalbd.qualitynews.Utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ripon on 4/4/2017.
 */

public class InternetConnection {

    public static boolean isInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null;
    }

}
