package com.example.taskremainderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_submit;
    TextView newreg;
   public EditText phone,password;
    CheckBox chk_remem;
    SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        phone.setText( "" );
        password.setText( "" );
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //super.onBackPressed();
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        btn_submit=findViewById( R.id.btn_submit );
        phone=findViewById( R.id.et_phone );
        password=findViewById( R.id.et_password );
        newreg = findViewById( R.id.tv_newreg );
        chk_remem=findViewById( R.id.chk_remem );
        sharedPreferences=getSharedPreferences( "Login",MODE_PRIVATE );
        final String mobile=sharedPreferences.getString( "phone",null );
        final String pass=sharedPreferences.getString( "pass",null );
        phone.setText( "" );
        password.setText( "" );
     //  phone.setText( mobile );
/*chk_remem.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(chk_remem.isChecked())
        {
            password.setText( pass );
        }
        else
        {
            password.setText( "" );
        }
    }
} );*/



phone.setOnFocusChangeListener( new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(phone.getText().toString().equals( mobile ) )
        {
           password.setText( pass );
           chk_remem.setChecked( true );

        }
        else
        {
            password.setText( "" );
            chk_remem.setChecked( false );
        }
    }
} );
password.setOnFocusChangeListener( new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
        {
            chk_remem.setVisibility( View.VISIBLE );
        }
        else
        {
            chk_remem.setVisibility(View.GONE );
        }
    }
} );



        newreg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( MainActivity.this,NewUserRegistration.class );
                startActivity( i );
            }
        } );
        btn_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(password.getText().toString().equals( pass )&& phone.getText().toString().equals( mobile )) {

    Intent i = new Intent( MainActivity.this, Tasks.class );
    startActivity( i );
}
else
{
    Toast.makeText( MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT ).show();
}

            }
        } );
    }
}
