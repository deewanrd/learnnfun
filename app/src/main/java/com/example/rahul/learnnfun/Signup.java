package com.example.rahul.learnnfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.logging.Logger;

public class Signup extends AppCompatActivity implements View.OnClickListener{


    //Defining views
    private EditText editTextUsername;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView already_login;

    private Button buttonAdminSignup;

    Logger logger=Logger.getLogger("AdminSignup");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Initializing views
        editTextUsername = (EditText) findViewById(R.id.input_username);
        editTextName = (EditText) findViewById(R.id.input_name);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        buttonAdminSignup = (Button) findViewById(R.id.btn_signup);
        already_login=(TextView)findViewById(R.id.already_login);


        //Setting listeners to button
        buttonAdminSignup.setOnClickListener(this);
        already_login.setOnClickListener(this);
    }


    //Adding an employee
    private void addAdmin(){

        final String username = editTextUsername.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        class AddAdmin extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Signup.this,"Adding...","Wait...",false,false);
                logger.info("Hi");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("oops! Please try again!")
                        ||s.equalsIgnoreCase("error")
                        ||s.equalsIgnoreCase("please fill all values")){
                    Toast.makeText(Signup.this,s,Toast.LENGTH_LONG).show();
                    logger.info("Hidf");}
                else {
                    Toast.makeText(Signup.this,s,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Signup.this, Login.class);
                    intent.putExtra("UID", s);
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                logger.info("Hi12345");
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_ADMIN_USERNAME,username);
                params.put(Config.KEY_ADMIN_NAME,name);
                params.put(Config.KEY_ADMIN_EMAIL,email);
                params.put(Config.KEY_ADMIN_PASSWORD,password);



                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddAdmin ae = new AddAdmin();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdminSignup){
            addAdmin();
        }
        if(v==already_login){
            startActivity(new Intent(this,Login.class));
        }
    }
}

