package com.example.rahul.learnnfun;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class Topic extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;

    private String JSON_STRING;

    Logger logger = Logger.getLogger("Topic");

    static int backButtonCount=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getJSON();
    }


    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String name = jo.getString(Config.TAG_NAME);
                logger.info(id);
                logger.info(name);

                HashMap<String,String> topics = new HashMap<>();
                topics.put(Config.TAG_ID,id);
                topics.put(Config.TAG_NAME,name);
                list.add(topics);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                this, list, R.layout.list_item,
                new String[]{Config.TAG_NAME},
                new int[]{R.id.topic_name});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Topic.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_TOPIC);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, QuizActivity.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        logger.info(String.valueOf(position));
        logger.info(String.valueOf(id));
        String topic_id = map.get(Config.TAG_ID).toString();
        intent.putExtra(Config.TOPIC_ID,topic_id);
        startActivity(intent);
    }

    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}


//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.util.logging.Logger;
//
//public class Topic extends AppCompatActivity {
//
//    public static final String JSON_URL = "http://learnnfun.16mb.com/encode.php";
//        Logger logger = Logger.getLogger("PreviousActivity");
//
//    private Intent intent;
//
//    private ListView listView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_topic);
//
//        logger.info("asdds"+listView);
//        listView = (ListView) findViewById(R.id.list);
//        sendRequest();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                logger.info(id+"");
//                long ll=listView.getItemIdAtPosition(position);
//                logger.info(String.valueOf(ll)+"Hello");
//                logger.info(position+""+"dsfsf");
//                Log.v( "List2", "Clicked");
//                int selectedFromList = (Integer.parseInt((String) listView.getItemAtPosition(position))) ;
//                logger.info(Integer.toString(selectedFromList)+"qwer");
//                intent = new Intent(PreviousActivity.this, QuizActivity.class);
//                intent.putExtra("sentIntent", ll);
//                startActivity(intent);
//
//            }
//        });
//    }
//
//    private void sendRequest(){
//
//        StringRequest stringRequest = new StringRequest(JSON_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {logger.info(response+"rahul");
//
//                        showJSON(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(PreviousActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }
//
//    private void showJSON(String json){
//        ParseJSON pj = new ParseJSON(json);
//        pj.parseJSON();
//        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names);
//        listView.setAdapter(cl);
//        logger.info(listView+"");
//    }
//
//}