package com.spt.studentprogresstracker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Main_View_Notice extends Fragment {

    public static final String URL_NOTICE="https://studentprogresstracker.000webhostapp.com/m/notice.php";
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> content=new ArrayList<>();
    TextView txtviewnoticedate,txtviewnoticetitle,txtviewnoticecontent,txtviewnoticename;
    String subject;

    public Student_Main_View_Notice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__main__view__notice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findcontrol();

        if(getArguments()!=null)
        {
            Student_Main_View_NoticeArgs args=Student_Main_View_NoticeArgs.fromBundle(getArguments());

            subject=args.getNoticesub();
        }

        Toast.makeText(getContext(), subject, Toast.LENGTH_SHORT).show();


        showNotice();

    }

    public void findcontrol()
    {
        txtviewnoticedate=getView().findViewById(R.id.txtviewnoticedate);
        txtviewnoticetitle=getView().findViewById(R.id.txtviewnoticetitle);
        txtviewnoticecontent=getView().findViewById(R.id.txtviewnoticecontent);
        txtviewnoticename=getView().findViewById(R.id.txtviewnoticename);
    }

    public void showNotice()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_NOTICE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);

                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject json = jsonArray.getJSONObject(i);
                        date.add(outputFormat.format(inputFormat.parse(json.getString("date_time"))));
                        title.add(json.getString("notice_sub"));
                        if(json.getString("a_name").equals("null"))
                            name.add(json.getString("f_name"));
                        else
                            name.add(json.getString("a_name"));
                        content.add(json.getString("notice_description"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                txtviewnoticedate.setText(date.get(0));
                txtviewnoticetitle.setText(title.get(0));
                txtviewnoticecontent.setText(content.get(0));
                txtviewnoticename.setText(name.get(0));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param= new HashMap<>();
                param.put("nsub","Gtu Exam Fee Related Gtu Exam Fee Related");
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



}
