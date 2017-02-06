package com.example.rahul.learnnfun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.logging.Logger;

public class ResultActivity extends Activity {

    Logger logger=Logger.getLogger("ResultActivity");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//get rating bar object
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
//get text view
        TextView t = (TextView) findViewById(R.id.textResult);
//get score
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        logger.info(String.valueOf(score));
        //t.setText(score);
//display score
        bar.setRating(score);
        switch (score) {
            case 1:
            case 2:
                t.setText("Oopsie! Better Luck Next Time!");
                break;
            case 3:
            case 4:
                t.setText("Hmmmm.. Someone's been reading a lot of trivia");
                break;
            case 5:
                t.setText("Who are you? A trivia wizard???");
                break;
        }
    }

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
