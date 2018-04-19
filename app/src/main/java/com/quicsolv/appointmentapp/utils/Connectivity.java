package com.quicsolv.appointmentapp.utils;

/**
 * Created by Tushar
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;


/************************************************************************************************************************
 *
 * Check Internet Connection
 *
 ************************************************************************************************************************/

public class Connectivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) { // connected to the internet
            NetworkInfo.DetailedState detailedState = activeNetwork.getDetailedState();
            if (detailedState != NetworkInfo.DetailedState.VERIFYING_POOR_LINK) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    return true;
                }
            } else {
                return false;
            }
        } else {
            // not connected to the internet
            return false;
        }
        return false;
    }


}
