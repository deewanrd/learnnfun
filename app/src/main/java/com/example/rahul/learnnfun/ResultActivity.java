package com.example.rahul.learnnfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.logging.Logger;

public class ResultActivity extends Activity {

        TextView t;
        TextView t1;
        TextView t2;
    Logger logger=Logger.getLogger("ResultActivity");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//get rating bar object
        //RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
//get text view
        t = (TextView) findViewById(R.id.textResult);
        t1 = (TextView) findViewById(R.id.atmpt);
//get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        int atmp = b.getInt("atmp");
        t1.setText(atmp+"");
        float accurate=0;
        accurate=score;
        accurate = (accurate/atmp)*100;
        int act;
        act = (int) accurate;
        t2 = (TextView)findViewById(R.id.accurrate);
        t2.setText(act+"");
        logger.info(String.valueOf(score));
        //t.setText(score);
//display score
      //  bar.setRating(score);

        t.setText(score+"");
    }
//        switch (score) {
//            case 1:
//            case 2:
//                t.setText("Oopsie! Better Luck Next Time!");
//                break;
//            case 3:
//            case 4:
//                t.setText("Hmmmm.. Someone's been reading a lot of trivia");
//                break;
//            case 5:
//                t.setText("Who are you? A trivia wizard???");
//                break;
//        }
//    }

    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Another One!")
                .setMessage("Ready for another quiz!")
                .setNegativeButton("No",null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(ResultActivity.this,Topic.class);
                        startActivity(i);
                    }
                }).create().show();
    }

}
