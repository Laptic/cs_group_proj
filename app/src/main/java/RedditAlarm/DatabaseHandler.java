package RedditAlarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RedditAlarmDB";
    private static final String TABLE_ALARMS = "alarms";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "time";
    private static final String KEY_DAYS = "days";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_ALARMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TIMESTAMP + " TEXT," + KEY_DAYS + " TEXT, " + KEY_URL
                + " TEXT" + ")";
        db.execSQL(CREATE_ALARMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);

        // Create tables againn
        onCreate(db);
    }

    void addAlarm(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmIn.name);
        values.put(KEY_TIMESTAMP, alarmIn.getTimeStamp());
        values.put(KEY_DAYS, alarmIn.strRepDays());
        values.put(KEY_URL, alarmIn.url);


        // Inserting Row
        db.insert(TABLE_ALARMS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all contacts in a list view
    public List<Alarm> getAllAlarm() {
        List<Alarm> alarmList = new ArrayList<Alarm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarm tempAlarm = new Alarm();
                tempAlarm.setID(Integer.parseInt(cursor.getString(0)));
                tempAlarm.setName(cursor.getString(1));
                tempAlarm.setTime(cursor.getString(2));
                tempAlarm.setURL(cursor.getString(3));
                tempAlarm.setDays(cursor.getString(4));
                // Adding contact to list
                alarmList.add(tempAlarm);
            } while (cursor.moveToNext());
        }

        // return contact list
        return alarmList;
    }

    // code to update the single contact
    public int updateAlarm(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmIn.getName());
        values.put(KEY_TIMESTAMP, alarmIn.getTimeStamp());
        values.put(KEY_DAYS, alarmIn.strRepDays());
        values.put(KEY_URL, alarmIn.url);

        // updating row
        return db.update(TABLE_ALARMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmIn.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmIn.getID()) });
        db.close();
    }
}
