package RedditAlarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

import RedditAlarm.Models.RedditJSON;
import RedditAlarm.Models.RedditPost;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.ALARM_SERVICE;

public class LogicHandler
        extends BroadcastReceiver            //problem here
        implements RedditCall.AsyncResponse, AlarmFragment.logicHandler {
    private UIClass ui;
    public String BASE_URL = "https://www.reddit.com/r/";
    public int NUM_POSTS = 3;
    private DatabaseHandler database;
    List<Alarm> alarmList;


    public LogicHandler() {
    }


    // run upon trigger by AlarmManager
    @Override
    public void onReceive(Context context,
                          Intent intent) {
        Calendar time = Calendar.getInstance();

        // alarm is triggered at time set, so compares trigger times with current time
        int hourTemp = time.get(Calendar.HOUR_OF_DAY);
        int minuteTemp = time.get(Calendar.MINUTE);
        // needs to load database and search for alarm that is executed
        database = new DatabaseHandler(context);
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
        processFinish(null, context);
        if (executeAlarm != null) {
            Retrofit retrofitCall = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RedditClient apiService =
                    retrofitCall.create(RedditClient.class);
            String subreddit = executeAlarm.url;
            Call<RedditJSON> retroCall = apiService.getRedditPosts(subreddit, NUM_POSTS);
            RedditCall redditCall = new RedditCall();
            redditCall.delegate = this;
            redditCall.contextIn = context;
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

    @Override
    public void addAlarm(Alarm alarmIn) {
        database.addAlarm(alarmIn);
        alarmList = database.getAllAlarm();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if ((Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= alarmIn.hour) &&
                (Calendar.getInstance().get(Calendar.MINUTE) >= alarmIn.minute)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
        }

        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // gets alarm manager instance from system
        AlarmManager alarmMan =
                (AlarmManager) ui
                        .getApplicationContext()
                        .getSystemService(ALARM_SERVICE);
        // says which class to use when alarms triggered and passes context
        Intent intent = new Intent(ui, LogicHandler.class);
        PendingIntent pendIntent =
                PendingIntent.getBroadcast(
                        ui.getApplicationContext(), alarmIn.id, intent,0);
        /* sets alarm to repeat every day at set time,
            need to update for cases where it doesn't repeat
        */

        alarmMan.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendIntent);
    }

    public void deleteAlarm(Alarm alarmIn) {
        database.deleteAlarm(alarmIn);
        Intent intent = new Intent(ui, LogicHandler.class);
        PendingIntent sender =
                PendingIntent.getBroadcast(ui.getApplicationContext(),
                        alarmIn.id,
                        intent,
                        0);
        AlarmManager alarmManager = (AlarmManager) ui
                .getApplicationContext()
                .getSystemService(ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void editAlarm(Alarm alarmIn) {
        database.updateAlarm(alarmIn);
    }

    public void processFinish(List<RedditPost> output, Context conIn) {
        Notifications noti = new Notifications();
        noti.newNotification(conIn, new Alarm(), output); // will need to put something into the alarm or make another parameter
                                                  // for newNotification -Ryan
    }
}
