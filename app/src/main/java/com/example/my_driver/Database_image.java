package com.example.my_driver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_image extends SQLiteOpenHelper {
    private static final String IMAGE_DATABASE = "image.db";
    private static final int DATABASE_VERSION = 1;
    private static final String COL_ID = "id";
    private static final String COL_NO = "LICENSE_NO";
    private static final String COL_exp = "EXPIRIENCE";
    private static final String COL_LIMAGE = "LICENSE";




    public Database_image(@Nullable Context context) {
        super(context,IMAGE_DATABASE,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRIVERS_DETAILS (ID INTEGER PRIMARY KEY AUTOINCREMENT,LICENSE_NO TEXT,EXPIRIENCE INTEGER,LICENSE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS DRIVERS_DETAILS");
        onCreate(db);
    }

    public boolean insertdata(String license_no,int experience,byte[] license){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NO, license_no);
        contentValues.put(COL_exp, experience);
        contentValues.put(COL_LIMAGE, license);
        long result = db.insert("DRIVERS_DETAILS", null, contentValues);
        return result != -1; // returns true if inserted sucesfuly
    }

}
