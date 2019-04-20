package RedditAlarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.view.View;

import java.util.List;

import RedditAlarm.Models.RedditPost;

// NOTE: This class uses Notification.Builder, not Notification
public class Notifications {

    public void newNotification(Context context, Alarm alarm, List<RedditPost> output) {

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), Notifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        bigText.bigText("No url present.");
        bigText.setBigContentTitle("BigContentTitle for no url");
        bigText.setSummaryText("Good Morning NO URL");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Good Morning");
        mBuilder.setContentText(output.get(0).getTitle());
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "Your_channel_id";
        NotificationChannel channel = new NotificationChannel(channelId,
               "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
        mNotificationManager.createNotificationChannel(channel);   //.CreateNotificationChannel(channel);
        mBuilder.setChannelId(channelId);


        mNotificationManager.notify(0, mBuilder.build());
    }
}


