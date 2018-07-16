package com.appentus.materialkingseller.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.appentus.materialkingseller.R;
import com.appentus.materialkingseller.Util.TagUtils;
import com.appentus.materialkingseller.views.activity.SignupHandler;
import com.appentus.materialkingseller.views.activity.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            String notification = remoteMessage.getData().toString();
            String success = remoteMessage.getData().get("success");
            String result = remoteMessage.getData().get("result");
            String title = remoteMessage.getData().get("title");
            String description = remoteMessage.getData().get("description");
            String type = remoteMessage.getData().get("type");

            Log.d(TagUtils.getTag(), "notification:-" + notification);
            Log.d(TagUtils.getTag(), "success:-" + success);
            Log.d(TagUtils.getTag(), "result:-" + result);
            Log.d(TagUtils.getTag(), "type:-" + type);

            checkType(type, result);
        } catch (Exception e) {
            Log.d(TagUtils.getTag(), e.toString());
            try {
                Log.d(TagUtils.getTag(), "From: " + remoteMessage.getFrom());
                Log.d(TagUtils.getTag(), "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TagUtils.getTag(), e1.toString());
            }
        }
    }


    public void checkType(String type, String result) {
        try {
            Log.d(TagUtils.getTag(), "type:-" + type);
            sendBidConfirmNotification(type, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendBidConfirmNotification(String type,String data){
        try {
            JSONObject jsonObject = new JSONObject(data);

            Log.d(TagUtils.getTag(),"notification message:-"+jsonObject.optString("msg"));
            Log.d(TagUtils.getTag(),"notification type:-"+type);

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("type", type);
            intent.putExtra("data", data);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(type)
                    .setContentText(jsonObject.optString("msg"));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify(notificationId, mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}