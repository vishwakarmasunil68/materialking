package com.appiqo.materialkingseller.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.appiqo.materialkingseller.R;
import com.appiqo.materialkingseller.views.activity.SignupHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("push","push");
        try {
            receiveData(remoteMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void receiveData(RemoteMessage remoteMessage) throws JSONException {
        int code = (int) System.currentTimeMillis();
        JSONObject object = new JSONObject(remoteMessage.getData());
        intent = new Intent(this, SignupHandler.class);
        intent.putExtra("cat_id", object.getString("cat_id"));
        intent.putExtra("cat_name", object.getString("cat_name"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, code, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(object.getString("message"))
                .setContentText(object.getString("cat_name"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(code, notificationBuilder.build());

    }


}