package com.example.rahul.learnnfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class QuizActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    RadioButton radio0,radio1,radio2,radio3;
    TextView t0,t1,t2,t3;
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
       /* radio0 = (RadioButton) findViewById(R.id.radio0);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);*/

        t0 = (TextView) findViewById(R.id.radio0);
        t1 = (TextView) findViewById(R.id.radio1);
        t2 = (TextView) findViewById(R.id.radio2);
        t3 = (TextView) findViewById(R.id.radio3);

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


    private void showQuestion() {
        JSONObject jsonObject = null;
        quesList = new ArrayList<Question>();
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        currentQ = quesList.get(qid);
        setQuestionView();
    }
    public void onclick(View view){
        logger.info(t0.getText().toString());
        switch (view.getId()){
            case R.id.radio0:
                if(currentQ.getANSWER().equalsIgnoreCase(t0.getText().toString())){
                    score++;
                    t0.setBackgroundColor(Color.GREEN);
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    t0.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.GREEN);
                    }
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                break;
            case R.id.radio1:
                if(currentQ.getANSWER().equalsIgnoreCase(t1.getText().toString())){
                    score++;
                    t1.setBackgroundColor(Color.GREEN);
                    t0.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    String s=currentQ.getANSWER();
                    t1.setBackgroundColor(Color.RED);
                    if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.GREEN);
                    }

                    t0.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                break;

            case R.id.radio2:
                if(currentQ.getANSWER().equalsIgnoreCase(t2.getText().toString())){
                    score++;
                    t2.setBackgroundColor(Color.GREEN);
                    t1.setClickable(false);
                    t0.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    t2.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.GREEN);
                    }
                    t1.setClickable(false);
                    t0.setClickable(false);
                    t3.setClickable(false);
                }
                break;

            case R.id.radio3:
                if(currentQ.getANSWER().equalsIgnoreCase(t3.getText().toString())){
                    score++;
                    t3.setBackgroundColor(Color.GREEN);
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t0.setClickable(false);
                }
                else{
                    t3.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.GREEN);
                    }
                    else if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.GREEN);
                    }
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t0.setClickable(false);
                }
                break;
        }
    }

    public void next(View v){
        t0.setBackgroundColor(Color.WHITE);
        t1.setBackgroundColor(Color.WHITE);
        t2.setBackgroundColor(Color.WHITE);
        t3.setBackgroundColor(Color.WHITE);
        t0.setClickable(true);
        t1.setClickable(true);
        t2.setClickable(true);
        t3.setClickable(true);
        if(qid<quesList.size()){
            currentQ=quesList.get(qid);
            setQuestionView();
        }
        else {
            Intent i=new Intent(QuizActivity.this,ResultActivity.class);
            i.putExtra("score",score);
            startActivity(i);
        }
    }

// *************************************************************************************

    private void setQuestionView()
    {

        question_text.setText(currentQ.getQUESTION());
        t0.setText(currentQ.getOPTA());
        t1.setText(currentQ.getOPTB());
        t2.setText(currentQ.getOPTC());
        t3.setText(currentQ.getOPTD());

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

