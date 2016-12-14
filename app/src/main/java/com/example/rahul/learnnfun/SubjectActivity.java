package com.example.rahul.learnnfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
    }
    public void onClick(View view){
        Intent i=new Intent(this,QuizActivity.class);
        startActivity(i);
    }
}
