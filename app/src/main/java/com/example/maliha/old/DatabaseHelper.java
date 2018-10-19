package com.example.maliha.old;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DataAdd";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Reminder";
    public static final String TABLE_NAME = "Appointment";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EM = "emergency";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_PHONE,
            KEY_EM };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Appointment ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT UNIQUE, "
                + "phone TEXT, " + "emergency TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean addAppoint(Contact appoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, appoint.getName());
        values.put(KEY_PHONE, appoint.getPhone());
        values.put(KEY_EM, appoint.getEmergency());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
        return true;
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getdData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor geteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String s="1";
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_EM + " = '" + s + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPhone(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_PHONE + " FROM " + TABLE_NAME +
                " WHERE " + KEY_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_NAME +
                " WHERE " + KEY_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor get2Data(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + KEY_NAME.toLowerCase() + " LIKE '" + name + "%'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG, "deleteName: Deleting " + name + " from database.");;

        long del=db.delete(TABLE_NAME, KEY_ID + "="+id,null);
        db.close();
        return true;
    }




}

