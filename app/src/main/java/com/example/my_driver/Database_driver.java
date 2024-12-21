package com.example.my_driver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database_driver extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "login.db";
    private static final String col_1="ID";
    private static final String col_2="DRIVERNAME";
    private static final String col_3="PASSWORD";
    private static final String col_4="PHONE";
    private static final String col_5="GMAIL";





    public Database_driver(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRIVERS (ID INTEGER PRIMARY KEY AUTOINCREMENT,DRIVERNAME TEXT,PASSWORD TEXT,PHONE TEXT,GMAIL TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS DRIVERS");
        onCreate(db);

    }
    public boolean insertdata(String driverername,String password,String phone,String gmail){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_2,driverername);
        contentValues.put(col_3,password);
        contentValues.put(col_4,phone);
        contentValues.put(col_5,gmail);
        long result=db.insert("DRIVERS",null,contentValues);
        return result!=-1;
    }

   public String checklogin(String drivername,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={col_2};
        String selection ="DRIVERNAME=? AND PASSWORD=?";
        String[] selectionArgs={drivername,password};
       Cursor cursor=db.query("DRIVERS",columns,selection,selectionArgs,null,null,null);
       String result=null;
       if(cursor!=null && cursor.moveToFirst()){
           result=cursor.getString(cursor.getColumnIndex(col_2));
           cursor.close();
       }
       return  result;



   }


}
