package com.example.madus.appointments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;

/**
 * Created by madus on 4/19/2016.
 */
public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "appointments.db";
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DETAILS = "details";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_TIME + " TEXT ," +
                COLUMN_DETAILS + " TEXT " +
                ");";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(sqLiteDatabase);
    }


    //add a new appointment
    public void addAppointment(Appointment appointment){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,appointment.get_title());
        values.put(COLUMN_DATE,appointment.get_date());
        values.put(COLUMN_TIME,appointment.get_time());
        values.put(COLUMN_DETAILS,appointment.get_details());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(TABLE_APPOINTMENTS,null,values);
        sqLiteDatabase.close();
    }

    //delete all appointments
    public void deleteAll(String date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\"" + date + "\";");
    }

    //delete selected appointments
    public void delete(String id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }


    //update date
    public void updateDate(String id ,String date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE " + TABLE_APPOINTMENTS +" SET "+ COLUMN_DATE + " =\"" + date +"\"" + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }



    //get appointments on date
    public Appointment[] appointmentsOnDate(String date){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + " LIKE '%" + date + "%'";

        //Cursor point to a location in your results
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        Appointment[] appointments  = new Appointment[c.getCount()];
        //Move to the first row
        c.moveToFirst();
        int arrIndex = 0;

       //while (!c.isAfterLast()){
        for(int i=0; i<c.getCount();i++) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                Appointment temp = new Appointment();
                temp.set_id(c.getString(c.getColumnIndex("_id")));
                temp.set_title(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                temp.set_date(c.getString(c.getColumnIndex(COLUMN_DATE)));
                temp.set_time(c.getString(c.getColumnIndex(COLUMN_TIME)));
                temp.set_details(c.getString(c.getColumnIndex(COLUMN_DETAILS)));
                appointments[arrIndex++] = temp;
                c.move(1);
             }
        }
        //}
        sqLiteDatabase.close();
        return appointments;
    }

    //get all appointments
    public Appointment[] allAppointments(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_APPOINTMENTS +";";

        //Cursor point to a location in your results
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        Appointment[] appointments  = new Appointment[c.getCount()];
        //Move to the first row
        c.moveToFirst();
        int arrIndex = 0;

        //while (!c.isAfterLast()){
        for(int i=0; i<c.getCount();i++) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                Appointment temp = new Appointment();
                temp.set_id(c.getString(c.getColumnIndex("_id")));
                temp.set_title(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                temp.set_date(c.getString(c.getColumnIndex(COLUMN_DATE)));
                temp.set_time(c.getString(c.getColumnIndex(COLUMN_TIME)));
                temp.set_details(c.getString(c.getColumnIndex(COLUMN_DETAILS)));
                appointments[arrIndex++] = temp;
                c.move(1);
            }
        }
        //}
        sqLiteDatabase.close();
        return appointments;
    }

    //get appointment by ID
    public Appointment getAppointmentByID(String id){
        Appointment appointment = new Appointment();

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = ("SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_ID + " LIKE '%" + id + "%'");

        Cursor c = sqLiteDatabase.rawQuery(query,null);

        c.moveToFirst();
        for(int i=0; i<c.getCount();i++) {
            if (c.getString(c.getColumnIndex(COLUMN_TITLE)) != null) {
                Appointment temp = new Appointment();
                temp.set_id(c.getString(c.getColumnIndex("_id")));
                temp.set_title(c.getString(c.getColumnIndex(COLUMN_TITLE)));
                temp.set_date(c.getString(c.getColumnIndex(COLUMN_DATE)));
                temp.set_time(c.getString(c.getColumnIndex(COLUMN_TIME)));
                temp.set_details(c.getString(c.getColumnIndex(COLUMN_DETAILS)));
                appointment = temp;
                c.move(1);
            }
        }
        sqLiteDatabase.close();
        return appointment;
    }







}
