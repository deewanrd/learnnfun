package com.example.rahul.learnnfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://learnnfun.16mb.com/register.php";
   // private static final String REGISTER_URL = "10.0.2.2:3306/UserRegistration/register.php";
    public static final String KEY_ROLLNO = "roll_no";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";


    private EditText editTextRollno;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextRollno = (EditText) findViewById(R.id.roll_no);
        editTextName = (EditText) findViewById(R.id.name);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail= (EditText) findViewById(R.id.email);

        signup_button = (Button) findViewById(R.id.signup_button);

        signup_button.setOnClickListener(this);
    }

    private void registerUser(){
        final String input_rollno = editTextRollno.getText().toString().trim();
        final String input_name = editTextName.getText().toString().trim();
        final String input_password = editTextPassword.getText().toString().trim();
        final String input_email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Signup.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signup.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ROLLNO,input_rollno);
                params.put(KEY_NAME,input_name);
                params.put(KEY_PASSWORD,input_password);
                params.put(KEY_EMAIL, input_email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == signup_button){
            registerUser();
        }
    }
}