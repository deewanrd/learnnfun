package com.example.rahul.learnnfun;

/**
 * Created by Belal on 10/24/2015.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://learnnfun.16mb.com/register.php";
    public static final String URL_GET_TOPIC="http://learnnfun.16mb.com/topic.php";
    public static final String ADMIN_LOGIN_URL = "http://learnnfun.16mb.com/login.php";
    public static final String URL_GET_QUESTION="http://learnnfun.16mb.com/question.php?id=";
    public static final String URL_ADD_SCORE="http://learnnfun.16mb.com/addScore.php";
    public static final String URL_GET_SCORE="http://learnnfun.16mb.com/profile.php?username=";



    //Keys that will be used to send the request to php scripts
    public static final String KEY_ADMIN_USERNAME = "username";
    public static final String KEY_ADMIN_NAME = "name";
    public static final String KEY_ADMIN_EMAIL = "email";
    public static final String KEY_ADMIN_PASSWORD = "password";
    public static final String KEY_ADMIN_USER_ID = "user_id";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_QUESTION = "text";
    public static final String TAG_OP1 = "op1";
    public static final String TAG_OP2 = "op2";
    public static final String TAG_OP3 = "op3";
    public static final String TAG_OP4 = "op4";
    public static final String TAG_ANS = "ans";

    public static final String TAG_SCORE="score";
    public static final String TAG_ATTEMPTED="attempt";
    public static final String TAG_INCORRECT="incorrect";

    //employee id to pass with intent
    public static final String TOPIC_ID = "topic_id";


    public static final String LOGIN_SUCCESS = "success";
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

}