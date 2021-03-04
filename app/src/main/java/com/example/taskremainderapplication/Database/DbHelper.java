package com.example.taskremainderapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public  static  final String DB_NAME="Event";
    public  static  final int DB_VERSION=1;
    public  static  final String EVENT_TABLE="event_table";
    public  static  final String USER_TABLE="USER_table";
    public  static  final String USER_ID="userid";
    public  static  final String ID="userid";
    public  static  final String TITLE="title";
    public  static  final String DATE="date";
    public  static  final String TIME="time";
    public  static  final String DESC_EVENT="desc_event";
    public  static  final String NAME="name";
    public  static  final String PHONE="phone";
    public  static  final String EMAIL="email";
    public  static  final String PASSWORD="password";

    public  static  final String CREATE_EVENT_TABLE=" create table " +  EVENT_TABLE + " ( " + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +  " TEXT NOT NULL, " +  DATE + " TEXT NOT NULL, "  +  TIME +  " TEXT NOT NULL," +  DESC_EVENT + " TEXT NOT NULL)" ;
    public  static  final String CREATE_USER_TABLE=" create table " +  USER_TABLE + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME +  " TEXT NOT NULL, " +  PHONE + " TEXT NOT NULL, "  +  EMAIL +  " TEXT NOT NULL," +  PASSWORD + " TEXT NOT NULL)" ;



    public DbHelper(@Nullable Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + EVENT_TABLE);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(sqLiteDatabase);

    }
}
