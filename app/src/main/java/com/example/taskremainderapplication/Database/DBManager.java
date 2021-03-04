package com.example.taskremainderapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
    Context context;
    SQLiteDatabase sqLiteDatabase;
    DbHelper dbHelper;

    public  DBManager(Context context)
    {
        this.context=context;
    }

    public DBManager open() throws SQLException
    {
        dbHelper=new DbHelper(context);
        sqLiteDatabase=dbHelper.getWritableDatabase();
        return this;
    }
    public  void close()
    {
        dbHelper.close();
    }

    public void insert(Javabean javabean)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(dbHelper.USER_ID,javabean.getUserid());
        contentValues.put(dbHelper.TITLE,javabean.getTitle());
        contentValues.put(dbHelper.DATE,javabean.getDate());
        contentValues.put(dbHelper.TIME,javabean.getTime());
        contentValues.put(dbHelper.DESC_EVENT,javabean.getDesc());
       sqLiteDatabase.insert(dbHelper.EVENT_TABLE,null,contentValues);
           }
    public void insertuser(Javabean javabean)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(dbHelper.ID,javabean.getId());

        contentValues.put(dbHelper.NAME,javabean.getName());
        contentValues.put(dbHelper.PHONE,javabean.getPhone());
        contentValues.put(dbHelper.EMAIL,javabean.getEmail());
        contentValues.put(dbHelper.PASSWORD,javabean.getPassword());
        sqLiteDatabase.insert(dbHelper.USER_TABLE,null,contentValues);
    }

    public ArrayList<Javabean>fetchall()
    {
        String[] columns=new String[]{dbHelper.USER_ID,dbHelper.TITLE,dbHelper.DATE,dbHelper.TIME,dbHelper.DESC_EVENT};
        Cursor cursor=sqLiteDatabase.query( dbHelper.EVENT_TABLE,columns,null,null,null,null,null );
        ArrayList<Javabean>arrayList=new ArrayList<>(  );
        if(cursor!=null && cursor.moveToFirst())
        {
            do {
               Javabean javabean=new Javabean();
               javabean.setUserid( cursor.getString( cursor.getColumnIndex( dbHelper.USER_ID ) ) );
               javabean.setTitle( cursor.getString( cursor.getColumnIndex( dbHelper.TITLE ) ) );
               javabean.setDate( cursor.getString( cursor.getColumnIndex( dbHelper.DATE ) ) );
               javabean.setTime( cursor.getString( cursor.getColumnIndex( dbHelper.TIME ) ) );
               javabean.setDesc( cursor.getString( cursor.getColumnIndex( dbHelper.DESC_EVENT ) ) );
               arrayList.add( javabean );
            }while (cursor.moveToNext());

        }
        cursor.close();;
        return  arrayList;
    }


    public void delete(String id)
    {
        sqLiteDatabase.delete( dbHelper.EVENT_TABLE,dbHelper.USER_ID + " = " + id,null );
    }
    public int update(Javabean javabean)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(dbHelper.USER_ID,javabean.getUserid());
        contentValues.put(dbHelper.TITLE,javabean.getTitle());
        contentValues.put(dbHelper.DATE,javabean.getDate());
        contentValues.put(dbHelper.TIME,javabean.getTime());
        contentValues.put(dbHelper.DESC_EVENT,javabean.getDesc());
      int i=  sqLiteDatabase.update(dbHelper.EVENT_TABLE,contentValues, dbHelper.USER_ID + "=" + javabean.getUserid() , null);
      return i;
    }



}
