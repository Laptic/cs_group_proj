package RedditAlarm;

import android.app.NotificationChannel;
import android.content.Context;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.support.v4.app.NotificationCompat;
import java.util.List;
import RedditAlarm.Models.RedditPost;
import android.os.Vibrator;

// NOTE: This class uses Notification.Builder, not Notification
public class Notifications {

    public void newNotification(Context context, Alarm alarm, List<RedditPost> output) {

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), Notifications.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        // For testing invalid subreddit info
        //output = null;

        // start of alarm representation in notification
        String AMPM = "AM";
        if (alarm.PM) {
            AMPM = "PM";
        }

        String minute = String.valueOf(alarm.minute);

        if (minute.equals("0")) {
            minute += "0 ";
        } else {
            minute += " ";
        }
        String title = alarm.hour + ":" + minute + AMPM;
        bigText.setBigContentTitle("Alarm for: " + title);
        // end of alarm representation in notification
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(1100, VibrationEffect.DEFAULT_AMPLITUDE));

        bigText.setSummaryText("Good Morning");
        mBuilder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));


        if (output != null) {
            mBuilder.setContentTitle("Here are your three headlines for today.");
            mBuilder.setContentText("Tap here for your Headlines.");
            mBuilder.setStyle(bigText.bigText("1: " + output.get(0).getTitle() + ".\nPosted by: " + output.get(0).getAuthor() + " at " + output.get(0).getScore() +
                    " upvotes.\n2: " + output.get(1).getTitle() + ".\nPosted by: " + output.get(1).getAuthor() + " at " + output.get(1).getScore() +
                    " upvotes.\n3: " + output.get(2).getTitle() + ".\nPosted by: " + output.get(2).getAuthor() + " at " + output.get(2).getScore() + " upvotes."));
        } else {
            mBuilder.setContentText("No/Invalid subreddit provided. No Headlines today.");
            mBuilder.setStyle(bigText);
        }

        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //mBuilder.setSound(alarmSound);
        mBuilder.setContentIntent(pendingIntent)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);

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


