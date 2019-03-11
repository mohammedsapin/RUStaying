package com.example.rustaying;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="usersTable";
    public static final String COL_1="ID";
    public static final String COL_2="firstName";
    public static final String COL_3="lastName";
    public static final String COL_4="email";
    public static final String COL_5="password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usersTable (ID INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, email TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String firstName, String lastName, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, password);

        //insert() returns the rowId the new inserted item
        long tableInput = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return tableInput;
    }

    public boolean checkUser(String email, String password){
        String[] id = {COL_1};
        SQLiteDatabase db = getReadableDatabase();

        String account = COL_4 + "=?" + " and " + COL_5 + "=?";
        String[] fullAccount = {email, password};
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

//    public Cursor getUsers(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor users = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
//        return users;
//    }

    public boolean isEmpty(EditText text){
        if (text.getText().toString().trim().length() > 0){
            return false;
        }else{
            return true;
        }
    }
    public boolean adminCheck(String email, String password){
        if (email.equals("admin") && password.equals("password")){
            return true;
        }
        return false;
    }
}

