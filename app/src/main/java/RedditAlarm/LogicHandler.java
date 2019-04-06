package RedditAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.List;

public class LogicHandler extends BroadcastReceiver {
    private UIClass ui;
    private DatabaseHandler database;
    List<Alarm> alarmList;

    @Override
    public void onReceive(Context context,
                          Intent intent) {
        // needs to load database and search for alarm that is executed
        database = new DatabaseHandler(ui);
        /* can't immediately think of a better way of doing this,
        objects can't be used in alarm manager, perhaps storing id of next
        one to execute in preferences? -bchesnut*/
        alarmList = database.getAllAlarm();
        Alarm execute = null;
        for (int i=0; i < alarmList.size(); i++ ) {
            Calendar time = Calendar.getInstance();
            int hourTemp = time.get(Calendar.HOUR_OF_DAY);
            int minuteTemp = time.get(Calendar.MINUTE);
            Alarm temp = alarmList.get(i);
            if ((temp.hour == hourTemp) && (temp.minute == minuteTemp)) {
                execute = temp;
                break;
            }
        }
        if (execute != null) {
            //#TO DO: implement calls to async tasks for Reddit and display of notifications
        }
    }

    public LogicHandler(UIClass uiReference) {
        this.ui = uiReference;
        DatabaseHandler database = new DatabaseHandler(ui);
        alarmList = database.getAllAlarm();
    }

    public void addAlarm(Alarm alarmIn) {
        database.addAlarm(alarmIn);
        alarmList = database.getAllAlarm();
        AlarmManager alarmMan = (AlarmManager) ui.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ui, LogicHandler.class);
        PendingIntent pendIntent =
                PendingIntent.getBroadcast(
                        ui,0, intent,0);
        alarmMan.setRepeating(AlarmManager.RTC,
                alarmIn.getMiliTime(),
                AlarmManager.INTERVAL_DAY,
                pendIntent);
    }

}
