package com.spt.studentprogresstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Student_Main_View_Profile extends AppCompatActivity {

    TextView txtname,txtemail,txtphone,txtdep;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__main__view__profile);

        getSupportActionBar().setTitle("Profile");

        findconrol();

        showDetail();

    }

    public void findconrol()
    {
        txtname=findViewById(R.id.name);
        txtemail=findViewById(R.id.email);
        txtphone=findViewById(R.id.phone);
        txtdep=findViewById(R.id.department);
        databaseHelper=new DatabaseHelper(this);
    }

    public void showDetail()
    {
        Cursor cursor=databaseHelper.getUserInfo();
        if(cursor.moveToFirst())
        {
            txtname.setText(cursor.getString(cursor.getColumnIndex("s_name")));
            txtemail.setText(cursor.getString(cursor.getColumnIndex("s_email_id")));
            txtphone.setText(cursor.getString(cursor.getColumnIndex("s_phone_no")));
            txtdep.setText(cursor.getString(cursor.getColumnIndex("c_name")));
        }
    }
}
