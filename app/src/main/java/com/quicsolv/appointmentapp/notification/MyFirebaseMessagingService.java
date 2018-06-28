package com.quicsolv.appointmentapp.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.quicsolv.appointmentapp.MyApplication;
import com.quicsolv.appointmentapp.R;
import com.quicsolv.appointmentapp.activities.RequestAppointmentActivity;
import com.quicsolv.appointmentapp.utils.Prefs;

import java.util.Map;


/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  21 Dec 2017
 ***********************************************************************/

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        Prefs.setSharedPreferenceString(MyApplication.getInstance(), Prefs.PREF_NOTIFICATION_DATA, data.get("app_date"));
        Prefs.IS_FROM_NOTIFICATION = true;
        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            sendBackgroundNotification(remoteMessage.getData());
//        } else {
//            if (remoteMessage.getNotification() != null) {
//                //Call this block if app is not in background
//                sendForegroundNotification(remoteMessage.getNotification());
//
//            }
//        }
        sendBackgroundNotification(remoteMessage.getNotification(), remoteMessage.getData());
    }


    private void sendBackgroundNotification(RemoteMessage.Notification notification, Map<String, String> messageBody) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, RequestAppointmentActivity.class);
        intent.putExtra(Prefs.PREF_NOTIFICATION_DATA, messageBody.get("app_date"));
        intent.putExtra(Prefs.PREF_NOTIFICATION_DATA, messageBody.values().toArray()[1].toString());
        Prefs.IS_FROM_NOTIFICATION = true;
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher, "", pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendForegroundNotification(RemoteMessage.Notification notification) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, RequestAppointmentActivity.class);
        intent.putExtra(Prefs.PREF_NOTIFICATION_DATA, "");
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody().toString())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher, "", pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}