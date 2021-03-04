package com.example.taskremainderapplication.Database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskremainderapplication.R;
import com.example.taskremainderapplication.Tasks;
import com.example.taskremainderapplication.TasksList;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    DBManager dbManager;
    String delete;
    Context context;
    ArrayList<Javabean> arrayList;
    LayoutInflater layoutInflater;

    public ListAdapter(Context context, ArrayList<Javabean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        View v = layoutInflater.inflate( R.layout.root_layout, parent, false );
        final TextView liid = v.findViewById( R.id.tv_id );
        final TextView title_li = v.findViewById( R.id.tv_title );
        final TextView descri = v.findViewById( R.id.tv_desc );
        final TextView date_li = v.findViewById( R.id.tvv_date );
        final TextView time_li = v.findViewById( R.id.tvv_time );
        final ImageView img_delete = v.findViewById( R.id.img_delete );
        final ImageView img_update = v.findViewById( R.id.img_update );

        final Javabean javabean = arrayList.get( position );
        liid.setText( javabean.getUserid() );
        title_li.setText( javabean.getTitle() );
        descri.setText( javabean.getDesc() );
        date_li.setText( javabean.getDate() );
        time_li.setText( javabean.getTime() );
        dbManager = new DBManager( context );
        dbManager.open();

        img_delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
                alertDialog.setTitle( "MY TASK" );
                alertDialog.setMessage( "Are You Sure to Delete" );
                alertDialog.setCancelable( false );
                alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        Toast.makeText( context, "Not Deleted", Toast.LENGTH_SHORT ).show();
                    }
                } );
                alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete = liid.getText().toString();
                        dbManager.delete( delete );
                        Intent i = new Intent( context, TasksList.class );
                        context.startActivity( i );
                        Toast.makeText( context, "deleted Successfully", Toast.LENGTH_SHORT ).show();

                    }
                } );
                alertDialog.show();

            }
        } );
        img_update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_id = liid.getText().toString();
                String u_title = title_li.getText().toString();
                String u_descr = descri.getText().toString();
                String u_date = date_li.getText().toString();
                String u_time = time_li.getText().toString();
                SharedPreferences sharedPreferences = context.getSharedPreferences( "Update", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString( "id", u_id );
                editor.putString( "title", u_title );
                editor.putString( "descr", u_descr );
                editor.putString( "date", u_date );
                editor.putString( "time", u_time );
                editor.commit();

                final AlertDialog al = new AlertDialog.Builder( context ).create();
                parent.findViewById( android. R.id.content );
                final View v1 = LayoutInflater.from( context ).inflate( R.layout.alert_custom, parent, false );
                final TextView tv_up_id = v1.findViewById( R.id.tv_up_id );
                final EditText et_up_title = v1.findViewById( R.id.et_up_title );
                final EditText et_up_desc = v1.findViewById( R.id.et_up_desc );
                final EditText et_up_date = v1.findViewById( R.id.et_up_date );
                final EditText et_up_time = v1.findViewById( R.id.et_up_time );
                final Button btn_up_update = v1.findViewById( R.id.btn_up_update );


                final ImageView img_dele = v1.findViewById( R.id.img_dele );

                al.setCancelable( false );
                al.setView( v1 );

                String up_id = sharedPreferences.getString( "id", "" );
                String up_title = sharedPreferences.getString( "title", "" );
                String up_desc = sharedPreferences.getString( "descr", "" );
                String up_date = sharedPreferences.getString( "date", "" );
                String up_time = sharedPreferences.getString( "time", "" );

                tv_up_id.setText( up_id );
                et_up_title.setText( up_title );
                et_up_desc.setText( up_desc );
                et_up_date.setText( up_date );
                et_up_time.setText( up_time );

                btn_up_update.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        javabean.setUserid( tv_up_id.getText().toString() );
                        javabean.setTitle( et_up_title.getText().toString() );
                       javabean.setDate( et_up_date.getText().toString() );
                        javabean.setTime( et_up_time.getText().toString() );
                        javabean.setDesc( et_up_desc.getText().toString() );
                        dbManager.update( javabean );
                        Intent i = new Intent( context, TasksList.class );
                        context.startActivity( i );
                        Toast.makeText( context, "data update successfully", Toast.LENGTH_SHORT ).show();
                    }
                } );
                img_dele.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        al.dismiss();
                    }
                } );
                al.show();
            }
        } );
        return v;
    }
}
