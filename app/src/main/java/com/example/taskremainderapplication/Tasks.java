package com.example.taskremainderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allyants.notifyme.NotifyMe;
import com.example.taskremainderapplication.Database.DBManager;
import com.example.taskremainderapplication.Database.Javabean;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Tasks extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText et_title, et_event;
   TextView tv_date,tv_time;

    Calendar now = Calendar.getInstance();
    Button save, cancel;
    ImageView img_cal_time;
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    DBManager dbManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent( Tasks.this,TasksList.class );
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tasks );
        save = findViewById( R.id.btn_save );
        cancel = findViewById( R.id.btn_cancel );
        img_cal_time = findViewById( R.id.img_cal_time );
        tv_date = findViewById( R.id.tv_date );
        tv_time = findViewById( R.id.tv_time);
        et_event = findViewById( R.id.et_event );
        et_title = findViewById( R.id.et_title );

        dpd = DatePickerDialog.newInstance(
                Tasks.this,
                now.get( Calendar.YEAR ),
                now.get( Calendar.MONTH ),
                now.get( Calendar.DAY_OF_MONTH )
        );

        tpd = TimePickerDialog.newInstance(
                Tasks.this,
                now.get( Calendar.HOUR_OF_DAY ),
                now.get( Calendar.MINUTE ),
                now.get( Calendar.SECOND ),
                false
        );

        dbManager=new DBManager( getApplicationContext() );
        dbManager.open();
        img_cal_time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show( getFragmentManager(), "Datepickerdialog" );
            }
        } );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=et_title.getText().toString();
                String event=et_event.getText().toString();
                String date=tv_date.getText().toString();
                String time=tv_time.getText().toString();
                Javabean javabean=new Javabean();
                javabean.setTitle( title );
                javabean.setDesc( event );
                javabean.setDate( date );
                javabean.setTime( time );
                dbManager.insert (javabean );
                            Intent i = new Intent( Tasks.this, TasksList.class );
                startActivity( i );

            }
        } );

        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }


        } );
    }

    private void clear() {

        et_title.setText( "" );
        et_event.setText( "" );
        tv_date.setText( "" );
        tv_time.setText( "" );
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = tv_date.getText().toString();
        date = String.valueOf( year + "/" + monthOfYear + "/" + dayOfMonth );
        tv_date.setText( date );
        now.set( Calendar.YEAR, year );
        now.set( Calendar.MONTH, monthOfYear );
        now.set( Calendar.DAY_OF_MONTH, dayOfMonth );
        tpd.show( getFragmentManager(), "Timepickerdialog" );
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = tv_time.getText().toString();
        time = String.valueOf( hourOfDay + "h:" + minute + "m:" + second + "s" );
        tv_time.setText( time );
        now.set( Calendar.HOUR_OF_DAY, hourOfDay );
        now.set( Calendar.MINUTE, minute );
        now.set( Calendar.SECOND, second );

        NotifyMe notifyMe = new NotifyMe.Builder( getApplicationContext() )
                .title( et_title.getText().toString() )
                .content( et_event.getText().toString() )
                .color( 255, 0, 0, 255 )
                .led_color( 255, 255, 255, 255 )
                .time( now )
                .addAction( new Intent(), "Snooze", false )
                .key( "test" )
                .addAction( new Intent(), "Dismiss", true, false )
                .addAction( new Intent(), "Done" )
                .large_icon( R.mipmap.ic_launcher_round )
                .rrule( "FREQ=MINUTELY;INTERVAL=5;COUNT=2" )
                .build();

    }
}
