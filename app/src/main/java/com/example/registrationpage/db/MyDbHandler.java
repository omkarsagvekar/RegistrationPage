package com.example.registrationpage.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.registrationpage.model.User;
import com.example.registrationpage.params.Params;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(@Nullable Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE "+ Params.TABLE_NAME + "("
                + Params.ID + "INTEGER PRIMARY KEY," + Params.NAME + " TEXT, "
                + Params.DOB + " TEXT, " + Params.EMAIL + " TEXT " + ")";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void addAllUsers(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Params.ID, user.getId());
        values.put(Params.NAME, user.getName());
        values.put(Params.DOB, user.getDateOfBirth());
        values.put(Params.EMAIL, user.getEmail());

        db.insert(Params.TABLE_NAME, null, values);
        db.close();
    }
}
