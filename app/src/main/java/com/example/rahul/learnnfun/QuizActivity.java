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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuizActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    RadioButton radio0,radio1,radio2,radio3;
    Button butNext;
    private TextView question_text;
    Logger logger = Logger.getLogger("QuizActivity");
    private String topic_id;
    private String JSON_STRING;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question_text = (TextView) findViewById(R.id.question_text);
        Intent intent = getIntent();
        topic_id = intent.getStringExtra(Config.TOPIC_ID);
        radio0 = (RadioButton) findViewById(R.id.radio0);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        butNext = (Button) findViewById(R.id.button1);
        getQuestion();
    }

    private void getQuestion(){
        class GetQuestion extends AsyncTask<Void,Void,String>{
                ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(QuizActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING=s;
                showQuestion();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                logger.info(topic_id+"hu");
                String s = rh.sendGetRequestParam(Config.URL_GET_QUESTION,topic_id);
                logger.info(s);
                return s;
            }
        }
        GetQuestion gq=new GetQuestion();
        gq.execute();
    }

    private void showQuestion(){
            JSONObject jsonObject =null;
        quesList=new ArrayList<Question>();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                currentQ = new Question();
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    logger.info(String.valueOf(jo));
                    int id = jo.getInt(Config.TAG_ID);
                    String question = jo.getString(Config.TAG_QUESTION);
                    String op1 = jo.getString(Config.TAG_OP1);
                    String op2 = jo.getString(Config.TAG_OP2);
                    String op3 = jo.getString(Config.TAG_OP3);
                    String op4 = jo.getString(Config.TAG_OP4);
                    String ans = jo.getString(Config.TAG_ANS);
                    currentQ = new Question(question, op1, op2, op3, op4, ans);
                    quesList.add(currentQ);
                    logger.info(question);
                    logger.info(op1);
                    logger.info(currentQ.getQUESTION());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


        currentQ=quesList.get(qid);
        setQuestionView();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
//                radio0.setEnabled(false);
//                radio1.setEnabled(false);
//                radio2.setEnabled(false);
//                radio3.setEnabled(false);
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(currentQ.getANSWER().equalsIgnoreCase((String) rb.getText())){
                    //rb.setBackgroundColor(Color.GREEN);
                    score++;
                }
                else {
                    //rb.setBackgroundColor(Color.RED);
                }
            }
        });

                    butNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          //  radioGroup.clearCheck();
                            if(qid<quesList.size()){
                                radioGroup=(RadioGroup)findViewById(R.id.radioGroup1);
//                                radio0.setEnabled(true);
//                                radio1.setEnabled(true);
//                                radio2.setEnabled(true);
//                                radio3.setEnabled(true);

//                                radio0.setBackgroundColor(Color.WHITE);
//                                radio1.setBackgroundColor(Color.WHITE);
//                                radio2.setBackgroundColor(Color.WHITE);
//                                radio3.setBackgroundColor(Color.WHITE);

                                radio0.setChecked(false);
                                radio1.setChecked(false);
                                radio2.setChecked(false);
                                radio3.setChecked(false);

                                currentQ=quesList.get(qid);
                                setQuestionView();
                               // RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                        // checkedId is the RadioButton selected
//                                        radio0.setEnabled(false);
//                                        radio1.setEnabled(false);
//                                        radio2.setEnabled(false);
//                                        radio3.setEnabled(false);
                                        RadioButton rb=(RadioButton)findViewById(checkedId);
                                        if(currentQ.getANSWER().equalsIgnoreCase((String) rb.getText())){
                                           // rb.setBackgroundColor(Color.GREEN);
                                            score++;
                                        }
                                        else {
                                           // rb.setBackgroundColor(Color.RED);
                                        }
                                    }
                                });

                            }
                            else {
                                Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                                intent.putExtra("score",score);
                                startActivity(intent);
                            }

                        }
                        });
                }

    private void setQuestionView()
    {

        question_text.setText(currentQ.getQUESTION());
        radio0.setText(currentQ.getOPTA());
        radio1.setText(currentQ.getOPTB());
        radio2.setText(currentQ.getOPTC());
        radio3.setText(currentQ.getOPTD());

        qid++;
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit")
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logger.info(String.valueOf(score));
                        Intent i=new Intent(QuizActivity.this,ResultActivity.class);
                        i.putExtra("score",score);
                        startActivity(i);
                    }
                }).create().show();
    }


}

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

