package com.learnacad.cashgo.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.learnacad.cashgo.Models.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sahil Malhotra on 14-10-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationHandler notificationHandler;
    private String TAG = "firebaseNotification";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage == null){
            return;
        }

        Log.d("firebaseNotification","From: - "+remoteMessage.getFrom());

        if(remoteMessage.getNotification() != null){
            Log.d("firebaseNotification","Notification body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String body) {

        if(!NotificationHandler.isAppInBackground(getApplicationContext())){
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message",body);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e("firebaseNotification", "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e("firebaseNotification", "title: " + title);
            Log.e("firebaseNotification", "message: " + message);
            Log.e("firebaseNotification", "isBackground: " + isBackground);
            Log.e("firebaseNotification", "payload: " + payload.toString());
            Log.e("firebaseNotification", "imageUrl: " + imageUrl);
            Log.e("firebaseNotification", "timestamp: " + timestamp);


            if (!NotificationHandler.isAppInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e("firebaseNotification", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("firebaseNotification", "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationHandler = new NotificationHandler(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationHandler.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationHandler = new NotificationHandler(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationHandler.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
