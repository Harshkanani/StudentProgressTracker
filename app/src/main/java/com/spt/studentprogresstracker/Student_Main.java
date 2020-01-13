package com.spt.studentprogresstracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public NavigationView navigationView;
    public ActionBarDrawerToggle toggle;
    private TextView txtname,txtemail;
    DatabaseHelper databaseHelper;
    public String filename="com.spt.studentprogresstracker.login";

   /// DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mDatabaseHelper=new DatabaseHelper(Student_Main.this);

            setContentView(R.layout.activity_student__main);

            databaseHelper=new DatabaseHelper(this);

            drawerLayout = findViewById(R.id.drawer);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            navigationView = findViewById(R.id.student_main_navigation_view);

            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Student_Main_Home()).commit();

            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

            getSupportActionBar().setDisplayShowTitleEnabled(true);

            toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);

            drawerLayout.addDrawerListener(toggle);

            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);

            txtname = (TextView) headerView.findViewById(R.id.u_id);

            txtemail = (TextView) headerView.findViewById(R.id.u_email);

            showData();

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.student_main_home:
                    //startActivity(new Intent(Student_Main.this,Student_Main.class));
                    drawerLayout.closeDrawer(Gravity.LEFT,true);
                    break;
            case R.id.student_main_faculty:
                    startActivity(new Intent(Student_Main.this,Student_Main_Faculty.class));
                    drawerLayout.closeDrawer(Gravity.LEFT,true);
                    break;
            case R.id.student_main_setting:
                    startActivity(new Intent(Student_Main.this,Student_Main_Setting.class));
                    drawerLayout.closeDrawer(Gravity.LEFT,true);
                    break;
            case R.id.student_main_send:
                break;
            case R.id.student_main_logout:
                logOut();
                startActivity(new Intent(Student_Main.this, Login_Activity.class));
                finish();
                break;
            case R.id.student_main_aboutus:
                break;

        }

        return false;
    }

    public void logOut() {
        SharedPreferences.Editor editor = getSharedPreferences(filename, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        databaseHelper.deleteStudentSubject();
        databaseHelper.deleteUserInfo();
    }

    public void showData()
    {
        Cursor cursor = databaseHelper.getUserInfo();
        if(cursor.moveToFirst()) {
            txtname.setText(cursor.getString(cursor.getColumnIndex("s_name")));
            txtemail.setText(cursor.getString(cursor.getColumnIndex("s_email_id")));
        }
    }
}
