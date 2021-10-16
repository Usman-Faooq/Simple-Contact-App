package com.example.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.shapes.OvalShape;

import androidx.annotation.Nullable;

public class DBConnector extends SQLiteOpenHelper {

    public static final String dbname = "Contactbook.db";

    public DBConnector(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String quary = "create table contact(id INTEGER primary key autoincrement, name text, number text)";
        sqLiteDatabase.execSQL(quary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists contact");
        onCreate(sqLiteDatabase);
    }

    public boolean addcontact(String name, String contact){

        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", contact);
        long result = sql.insert("contact",null, values);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor viewContactBook(){
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor cursor = sql.rawQuery("Select * from contact", null);
        return cursor;
    }

    public boolean deletedata(String name){
       SQLiteDatabase sql = this.getWritableDatabase();

       Cursor cursor = sql.rawQuery("select * from contact where name = ?", new String[]{name});
       if (cursor.getCount() > 0) {
           long result = sql.delete("contact", "name = ?", new String[]{name});
           if (result == -1) {
               return false;
           } else {
               return true;
           }
       }else{
           return false;
       }
    }

    public boolean updatedata(String name, String contact){
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", contact);
        Cursor cursor = sql.rawQuery("select * from contact where name = ?", new String[]{name});
        if (cursor.getCount() > 0 ){
           long result = sql.update("contact", values, "name = ?", new String[]{name});
           if (result == -1){
               return false;
           }else {
               return true;
           }
        }else{
            return false;
        }
    }

}
