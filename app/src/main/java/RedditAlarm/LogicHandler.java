package RedditAlarm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicHandler {
    UIClass ui;
    List<Alarm> alarmList = Collections.synchronizedList(new ArrayList<Alarm>());

    public LogicHandler(UIClass uiReference) {
        this.ui = uiReference;
        String query = "select sqlite_version() AS sqlite_version";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("redditAlarmDB", null);
        Cursor cursor = db.rawQuery(query, null);
        String sqliteVersion = "";
        if (cursor.moveToNext()) {
            sqliteVersion = cursor.getString(0);
        }
    }

    //public boolean addAlarm() {
    //    Alarm newAlarm =  new Alarm();
    //}

}
