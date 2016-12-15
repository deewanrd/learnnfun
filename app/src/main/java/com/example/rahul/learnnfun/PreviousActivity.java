package com.example.rahul.learnnfun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.logging.Logger;

public class PreviousActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://learnnfun.16mb.com/encode.php";
        Logger logger = Logger.getLogger("PreviousActivity");

    private Intent intent;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);

        logger.info("asdds"+listView);
        listView = (ListView) findViewById(R.id.list);
        sendRequest();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                logger.info(id+"");
                long ll=listView.getItemIdAtPosition(position);
                logger.info(String.valueOf(ll)+"Hello");
                logger.info(position+""+"dsfsf");
                Log.v( "List2", "Clicked");
                int selectedFromList = (Integer.parseInt((String) listView.getItemAtPosition(position))) ;
                logger.info(Integer.toString(selectedFromList)+"qwer");
                intent = new Intent(PreviousActivity.this, QuizActivity.class);
                intent.putExtra("sentIntent", ll);
                startActivity(intent);

            }
        });
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {logger.info(response+"rahul");

                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PreviousActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names);
        listView.setAdapter(cl);
        logger.info(listView+"");
    }

}