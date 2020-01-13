package com.spt.studentprogresstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_Main_Faculty extends AppCompatActivity {

    CircleImageView imageView;
    TextView txtfname,txtfemail;
    public static final String URL_FACULTY="https://studentprogresstracker.000webhostapp.com/m/fetch_faculty.php";
    private ArrayList<String> fname = new ArrayList<String>();
    private ArrayList<String> femail = new ArrayList<String>();
    private ArrayList<String> fsub = new ArrayList<String>();
    List<Faculty> listfaculty;
    ListView listviewfaculty;
    FacultyAdapter adapter;
    DatabaseHelper databaseHelper;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__main__faculty);

        getSupportActionBar().setTitle("Faculty");

        findcontrol();

        getUsername();

        showDetail();



    }

    public void findcontrol()
    {
        imageView=findViewById(R.id.fphoto);
        txtfname=findViewById(R.id.fname);
        txtfemail=findViewById(R.id.femail);
        listfaculty=new ArrayList<>();
        listviewfaculty=findViewById(R.id.listviewfaculty);
        databaseHelper=new DatabaseHelper(this);
    }

    public void getUsername()
    {
        Cursor cursor=databaseHelper.getUserInfo();

        if (cursor.moveToFirst()) {
            username=cursor.getString(cursor.getColumnIndex("s_enrollment"));
        }
    }

    public void showDetail()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_FACULTY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject obj=jsonArray.getJSONObject(i);
                        fname.add(obj.getString("f_name"));
                        femail.add(obj.getString("f_email_id"));
                        fsub.add(obj.getString("sub_name"));
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Student_Main_Faculty.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

                for(int i=0;i<fname.size();i++)
                {
                    Faculty fac=new Faculty(fname.get(i),femail.get(i),fsub.get(i));

                    listfaculty.add(fac);
                }

                adapter=new FacultyAdapter(getApplicationContext(),listfaculty){
                    public boolean areAllItemsEnabled()
                    {
                        return false;
                    }
                    public boolean isEnabled(int position)
                    {
                        return false;
                    }
                };

                listviewfaculty.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Student_Main_Faculty.this, "Error: "+error.toString(), Toast.LENGTH_SHORT).show();
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
