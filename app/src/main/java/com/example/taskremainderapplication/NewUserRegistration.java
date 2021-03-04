package com.example.taskremainderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.taskremainderapplication.Database.DBManager;
import com.example.taskremainderapplication.Database.Javabean;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class NewUserRegistration extends AppCompatActivity {
    EditText nucnfpassword, nupass, nuphone, nuname, nuemail;
    Button nusubmit;
    String regexphone = "[7-9]{1}[0-9]{9}";
    String regexPassword = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,10}";
    AwesomeValidation mAwesomeValidation = new AwesomeValidation( BASIC );
    DBManager dbManager;
    SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
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
        setContentView( R.layout.activity_new_user_registration );
        nuname = findViewById( R.id.et_nuname );
        nuemail = findViewById( R.id.et_email );
        nuphone = findViewById( R.id.et_nuphone );
        nupass = findViewById( R.id.et_nupass );
        nucnfpassword = findViewById( R.id.et_nucpassword );
        nusubmit = findViewById( R.id.nusubmit );

        dbManager = new DBManager( getApplicationContext() );
        dbManager.open();

        sharedPreferences=getSharedPreferences( "Login",MODE_PRIVATE );
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        mAwesomeValidation.addValidation(this, R.id.et_nuname, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(this, R.id.et_nuphone, regexphone, R.string.err_tel);
        mAwesomeValidation.addValidation(this, R.id.et_email, android.util.Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.et_nupass, regexPassword, R.string.err_password);
        mAwesomeValidation.addValidation(this, R.id.et_nucpassword, R.id.et_nupass, R.string.err_password_confirmation);


        nusubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()) {
                    String name = nuname.getText().toString();
                    String email = nuemail.getText().toString();
                    String phone=nuphone.getText().toString();
                    String pass=nupass.getText().toString();
                    editor.putString( "phone",phone );
                    editor.putString( "pass",pass );
                    editor.commit();

                    Javabean javabean = new Javabean();
                    javabean.setName( name );
                    javabean.setEmail( email );
                    javabean.setPhone( phone );
                    javabean.setPassword( pass );

                    dbManager.insertuser( javabean );

                    Intent i = new Intent( NewUserRegistration.this, MainActivity.class );
                    startActivity( i );
                }
                else
                {
                    Toast.makeText( NewUserRegistration.this, "Unsuccessfull Rgistration", Toast.LENGTH_SHORT ).show();
                }
            }
        } );


    }
}
