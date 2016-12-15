package com.example.rahul.learnnfun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private static final String LOGIN_URL = "http://learnnfun.16mb.com/login.php";
    public static final String KEY_ROLLNO="roll_no";
    public static final String KEY_PASSWORD="password";

    private EditText editTextRollno;
    private EditText editTextPassword;
    private Button login_button;
    private TextView account_confirm;

    private String input_rollno;
    private String input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        editTextRollno = (EditText) findViewById(R.id.roll_no);
        editTextPassword = (EditText) findViewById(R.id.password);

        login_button = (Button) findViewById(R.id.login_button);
        account_confirm=(TextView)findViewById(R.id.account_confirm);

        login_button.setOnClickListener(this);
        account_confirm.setOnClickListener(this);
    }


    private void userLogin() {
        input_rollno = editTextRollno.getText().toString().trim();
        input_password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_ROLLNO,input_rollno);
                map.put(KEY_PASSWORD,input_password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(){
        Intent intent = new Intent(this, PreviousActivity.class);
        intent.putExtra(KEY_ROLLNO, input_rollno);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v==login_button) {
            userLogin();
        }
        if(v==account_confirm){
            Intent intent=new Intent(this,Signup.class);
            startActivity(intent);
        }
    }

}






