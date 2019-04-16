package RedditAlarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.List;

import RedditAlarm.Models.RedditPost;
import RedditAlarm.Models.RedditResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogicHandler
        extends BroadcastReceiver            //problem here
        implements RedditCall.AsyncResponse, AlarmFragment.logicHandler {
    private UIClass ui;
    public String BASE_URL = "http://www.reddit.com/r";
    public int NUM_POSTS = 3;
    private DatabaseHandler database;
    List<Alarm> alarmList;




    // run upon trigger by AlarmManager
    @Override
    public void onReceive(Context context,
                          Intent intent) {
        Calendar time = Calendar.getInstance();
        // alarm is triggered at time set, so compares trigger times with current time
        int hourTemp = time.get(Calendar.HOUR_OF_DAY);
        int minuteTemp = time.get(Calendar.MINUTE);
        // needs to load database and search for alarm that is executed
        database = new DatabaseHandler(ui);
        /* can't immediately think of a better way of doing this,
        objects can't be used in alarm manager, perhaps storing id of next
        one to execute in preferences? -bchesnut*/
        alarmList = database.getAllAlarm();
        Alarm executeAlarm = null;
        for (int i=0; i < alarmList.size(); i++ ) {
            Alarm temp = alarmList.get(i);
            if ((temp.hour == hourTemp) && (temp.minute == minuteTemp)) {
                executeAlarm = temp;
                break;
            }
        }
        if (executeAlarm != null) {
            Retrofit retrofitCall = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RedditClient apiService =
                    retrofitCall.create(RedditClient.class);
            String subreddit = executeAlarm.url;
            Call<RedditResult> retroCall = apiService.getRedditPosts(NUM_POSTS, subreddit);
            RedditCall redditCall = new RedditCall();
            redditCall.execute(retroCall);
        }
    }

    public LogicHandler(UIClass uiReference) {
        this.ui = uiReference;
        /* builds database from UI's context, populates a list of alarms with
            alarms created from database entries */
        database = new DatabaseHandler(ui);
        alarmList = database.getAllAlarm();
        System.out.println(alarmList.toString());
    }

    public void addAlarm(Alarm alarmIn) {
        database.addAlarm(alarmIn);
        alarmList = database.getAllAlarm();
        // gets alarm manager instance from system
        AlarmManager alarmMan = (AlarmManager) ui.getSystemService(Context.ALARM_SERVICE);
        // says which class to use when alarms triggered and passes context
        Intent intent = new Intent(ui, LogicHandler.class);
        PendingIntent pendIntent =
                PendingIntent.getBroadcast(
                        ui,0, intent,0);
        /* sets alarm to repeat every day at set time,
            need to update for cases where it doesn't repeat
        */
        alarmMan.setRepeating(AlarmManager.RTC,
                alarmIn.getMiliTime(),
                AlarmManager.INTERVAL_DAY,
                pendIntent);

    }

    public void processFinish(List<RedditPost> output, Context conIn) {
        Notifications newNotification = new Notifications();
        newNotification.newNotification(conIn);
    }
}