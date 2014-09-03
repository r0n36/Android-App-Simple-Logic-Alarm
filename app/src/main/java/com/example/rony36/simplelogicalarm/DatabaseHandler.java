package com.example.rony36.simplelogicalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RonyLap on 9/2/2014.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "simpleAlarm";
    private static final String TABLE_LOGIC_ALARM = "logicAlarm";

    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "alarm_time";
    private static final String KEY_STATUS = "status";
    private static final String KEY_REPEAT = "repeat";
    private static final String KEY_SUN = "sun";
    private static final String KEY_MON = "mon";
    private static final String KEY_TUE = "tue";
    private static final String KEY_WED = "wed";
    private static final String KEY_THU = "thu";
    private static final String KEY_FRI = "fri";
    private static final String KEY_SAT = "sat";
    private static final String KEY_URGENCY = "urgency";
    private static final String KEY_OFF_METHOD = "off_method";
    private static final String KEY_RINGTONE = "ringtone_path";
    private static final String KEY_NOTE = "note";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIC_ALARM_TABLE = "CREATE TABLE " + TABLE_LOGIC_ALARM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
                + KEY_STATUS + " INTEGER DEFAULT 0,"
                + KEY_REPEAT + " INTEGER DEFAULT 0,"
                + KEY_SUN + " INTEGER DEFAULT 0,"
                + KEY_MON + " INTEGER DEFAULT 0,"
                + KEY_TUE + " INTEGER DEFAULT 0,"
                + KEY_WED + " INTEGER DEFAULT 0,"
                + KEY_THU + " INTEGER DEFAULT 0,"
                + KEY_FRI + " INTEGER DEFAULT 0,"
                + KEY_SAT + " INTEGER DEFAULT 0,"
                + KEY_URGENCY + " INTEGER DEFAULT -1,"
                + KEY_OFF_METHOD + " INTEGER DEFAULT -1,"
                + KEY_RINGTONE + " TEXT,"
                + KEY_NOTE + " TEXT"+
                ")";
        db.execSQL(CREATE_LOGIC_ALARM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIC_ALARM);

        // Create tables again
        onCreate(db);
    }

    // Adding new alarm
    void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME,        alarm.get_alarm_time());
        values.put(KEY_STATUS,      alarm.get_status());
        values.put(KEY_REPEAT,      alarm.get_repeat());
        values.put(KEY_SUN,         alarm.get_sun());
        values.put(KEY_MON,         alarm.get_mon());
        values.put(KEY_TUE,         alarm.get_thu());
        values.put(KEY_WED,         alarm.get_wed());
        values.put(KEY_THU,         alarm.get_thu());
        values.put(KEY_FRI,         alarm.get_fri());
        values.put(KEY_SAT,         alarm.get_sat());
        values.put(KEY_URGENCY,     alarm.get_urgency());
        values.put(KEY_OFF_METHOD,  alarm.get_off_method());
        values.put(KEY_RINGTONE,    alarm.get_ringtone_path());
        values.put(KEY_NOTE,        alarm.get_note());

        // Inserting Row
        db.insert(TABLE_LOGIC_ALARM, null, values);
        db.close(); // Closing database connection
    }

    // Getting single alarm
    Alarm getAlarm(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOGIC_ALARM, new String[] { KEY_ID, KEY_TIME,
                        KEY_STATUS, KEY_REPEAT, KEY_SUN, KEY_MON,
                        KEY_TUE, KEY_WED, KEY_THU, KEY_FRI, KEY_SAT,
                        KEY_URGENCY, KEY_OFF_METHOD, KEY_RINGTONE, KEY_NOTE, }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Alarm alarm = new Alarm(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)),
                Integer.parseInt(cursor.getString(8)),
                Integer.parseInt(cursor.getString(9)),
                Integer.parseInt(cursor.getString(10)),
                Integer.parseInt(cursor.getString(11)),
                Integer.parseInt(cursor.getString(12)),
                cursor.getString(13),
                cursor.getString(14)
        );
        // return alarm
        return alarm;
    }

    // Getting All Alarms
    public List<Alarm> getAllAlarms() {
        List<Alarm> alarmList = new ArrayList<Alarm>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIC_ALARM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.set_id(Integer.parseInt(cursor.getString(0)));
                alarm.set_alarm_time(cursor.getString(1));
                alarm.set_status(Integer.parseInt(cursor.getString(2)));
                alarm.set_repeat(Integer.parseInt(cursor.getString(3)));
                alarm.set_sun(Integer.parseInt(cursor.getString(4)));
                alarm.set_mon(Integer.parseInt(cursor.getString(5)));
                alarm.set_tue(Integer.parseInt(cursor.getString(6)));
                alarm.set_wed(Integer.parseInt(cursor.getString(7)));
                alarm.set_thu(Integer.parseInt(cursor.getString(8)));
                alarm.set_fri(Integer.parseInt(cursor.getString(9)));
                alarm.set_sat(Integer.parseInt(cursor.getString(10)));
                alarm.set_urgency(Integer.parseInt(cursor.getString(11)));
                alarm.set_off_method(Integer.parseInt(cursor.getString(12)));
                alarm.set_ringtone_path(cursor.getString(13));
                alarm.set_note(cursor.getString(14));
                // Adding alarm to list
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }

        // return alarm list
        return alarmList;
    }

    // Updating single alarm
    public int updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME,        alarm.get_alarm_time());
        values.put(KEY_STATUS,      alarm.get_status());
        values.put(KEY_REPEAT,      alarm.get_repeat());
        values.put(KEY_SUN,         alarm.get_sun());
        values.put(KEY_MON,         alarm.get_mon());
        values.put(KEY_TUE,         alarm.get_thu());
        values.put(KEY_WED,         alarm.get_wed());
        values.put(KEY_THU,         alarm.get_thu());
        values.put(KEY_FRI,         alarm.get_fri());
        values.put(KEY_SAT,         alarm.get_sat());
        values.put(KEY_URGENCY,     alarm.get_urgency());
        values.put(KEY_OFF_METHOD,  alarm.get_off_method());
        values.put(KEY_RINGTONE,    alarm.get_ringtone_path());
        values.put(KEY_NOTE,        alarm.get_note());

        // updating row
        return db.update(TABLE_LOGIC_ALARM, values, KEY_ID + " = ?",
                new String[] { String.valueOf(alarm.get_id()) });
    }

    // Deleting single alarm
    public void deleteAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIC_ALARM, KEY_ID + " = ?",
                new String[] { String.valueOf(alarm.get_id()) });
        db.close();
    }


    // Getting alarms Count
    public int getAlarmsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIC_ALARM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
