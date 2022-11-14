package com.example.registrationpage.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.registrationpage.model.User;
import com.example.registrationpage.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(@Nullable Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+ Params.TABLE_NAME + "("
                + Params.ID + " INTEGER PRIMARY KEY, " + Params.NAME + " TEXT, "
                + Params.DOB + " TEXT, " + Params.EMAIL + " TEXT" + ")";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addAllUsers(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.NAME, user.getName());
        values.put(Params.DOB, user.getDateOfBirth());
        values.put(Params.EMAIL, user.getEmail());

        db.insert(Params.TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<User> getAllUsers(){
        List<User> usersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM "+ Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Params.ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(Params.NAME)));
                user.setDateOfBirth(cursor.getString(cursor.getColumnIndex(Params.DOB)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(Params.EMAIL)));
                usersList.add(user);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return usersList;
    }
}
