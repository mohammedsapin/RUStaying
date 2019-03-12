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
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, firstName TEXT, lastName TEXT)");
        //Change table name to usersTable
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(String firstName, String lastName, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
        contentValues.put("password", password);
        //contentValues.put("firstName", firstName);
        //contentValues.put("lastName", lastName);

        //insert() returns the rowId the new inserted item
        long tableInput = db.insert("registeruser",null,contentValues);
        //Again change table name to usersTable
        db.close();
        return tableInput;
    }

    //Is this method ever used??
    public boolean checkUser(String email, String password){
        String[] id = {COL_1};
        SQLiteDatabase db = getReadableDatabase();

        //3 lines below don't make sense to me
        //Why do you have account and fullAccount variables? Why are you storing it in the database like that?
        String account = COL_2 + "=?" + " and " + COL_3 + "=?";
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

    public Guest getGuestInfo(String email)
    {
        //Create Guest object and send it back

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM registeruser;", null);
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String password = cursor.getString(cursor.getColumnIndex("password"));

        Guest g = new Guest(id, email, password);
        return g;
    }

    public boolean isEmpty(EditText text){
        if (text.getText().toString().trim().length() > 0){
            return false;
        }else{
            return true;
        }
    }
}

