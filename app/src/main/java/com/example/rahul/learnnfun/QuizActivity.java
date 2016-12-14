package com.example.rahul.learnnfun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class QuizActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;

    private TextView Textv;
    private static final String REGISTER_URL = "http://learnnfun.16mb.com/question.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOPIC = "subject_id";

    Logger logger = Logger.getLogger("QuizActivity");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Textv = (TextView) findViewById(R.id.textView1);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            long j = iin.getLongExtra("sentIntent", 0);
            Textv.setText(j + "");
            //  logger.info(String.valueOf(j));
            quiz(Integer.toString((int) j));
        }
    }

    private void quiz(String j) {
        final int k = Integer.parseInt(j);

//        final String username = editTextUsername.getText().toString().trim();
//        final String password = editTextPassword.getText().toString().trim();
//        final String email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(QuizActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QuizActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put(KEY_USERNAME,username);
//                params.put(KEY_PASSWORD,password);
//                params.put(KEY_EMAIL, email);
                params.put(KEY_TOPIC, Integer.toString(k));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

//    private void quiz(String j){
//            class SendData extends AsyncTask<String,Void,String>{
//
//                @Override
//                protected String doInBackground(String... params) {
//                    int id= Integer.parseInt(params[0]);
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                    nameValuePairs.add(new BasicNameValuePair("name",Integer.toString(id)));
//                    logger.info(nameValuePairs.toString());
//                  //  nameValuePairs.add(new BasicNameValuePair("address", add));
//                    try {
//                        HttpClient httpClient = new DefaultHttpClient();
//                        HttpPost httpPost = new HttpPost("http://learnnfun.16mb.com/question.php");
//                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                        HttpResponse response = httpClient.execute(httpPost);
//
//                        HttpEntity entity = response.getEntity();
//
//
//                        } catch (ClientProtocolException e) {
//
//                        } catch (IOException e) {
//
//                        }
//                    return "success";
//                    }
//
//
//            }
//            SendData sd=new SendData();
//            sd.execute(j);
//            }
//
//
//    }






    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        DbHelper db=new DbHelper(this);
        quesList=db.getAllQuestions();
        currentQ=quesList.get(qid);
        txtQuestion=(TextView)findViewById(R.id.textView1);
        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        butNext=(Button)findViewById(R.id.button1);
        setQuestionView();
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
                RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());
                Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());
                if(currentQ.getANSWER().equals(answer.getText()))
                {
                    score++;
                    Log.d("score", "Your score"+score);
                }
                if(qid<5){
                    currentQ=quesList.get(qid);
                    setQuestionView();
                }else{
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_quiz, menu);
        return true;
    }
    private void setQuestionView()
    {
        txtQuestion.setText(currentQ.getQUESTION());
        rda.setText(currentQ.getOPTA());
        rdb.setText(currentQ.getOPTB());
        rdc.setText(currentQ.getOPTC());
        qid++;
    }*/

