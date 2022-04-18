package sa.med.imc.myimc.Notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.SplashActivity;


/**
 * Firebase service to generate token and get notifications
 */

public class MyFirebaseMessagningService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagningService.class.getSimpleName();
    List<ActivityManager.RunningTaskInfo> taskInfo;
    ActivityManager am;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Data: " + remoteMessage.getData());

        //sendNotification(remoteMessage.getData().toString(), new Intent(), "IMC");
        JSONObject jsonObject = new JSONObject(remoteMessage.getData());
        String maindata;
        try {
            maindata = jsonObject.getString("message");
            String title = jsonObject.getString("title");
            String type = jsonObject.getString("type");

            if (type.equalsIgnoreCase("SE")) {//&& taskInfo.get(0).topActivity.getClassName().equalsIgnoreCase("MainActivity")) {
                try {
                    Intent refresh = new Intent(Constants.Filter.REFRESH_MAIN);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(refresh);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i1 = new Intent(this, SplashActivity.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendNotification(maindata, i1, title);

            } else if (type.equalsIgnoreCase("L") || type.equalsIgnoreCase("M")) {
                SharedPreferencesUtils.getInstance(this).setValue(Constants.KEY_NOTIFICATION_UNREAD, "1");
//                Intent i1 = new Intent(this, MainActivity.class);
//                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                i1.putExtra(Constants.TYPE.RECORD_LAB, "dsfdsfsd");
                sendNotification(maindata, new Intent(), title);

            } else if (type.equalsIgnoreCase("B")) {
                SharedPreferencesUtils.getInstance(this).setValue(Constants.KEY_NOTIFICATION_UNREAD, "1");
//                Intent i1 = new Intent(this, ManageBookingsActivity.class);
//                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendNotification(maindata, new Intent(), title);

            }
            else if (type.equalsIgnoreCase("S"))
            {
                try {
                    Intent chat = new Intent(Constants.Filter.CHAT_NOTIFICATION);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(chat);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                SharedPreferencesUtils.getInstance(this).setValue(Constants.KEY_NOTIFICATION_UNREAD, "1");
                sendNotification(maindata, new Intent(), title);
            }

            try {
                Intent refresh = new Intent(Constants.Filter.REFRESH_MAIN_NOTIFICATION);
                LocalBroadcastManager.getInstance(this).sendBroadcast(refresh);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SharedPreferencesUtils.getInstance(this).setValue(Constants.KEY_DEVICE_ID, s);
//        Log.e(TAG, "Token : " + s);
//        sendNotification("I am testing small icon", new Intent(), "Hello");
    }


    private void sendNotification(String message, Intent intent, String title) {

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.imc_logo);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationChannel mChannel = null;

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.imc_logo)
                .setLargeIcon(image)
                .setColor(getResources().getColor(android.R.color.white))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setChannelId(CHANNEL_ID)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.small_imc_icon);
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.small_imc_icon);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.createNotificationChannel(mChannel);

        notificationManager.notify(new Random(System.currentTimeMillis()).nextInt(), notificationBuilder.build());
    }
}
