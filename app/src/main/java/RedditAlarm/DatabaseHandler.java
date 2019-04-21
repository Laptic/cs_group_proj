package RedditAlarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // various database keys for fields
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RedditAlarmDB";
    private static final String TABLE_ALARMS = "alarms";
    private static final String KEY_ID = "id";
    private static final String KEY_TIMESTAMP = "time";
    private static final String KEY_DAYS = "days";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";
    private static final String KEY_STATUS = "status";
    private static final String KEY_AM_PM = "am_pm";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getNextKey();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_ALARMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_TIMESTAMP + " TEXT," + KEY_DAYS + " TEXT," + KEY_URL
                + " TEXT," + KEY_STATUS + " TEXT," + KEY_AM_PM + " TEXT" + ")";
        db.execSQL(CREATE_ALARMS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);



        // Create tables again
        onCreate(db);

    }

    int getNextKey() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqliteSeqTableName = "sqlite_sequence";
        /*
         * Relevant columns to retrieve from <code>sqliteSequenceTableName</code>
         */
        String[] columns = {"seq"};
        String selection = "name=?";
        String[] selectionArgs = { TABLE_ALARMS };

        Cursor cursor = db.query(sqliteSeqTableName,
                columns, selection, selectionArgs, null, null, null);

        long autoIncrement = 0;

        if (cursor.moveToFirst()) {
            int indexSeq = cursor.getColumnIndex(columns[0]);
            autoIncrement = cursor.getLong(indexSeq);
        }

        cursor.close();
        db.close();
        //return autoIncrement + 1;
        return (int) autoIncrement + 1;

    }

    void addAlarm(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmIn.name);
        values.put(KEY_TIMESTAMP, alarmIn.getTimeStamp());
        values.put(KEY_DAYS, alarmIn.strRepDays());
        values.put(KEY_URL, alarmIn.url);
        values.put(KEY_STATUS, String.valueOf(alarmIn.status));
        values.put(KEY_AM_PM, String.valueOf(alarmIn.PM));
        System.out.println(String.valueOf(alarmIn.PM));

        // Inserting Row
        db.insert(TABLE_ALARMS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all alarms in a list
    List<Alarm> getAllAlarm() {
        List<Alarm> alarmList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALARMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarm tempAlarm = new Alarm();
                tempAlarm.id = Integer.parseInt(cursor.getString(0));
                tempAlarm.name = cursor.getString(1);
                tempAlarm.setTime(cursor.getString(2));
                tempAlarm.setDays(cursor.getString(3));
                tempAlarm.url = cursor.getString(4);
                tempAlarm.status = cursor.getString(5).equals("true");
                tempAlarm.PM = cursor.getString(6).equals("true");

                // Adding contact to list
                alarmList.add(tempAlarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return alarm list
        return alarmList;
    }

    // code to update the single alarm
    public int updateAlarm(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmIn.name);
        values.put(KEY_TIMESTAMP, alarmIn.getTimeStamp());
        values.put(KEY_DAYS, alarmIn.strRepDays());
        values.put(KEY_URL, alarmIn.url);
        values.put(KEY_STATUS, String.valueOf(alarmIn.status));
        values.put(KEY_AM_PM, String.valueOf(alarmIn.PM));

        // updating row
        return db.update(TABLE_ALARMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmIn.id) });
    }

    // Deleting single alarm
    public void deleteAlarm(Alarm alarmIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, KEY_ID + " = ?",
                new String[] { String.valueOf(alarmIn.id) });
        db.close();
    }

    public int getAlarmCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALARMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
}