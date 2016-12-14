package com.example.rahul.learnnfun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Belal on 9/22/2015.
 */
public class ParseJSON {
    public static String[] ids;
    public static String[] names;
//    public static String[] emails;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
//    public static final String KEY_EMAIL = "email";
    Logger logger=Logger.getLogger("ParseJSON");

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
        System.out.print(json+"Deewan");
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            logger.info(users+"DEEWAN");

            ids = new String[users.length()];
            names = new String[users.length()];

            logger.info(Arrays.toString(ids) +"DEEWAN");
            logger.info(Arrays.toString(names) +"DEEWAN");
//            emails = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                names[i] = jo.getString(KEY_NAME);
                logger.info(Arrays.toString(ids) +"DEEWAN");
                logger.info(Arrays.toString(names) +"DEEWAN");

//                emails[i] = jo.getString(KEY_EMAIL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
