package com.example.rahul.learnnfun;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private TextView no_account;
    private Button btnlogin;

    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.input_username);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        no_account = (TextView) findViewById(R.id.no_account);
        no_account.setOnClickListener(this);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(Login.this, Topic.class);
            startActivity(intent);
        }
    }


   private void login(){
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        userLogin(username,password);
    }

    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Please Wait",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){
                    //Creating a shared preference
                    SharedPreferences sharedPreferences = Login.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    //editor.putString(Config.EMAIL_SHARED_PREF, email);
                    editor.putString(Config.EMAIL_SHARED_PREF, username);


                    //Saving values to editor
                    editor.commit();
                    Toast.makeText(Login.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Topic.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(Login.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> data = new HashMap<>();
                data.put(Config.KEY_ADMIN_USERNAME,username);
                data.put(Config.KEY_ADMIN_PASSWORD,password);

                RequestHandler ruc = new RequestHandler();

                String result = ruc.sendPostRequest(Config.ADMIN_LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute();
    }

    @Override
    public void onClick(View v) {
        //Calling the login function
        if (v == btnlogin) {
            login();
        }
        if (v == no_account) {
            startActivity(new Intent(this, Signup.class));
        }
    }
}
