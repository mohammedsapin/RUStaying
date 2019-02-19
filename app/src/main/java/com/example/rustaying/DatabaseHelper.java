package com.example.rustaying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    public static final String COL_1="ID";
    public static final String COL_2="email";
    public static final String COL_3="password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long tableInput = db.insert("registeruser",null,contentValues);
        db.close();
        return tableInput;
    }

    public boolean checkUser(String email, String password){
        String[] id = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String account = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] fullAccount = {email,password};
        Cursor cursor = db.query(TABLE_NAME,id,account,fullAccount,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean isEmpty(EditText text){
        if (text.getText().toString().trim().length() > 0){
            return false;
        }else{
            return true;
        }
    }
}

