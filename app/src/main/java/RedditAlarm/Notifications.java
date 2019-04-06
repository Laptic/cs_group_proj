package RedditAlarm;
//import android.app.NotificationManager;
import android.app.Notification; // Maybe change to Notification.Builder
import android.app.NotificationManager;
import android.content.Context;


// NOTE: This class uses Notification.Builder, not Notification
public class Notifications {

    private static Notification noti;

    // assume we have context set up, method here will be called with the existing context passed in from LogicHandler
    public static Notification newNotification(Context context) {
        noti = new Notification()
        .setContentTitle("Notification.")
        .setContentText("This is your notification.")
        .setVisibility(Notification.VISIBILITY_PUBLIC);
        // now methods to display it, maybe include preferences in making this better
        noti.notify();
        return noti;
    }

    // if no reddit information

    // if reddit information included

}
