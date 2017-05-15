package com.example.rahul.learnnfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class QuizActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int attempt=0;
    int qid = 0;
    int pid=0;
    Question currentQ;
    TextView t0,t1,t2,t3;
    Button butNext,butPrev;
    private TextView question_text;
    Logger logger = Logger.getLogger("QuizActivity");
    private String topic_id;
    private String JSON_STRING;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question_text = (TextView) findViewById(R.id.question_text);
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        logger.info(username+"Hello");
        topic_id = intent.getStringExtra(Config.TOPIC_ID);
        setTitle("HI");

        t0 = (TextView) findViewById(R.id.radio0);
        t1 = (TextView) findViewById(R.id.radio1);
        t2 = (TextView) findViewById(R.id.radio2);
        t3 = (TextView) findViewById(R.id.radio3);

        butNext = (Button) findViewById(R.id.button1);
        butPrev=(Button)findViewById(R.id.button2);
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
               // logger.info(s);
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
                currentQ = new Question(question, op1, op2, op3, op4, ans,"null1");
                currentQ.setQUESTION(question);
                currentQ.setOPTA(op1);
                currentQ.setOPTB(op2);
                currentQ.setOPTC(op3);
                currentQ.setOPTD(op4);
                currentQ.setANSWER(ans);
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
        logger.info(currentQ.getOPTA());
        logger.info(currentQ.getOPTB());
        logger.info(currentQ.getOPTC());
        logger.info(currentQ.getOPTD());
    }
    public void onclick(View view){
        logger.info(t0.getText().toString());
        attempt++;
        switch (view.getId()){

            case R.id.radio0:
                currentQ.setResponse(t0.getText().toString());
                if(currentQ.getANSWER().equalsIgnoreCase(t0.getText().toString())){
                    score++;
                    t0.setBackgroundColor(Color.rgb(0,100,0));
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    t0.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                break;

            case R.id.radio1:
                currentQ.setResponse(t1.getText().toString());
                if(currentQ.getANSWER().equalsIgnoreCase(t1.getText().toString())){
                    score++;
                    t1.setBackgroundColor(Color.rgb(0,100,0));
                    t0.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    String s=currentQ.getANSWER();
                    t1.setBackgroundColor(Color.RED);
                    if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.rgb(0,100,0));
                    }
                     else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.rgb(0,100,0));
                    }

                    t0.setClickable(false);
                    t2.setClickable(false);
                    t3.setClickable(false);
                }
                break;

            case R.id.radio2:
                currentQ.setResponse(t2.getText().toString());
                if(currentQ.getANSWER().equalsIgnoreCase(t2.getText().toString())){
                    score++;
                    t2.setBackgroundColor(Color.rgb(0,100,0));
                    t1.setClickable(false);
                    t0.setClickable(false);
                    t3.setClickable(false);
                }
                else{
                    t2.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    t1.setClickable(false);
                    t0.setClickable(false);
                    t3.setClickable(false);
                }
                break;

            case R.id.radio3:
                currentQ.setResponse(t3.getText().toString());
                if(currentQ.getANSWER().equalsIgnoreCase(t3.getText().toString())){
                    score++;
                    t3.setBackgroundColor(Color.rgb(0,100,0));
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t0.setClickable(false);
                }
                else{
                    t3.setBackgroundColor(Color.RED);
                    String s=currentQ.getANSWER();
                    if(s.equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(s.equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    t1.setClickable(false);
                    t2.setClickable(false);
                    t0.setClickable(false);
                }
                break;
            default:
        }
        logger.info(currentQ.getRESPONSE());
    }

    public void next(View v){
        if(qid<quesList.size()){
            if(quesList.get(qid).getRESPONSE().equalsIgnoreCase("null1")){

                t0.setBackgroundColor(Color.WHITE);
                t1.setBackgroundColor(Color.WHITE);
                t2.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t0.setClickable(true);
                t1.setClickable(true);
                t2.setClickable(true);
                t3.setClickable(true);
                currentQ=quesList.get(qid);
                setQuestionView();
            }
            else {
                int nid=qid;
                currentQ=quesList.get(nid);
                setQuestionView();
                t0.setClickable(false);
                t1.setClickable(false);
                t2.setClickable(false);
                t3.setClickable(false);
                if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(quesList.get(nid).getANSWER())){
                    if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.rgb(0,100,0));
                        t0.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t0.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t0.setBackgroundColor(Color.WHITE);
                    }
                }
                else {
                    String ans1=quesList.get(nid).getANSWER();
                    t0.setBackgroundColor(Color.WHITE);
                    t1.setBackgroundColor(Color.WHITE);
                    t2.setBackgroundColor(Color.WHITE);
                    t3.setBackgroundColor(Color.WHITE);

                    if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(nid).getRESPONSE().equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                    }
                }
            }
        }
        else {
            showSummary();
        }
    }

    public void previous(View v){
        pid=pid-1;
        logger.info("Hidfs"+pid);
        butPrev.setEnabled(true );
        if(pid<0)
            butPrev.setEnabled(false);
        else {
            if(quesList.get(pid).getRESPONSE().equalsIgnoreCase("null1")){
                t0.setBackgroundColor(Color.WHITE);
                t1.setBackgroundColor(Color.WHITE);
                t2.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t0.setClickable(true);
                t1.setClickable(true);
                t2.setClickable(true);
                t3.setClickable(true);
                currentQ=quesList.get(pid);
                setPreviousQuestionView();
            }
            else {
                currentQ=quesList.get(pid);
                setPreviousQuestionView();
                t0.setClickable(false);
                t1.setClickable(false);
                t2.setClickable(false);
                t3.setClickable(false);
                logger.info("Rahul");
                if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(quesList.get(pid).getANSWER())){
                    if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.rgb(0,100,0));
                        t0.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t0.setBackgroundColor(Color.WHITE);
                        t3.setBackgroundColor(Color.WHITE);
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.rgb(0,100,0));
                        t1.setBackgroundColor(Color.WHITE);
                        t2.setBackgroundColor(Color.WHITE);
                        t0.setBackgroundColor(Color.WHITE);
                    }
                }
                else {
                    String ans1=quesList.get(pid).getANSWER();
                    t0.setBackgroundColor(Color.WHITE);
                    t1.setBackgroundColor(Color.WHITE);
                    t2.setBackgroundColor(Color.WHITE);
                    t3.setBackgroundColor(Color.WHITE);

                    if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t0.getText().toString())){
                        t0.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t1.getText().toString())){
                        t1.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t2.getText().toString())){
                        t2.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t3.getText().toString()))
                            t3.setBackgroundColor(Color.rgb(0,100,0));
                    }
                    else if(quesList.get(pid).getRESPONSE().equalsIgnoreCase(t3.getText().toString())){
                        t3.setBackgroundColor(Color.RED);
                        if(ans1.equalsIgnoreCase(t1.getText().toString()))
                            t1.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t2.getText().toString()))
                            t2.setBackgroundColor(Color.rgb(0,100,0));
                        else if(ans1.equalsIgnoreCase(t0.getText().toString()))
                            t0.setBackgroundColor(Color.rgb(0,100,0));
                    }
                }
            }
        }
    }

    private void setQuestionView()
    {
        question_text.setText(currentQ.getQUESTION());
        t0.setText(currentQ.getOPTA());
        t1.setText(currentQ.getOPTB());
        t2.setText(currentQ.getOPTC());
        t3.setText(currentQ.getOPTD());
        pid=qid;
        qid++;
        logger.info("QID"+qid);
    }

    private void setPreviousQuestionView()
    {
        question_text.setText(currentQ.getQUESTION());
        t0.setText(currentQ.getOPTA());
        t1.setText(currentQ.getOPTB());
        t2.setText(currentQ.getOPTC());
        t3.setText(currentQ.getOPTD());
        qid--;
        logger.info("PID"+pid);
    }

    public void showSummary(){
       // addScore();
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.summary);
        dialog.setTitle("Quiz Summary");
        TextView totalValue = (TextView) dialog.findViewById(R.id.totalvalue);
        TextView correctValue = (TextView) dialog.findViewById(R.id.correctvalue);
        TextView incorrectValue = (TextView) dialog.findViewById(R.id.incorrectvalue);
        TextView notAttemptValue = (TextView) dialog.findViewById(R.id.notattemptvalue);
        totalValue.setText(quesList.size()+"");
        correctValue.setText(score+"");
        incorrectValue.setText(attempt-score+"");
        notAttemptValue.setText(quesList.size()-attempt+"");

        TextView okButton = (TextView)dialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Thread t=new Thread(){
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            addScore();
//                            Intent i=new Intent(QuizActivity.this,Topic.class);
//                            startActivity(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                t.start();
                dialog.dismiss();
                Intent i=new Intent(QuizActivity.this,Topic.class);
                startActivity(i);
            }
        });
        dialog.show();
    }

    private void addScore(){
        class AddScore extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;


            protected void onPreExecute() {
                super.onPreExecute();
              //  loading = ProgressDialog.show(QuizActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
//                JSON_STRING=s;
//                showQuestion();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> data=new HashMap<>();
                data.put(Config.KEY_ADMIN_USERNAME,username);
                data.put(Config.TOPIC_ID,topic_id);
                data.put("score", String.valueOf(score));
                data.put("attempt", String.valueOf(attempt));
                data.put("incorrect", String.valueOf(attempt-score));

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD_SCORE, data);
                logger.info(username+"GG");
                logger.info(topic_id+"GG");
                logger.info(score+"GG");
                logger.info(attempt+"GG");
                logger.info(res+"qwerty");
                return res;
            }
        }
        AddScore as=new AddScore();
        as.execute();

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
                        showSummary();
                    }
                }).create().show();
    }
}

