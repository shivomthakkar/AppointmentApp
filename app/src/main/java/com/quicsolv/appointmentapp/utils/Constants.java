package com.quicsolv.appointmentapp.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  18 Apr 2018
 ***********************************************************************/

public class Constants {

    public static boolean isLogout = false;
    public static boolean isfromSplash = false;

    public static final String MPARK_PREFS = "MparkPrefs";
    public static final String PREFS_PARKING_NAME = "PrefsParkingName";

    public static final String USER_TYPE_PATIENT = "p";

    //Error code
    public static final int ERROR_CODE_200 = 200;
    public static final int ERROR_CODE_400 = 400;
    public static final int ERROR_CODE_401 = 401;


    public static void DisplayMessageDialog(Activity activity, String title, String message) {
        if (activity != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
            builder1.setMessage(message);
            builder1.setCancelable(true);
            builder1.setTitle(title);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

}
