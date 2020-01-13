package com.spt.studentprogresstracker;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Student_Main_Result extends Fragment {

    public static final String  URL_MARKS="https://studentprogresstracker.000webhostapp.com/m/fetch_marks.php";

    ListView listView;
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> marks = new ArrayList<String>();
    private ArrayList<String> status = new ArrayList<String>();
    List<Result> listresult;
    ResultAdapter adapter;


    public Student_Main_Result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__main__result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControll();

        if(getArguments()!=null)
        {
            Student_Main_Attandance1Args args=Student_Main_Attandance1Args.fromBundle(getArguments());

            Toast.makeText(getContext(), args.getSubcode(), Toast.LENGTH_SHORT).show();
        }

        new showAttandance().execute();
    }

    public void findControll()
    {
        listView=getView().findViewById(R.id.listmarks);
        listresult=new ArrayList<>();
    }

    class showAttandance extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_MARKS, new Response.Listener<String>() {
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
                            title.add(json.getString("e_type"));
                            marks.add(json.getString("marks")+"/"+json.getString("total_marks"));
                            status.add(json.getString("status"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<date.size();i++)
                    {
                        Result result=new Result(date.get(i),title.get(i),marks.get(i),status.get(i));

                        listresult.add(result);
                    }

                    adapter=new ResultAdapter(getContext(),listresult)
                    {
                        public boolean areAllItemsEnabled()
                        {
                            return false;
                        }
                        public boolean isEnabled(int position)
                        {
                            return false;
                        }
                    };

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
                    param.put("username","176120316001");
                    param.put("subcode","3361601");
                    return param;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

            return null;
        }
    }
}
