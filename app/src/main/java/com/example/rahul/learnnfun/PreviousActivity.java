//package com.example.rahul.learnnfun;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.StringTokenizer;
//import java.util.logging.Logger;
//
//public class PreviousActivity extends AppCompatActivity {
//    Logger logger = Logger.getLogger("foo");
//
//    String myJSON = "{}";
//    private static final String TAG_RESULTS="result";
//    private static final String TAG_ID = "id";
//    private static final String TAG_NAME = "name";
//    //private static final String TAG_ADD ="address";
//
//    JSONArray peoples = null;
//    ArrayList<HashMap<String, String>> personList;
//    ListView list;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_previous);
//        list = (ListView) findViewById(R.id.list);
//        personList = new ArrayList<HashMap<String,String>>();
//        getData();
//        }
//         protected void showList(){
//        try {
//            logger.info("hi");
//            JSONObject jsonObj = new JSONObject(myJSON);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);
//            logger.info(peoples.toString());
//            for(int i=0;i<peoples.length();i++){
//                JSONObject elem = peoples.getJSONObject(i);
//                String name=elem.getString(TAG_NAME);
//                int id=elem.getInt(TAG_ID);
//               // String id = c.getString(TAG_ID);
//                //String address = c.getString(TAG_ADD);
//
//                HashMap<String,String> persons = new HashMap<String,String>();
//                persons.put(TAG_ID,Integer.toString(id));
//                persons.put(TAG_NAME,name);
//                //persons.put(TAG_ADD,address);
//                logger.info(persons.toString());
//                personList.add(persons);
//                }
//
//            ListAdapter adapter = new SimpleAdapter(PreviousActivity.this, personList, R.layout.list_item,
//                                        new String[]{TAG_NAME}, new int[]{ R.id.name});
//
//            list.setAdapter(adapter);
//
//            } catch (JSONException e) {
//            e.printStackTrace();
//            }
//             list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                 @Override
//                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                     try {
//                         logger.info(peoples.getString(position));
//                         Intent i=new Intent(PreviousActivity.this,QuizActivity.class);
//                         i.putExtra("subject",peoples.getJSONObject(position).getInt(TAG_ID));
//                         startActivity(i);
//                     } catch (JSONException e) {
//                         e.printStackTrace();
//                     }
//                 }
//             });
//            // logger.info(String.valueOf(list.getCount()));
//        }
//
//            public void getData(){
//        class GetDataJSON extends AsyncTask<String, Void, String>{
//
//             @Override
//            protected String doInBackground(String... params) {
//               DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//                HttpPost httppost = new HttpPost("http://learnnfun.16mb.com/encode.php");
//                 //HttpPost httppost = new HttpPost("localhost/UserRegistration/encode.php");
//
//
//                 // Depends on your web service
//                httppost.setHeader("Content-type", "application/json");
//
//                InputStream inputStream = null;
//                String result = null;
//                try {
//                    HttpResponse response = httpclient.execute(httppost);
//                    HttpEntity entity = response.getEntity();
//
//                    inputStream = entity.getContent();
//                    // json is UTF-8 by default
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//                    StringBuilder sb = new StringBuilder();
//
//                    String line = null;
//                    while ((line = reader.readLine()) != null)
//                    {
//                        sb.append(line + "\n");
//                        }
//                    result = sb.toString();
//                    } catch (Exception e) {
//                    // Oops
//                    }
//                finally {
//                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
//                    }
//                return result;
//                }
//
//                    @Override
//            protected void onPostExecute(String result){
//                myJSON=result;
//                showList();
//                }
//            }
//        GetDataJSON g = new GetDataJSON();
//        g.execute();
//        }
//
//           // @Override
//  /*  public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//        }
//
//            @Override
//   public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatementif (id == R.id.action_settings) {
//            return true;
//            }
//
//        return super.onOptionsItemSelected(item);
//        }*/
//}


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


    private ProgressDialog pDialog;
    private Intent intent;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);

        ProgressDialog pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

//        buttonGet = (Button) findViewById(R.id.buttonGet);
//        buttonGet.setOnClickListener(this);
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

                //   Toast.makeText(PreviousActivity.this, getBaseContext().getResources().getText(position),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidePDialog();
                        logger.info(response+"rahul");

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
//        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails);
        CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names);
        listView.setAdapter(cl);
        logger.info(listView+"");
    }
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }



//    @Override
//    public void onClick(View v) {
//        sendRequest();
//    }
}