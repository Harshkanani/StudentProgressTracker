package com.spt.studentprogresstracker;


import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
public class Student_Main_Notice extends Fragment {

    public static final String URL_NOTICE="https://studentprogresstracker.000webhostapp.com/m/notice.php";
    ListView listView;
    DatabaseHelper databaseHelper;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<>();
    List<Notice> listNotice;
    NoticeAdapter adapter;
    String sem,cid;

    public Student_Main_Notice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__main__notice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((Student_Main) getActivity()).getSupportActionBar().hide();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findcontrol();

        showNotice();

        listviewSelectItem();

    }

    public void showNotice()
    {
        Cursor cursor = databaseHelper.getUserInfo();

        if (cursor.moveToFirst()) {
            sem=cursor.getString(cursor.getColumnIndex("s_sem"));
            cid=cursor.getString(cursor.getColumnIndex("c_id"));
        }

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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int i=0;i<date.size();i++)
                {
                    Notice notice=new Notice(title.get(i),date.get(i),name.get(i));

                    listNotice.add(notice);
                }

                adapter=new NoticeAdapter(getContext(),listNotice);

                listView.setAdapter(adapter);
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
                param.put("nsem",sem);
                param.put("ncid",cid);
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void findcontrol()
    {
        listView=getView().findViewById(R.id.listnotice);
        databaseHelper=new DatabaseHelper(getActivity());
        listNotice=new ArrayList<>();
    }

    public void listviewSelectItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student_Main_NoticeDirections.ActionStudentMainNoticeToStudentMainViewNotice action=Student_Main_NoticeDirections.actionStudentMainNoticeToStudentMainViewNotice();

                action.setNoticesub(listView.getItemAtPosition(i).toString());

                Navigation.findNavController(view).navigate(action);

            }
        });
    }
}
