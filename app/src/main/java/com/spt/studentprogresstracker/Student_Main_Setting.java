package com.spt.studentprogresstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_Main_Setting extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    CardView cardViewprofile;
    TextView txtname,txtemail;
    EditText editTextcurrentpass,editTextnewpass,editTextconformpass;
    Button btnsave;
    String username,currepass,newpass,conpass;
    ProgressDialog progressDialog;
    public static final String URL_CHECK="https://studentprogresstracker.000webhostapp.com/m/checkPass.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__main__setting);

        getSupportActionBar().setTitle("Setting");

        findcontrol();

        getvalue();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currepass.isEmpty()) {
                    editTextcurrentpass.setError("Empty");
                    return;
                }

                if(newpass.isEmpty())
                {
                    editTextnewpass.setError("Empty");
                    return;
                }

                if(conpass.isEmpty())
                {
                    editTextconformpass.setError("Empty");
                    return;
                }

                if(newpass.equals(conpass)!=true)
                {
                    editTextconformpass.setError("password not match");
                    return;
                }

                progressDialog = new ProgressDialog(Student_Main_Setting.this);
                progressDialog.setMessage("validating data..."); // Setting Message
                progressDialog.setTitle("Changing..."); // Setting Title
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

                changepass();
            }
        });

        showdata();

    }

    public void findcontrol()
    {
        databaseHelper=new DatabaseHelper(this);
        cardViewprofile=findViewById(R.id.cardviewprofile);
        txtname=findViewById(R.id.profilename);
        txtemail=findViewById(R.id.profileemail);
        editTextcurrentpass =findViewById(R.id.currentpass);
        editTextnewpass=findViewById(R.id.newpass);
        editTextconformpass=findViewById(R.id.confompass);
        btnsave=findViewById(R.id.btnsavechange);
    }

    public void getvalue()
    {
        currepass=editTextcurrentpass.getText().toString().trim();
        newpass=editTextnewpass.getText().toString().trim();
        conpass=editTextconformpass.getText().toString().trim();
    }

    public void changepass()
    {
        Cursor cursor=databaseHelper.getUserInfo();
        if(cursor.moveToFirst()){
            username=cursor.getString(cursor.getColumnIndex("s_enrollment"));
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String password = jsonObject.getString("status");
                    if(password.equals("true")) {
                        progressDialog.dismiss();
                        Toast.makeText(Student_Main_Setting.this, "password change successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Student_Main_Setting.this,Login_Activity.class));
                        finish();
                        Student_Main obj=new Student_Main();
                        obj.finish();
                    }
                    else if(password.equals("false")){
                        progressDialog.dismiss();
                        Toast.makeText(Student_Main_Setting.this, "Current password is wrong", Toast.LENGTH_SHORT).show();
                    }
                    else if(password.equals("incorrectrequest")){
                        progressDialog.dismiss();
                        Toast.makeText(Student_Main_Setting.this, "Invalid Request", Toast.LENGTH_SHORT).show();
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
                param.put("currentpass",currepass);
                param.put("newpass",newpass);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showdata()
    {
        Cursor cursor=databaseHelper.getUserInfo();
        if(cursor.moveToFirst())
        {
            txtname.setText(cursor.getString(cursor.getColumnIndex("s_name")));
            txtemail.setText(cursor.getString(cursor.getColumnIndex("s_email_id")));
        }
    }

    public void viewProfile(View view)
    {
        startActivity(new Intent(Student_Main_Setting.this,Student_Main_View_Profile.class));
    }
}
