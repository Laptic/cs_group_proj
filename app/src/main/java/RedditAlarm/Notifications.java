package RedditAlarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.content.Context;

// NOTE: This class uses Notification.Builder, not Notification
public class Notifications {

    private static NotificationCompat.Builder notiB;
    private static final int uniqueId = 6378291;

    public static void newNotification(Context context, Alarm alarm) {

        
        notiB = new NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID);
        notiB.setAutoCancel(true)
                .setSmallIcon(R.drawable.plus_button)
                //.setLargeIcon() // put bitmap here
                .setTicker("Alert from Dash Alarm")
                .setContentTitle("Good Morning!")
                .setContentText("Have a nice Day!")
                .setVisibility(View.SCROLLBAR_POSITION_DEFAULT)
                .setWhen(System.currentTimeMillis());

        /*if (alarm.url.equals("")) {
            // empty url
            notiB.setContentText("Have a nice Day!");
        } else {
            // url present
            notiB.setContentText("Here is your tailored news!");
        }*/

        Intent intent = new Intent(context, UIClass.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notiB.setContentIntent(pendingIntent);

        NotificationManager notimanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notimanager.notify(uniqueId, notiB.build());

    }

}
