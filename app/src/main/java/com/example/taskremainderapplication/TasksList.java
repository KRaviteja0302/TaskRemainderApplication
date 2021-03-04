package com.example.taskremainderapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.taskremainderapplication.Database.DBManager;
import com.example.taskremainderapplication.Database.Javabean;
import com.example.taskremainderapplication.Database.ListAdapter;

import java.util.ArrayList;

public class TasksList extends AppCompatActivity {
ListView listView;
ListAdapter listAdapter;
LinearLayoutManager linearLayoutManager;
ArrayList<Javabean>arrayList;
Javabean javabean;
DBManager dbManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent( TasksList.this,Tasks.class );
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tasks_list );
listView=findViewById( R.id.list_view );

        dbManager=new DBManager( getApplicationContext() );
        dbManager.open();
        arrayList=dbManager.fetchall();
        listAdapter=new ListAdapter( this,arrayList );
        registerForContextMenu( listView );
        //linearLayoutManager=new LinearLayoutManager( this,LinearLayoutManager.VERTICAL,false );

        listView.setAdapter( listAdapter );






    }

}
