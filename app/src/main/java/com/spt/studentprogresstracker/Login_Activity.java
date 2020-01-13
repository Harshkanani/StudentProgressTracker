package com.spt.studentprogresstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {

    private EditText edittextUsername,edittextPassword;
    private Button buttonLogin;
    public String filename="com.spt.studentprogresstracker.login";
    public static final String URL_LOGIN="https://studentprogresstracker.000webhostapp.com/m/Loginuser.php";
    public static final String URL_SUBJECT="https://studentprogresstracker.000webhostapp.com/m/fetch_subject.php";
    public static final String URL_USER="https://studentprogresstracker.000webhostapp.com/m/fetch_userinfo.php";
    ProgressDialog progressDialog;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        mDatabaseHelper=new DatabaseHelper(Login_Activity.this);

        if(netInfo!=null && netInfo.isConnectedOrConnecting()) {

            SharedPreferences prefs = getSharedPreferences(filename, MODE_PRIVATE);
            Boolean login = prefs.getBoolean("login", false);

            if (login != true) {

                edittextUsername = (EditText) findViewById(R.id.username);
                edittextPassword = (EditText) findViewById(R.id.password);
                buttonLogin = (Button) findViewById(R.id.login);

                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String username = edittextUsername.getText().toString().trim();
                        final String password = edittextPassword.getText().toString().trim();

                        if (username.isEmpty()) {
                            edittextUsername.setError("Empty");
                            return;
                        }

                        if (password.isEmpty()) {
                            edittextPassword.setError("Empty");
                            return;
                        }

                        progressDialog = new ProgressDialog(Login_Activity.this);
                        progressDialog.setMessage("validating data..."); // Setting Message
                        progressDialog.setTitle("Login"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(5000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();

                        check(username,password);
                    }
                });
            }
            else
                startActivity(new Intent(Login_Activity.this,Student_Main.class));
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Connection").setMessage("No Internet Connection").setNegativeButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    public void check(final String username, final String password)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if(success.equals("admin")) {
                        storeInShared(username,password);
                    }
                    else if(success.equals("faculty")){
                        storeInShared(username,password);
                    }
                    else if(success.equals("student")){
                        storeInShared(username,"student");
                        storeStudentSubject(username);
                        storeUserInfo(username);
                        progressDialog.dismiss();
                        startActivity(new Intent(Login_Activity.this,Student_Main.class));
                        finish();
                    }
                    else if(success.equals("false")) {
                        progressDialog.dismiss();
                        Toast.makeText(Login_Activity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    else if(success.equals("incorrectuser")){
                        progressDialog.dismiss();
                        Toast.makeText(Login_Activity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                    else if(success.equals("incorrectrequest")){
                        progressDialog.dismiss();
                        Toast.makeText(Login_Activity.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                return param;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void storeInShared(String username,String type)
    {
        SharedPreferences.Editor editor = getSharedPreferences(filename, MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("type",type);
        editor.putBoolean("login", true);
        editor.apply();
    }

    public void storeStudentSubject(final String username)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_SUBJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    String[] subcode=new String[jsonArray.length()];
                    String[] subname=new String[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        subcode[i]=obj.getString("sub_code");
                        subname[i]=obj.getString("sub_name");
                    }
                    mDatabaseHelper.addStudentSubject(subcode,subname);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login_Activity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",username);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void storeUserInfo(final String username)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    mDatabaseHelper.addUserInfo(jsonObject.getString("s_enrollment"),
                                                jsonObject.getString("s_name"),
                                                jsonObject.getString("s_type"),
                                                jsonObject.getString("s_gender"),
                                                jsonObject.getString("s_email_id"),
                                                jsonObject.getString("s_phone_no"),
                                                jsonObject.getString("s_batch"),
                                                jsonObject.getString("s_sem"),
                                                jsonObject.getString("s_photo"),
                                                jsonObject.getString("c_id"),
                                                jsonObject.getString("c_name"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Login_Activity.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("username",username);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

