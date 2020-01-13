package com.spt.studentprogresstracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Main_Attandance1 extends Fragment {

    public String filename="com.spt.studentprogresstracker.login";
    public static final String  URL_Attandance="https://studentprogresstracker.000webhostapp.com/m/fetch_attandance.php";
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> status = new ArrayList<String>();
    private ArrayList<String> type = new ArrayList<String>();
    List<Attandance> listattandance;
    ListView listviewattandance;
    TextView txtlecturepresent,txtlectureabsent,txtlabpresent;
    TextView txtlababsent,txttutorialpresent,txttutorialabsent;
    TextView txtlecturepercentage,txtlabpercentage,txttutorialpercentage,txttotalpercentage;
    AttandanceAdapter adapter;
    public int totallabpresent=0;
    public int totallababsent=0;
    public int totallecturepresent=0;
    public int totallectureabsent=0;
    public int totaltutorialpresent=0;
    public int totaltutorialabsent=0;
    public int countlec=0,countlab=0,counttuto=0;
    public int totalpercentage=0;
    CircularProgressBar progressBar;

    public Student_Main_Attandance1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__main__attandance1, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findcontrol();

        if(getArguments()!=null)
        {
            Student_Main_Attandance1Args args=Student_Main_Attandance1Args.fromBundle(getArguments());

            Toast.makeText(getContext(), args.getSubcode(), Toast.LENGTH_SHORT).show();
        }

        new showAttandance().execute();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((Student_Main) getActivity()).getSupportActionBar().hide();
        super.onActivityCreated(savedInstanceState);
    }

    class showAttandance extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Attandance, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONArray jsonArray=new JSONArray(response);

                        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject json = jsonArray.getJSONObject(i);
                            date.add(outputFormat.format(inputFormat.parse(json.getString("date"))));
                            status.add(json.getString("status"));
                            type.add(json.getString("type"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<type.size();i++)
                    {
                        if(type.get(i).equals("Lecture"))
                            countlec+=1;
                        if(type.get(i).equals("Lab"))
                            countlab+=1;
                        if(type.get(i).equals("Tutorial"))
                            counttuto+=1;
                    }

                    for(int i=0;i<status.size();i++) {

                        if (type.get(i).equals("Lecture")) {
                            if (status.get(i).equals("Present"))
                                totallecturepresent += 1;
                            if (status.get(i).equals("Absent"))
                                totallectureabsent += 1;
                        }

                        if (type.get(i).equals("Lab")) {
                            if (status.get(i).equals("Present"))
                                totallabpresent += 1;
                            if (status.get(i).equals("Absent"))
                                totallababsent += 1;
                        }

                        if (type.get(i).equals("Tutorial")) {
                            if (status.get(i).equals("Present"))
                                totaltutorialpresent += 1;
                            if (status.get(i).equals("Absent"))
                                totaltutorialabsent += 1;
                        }
                    }

                    txtlecturepercentage.setText(String.valueOf((totallecturepresent*100)/countlec)+"%");
                    txtlabpercentage.setText(String.valueOf((totallabpresent*100)/countlab)+"%");
                    txttutorialpercentage.setText(String.valueOf((totaltutorialpresent*100) /counttuto)+"%");

                    totalpercentage=((totallecturepresent*100)/countlec +
                                     (totallabpresent*100)/countlab +
                                     (totaltutorialpresent*100)/counttuto)/3 ;

                    progressBar.setProgress(Float.parseFloat(String.valueOf(totalpercentage)));
                    txttotalpercentage.setText(String.valueOf(totalpercentage)+"%");

                    txtlecturepresent.setText(String.valueOf(totallecturepresent));
                    txtlectureabsent.setText(String.valueOf(totallectureabsent));

                    txtlababsent.setText(String.valueOf(totallababsent));
                    txtlabpresent.setText(String.valueOf(totallabpresent));

                    txttutorialpresent.setText(String.valueOf(totaltutorialpresent));
                    txttutorialabsent.setText(String.valueOf(totaltutorialabsent));

                    for(int i=0;i<date.size();i++)
                    {
                        Attandance att=new Attandance(date.get(i),status.get(i),type.get(i));

                        listattandance.add(att);
                    }

                    adapter=new AttandanceAdapter(getContext(),listattandance){
                        public boolean areAllItemsEnabled()
                        {
                            return false;
                        }
                        public boolean isEnabled(int position)
                        {
                            return false;
                        }
                    };

                    listviewattandance.setAdapter(adapter);
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
            RequestQueue requestQueue=Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

            return null;
        }
    }

    public void findcontrol()
    {
        listviewattandance=getView().findViewById(R.id.listviewshowattandance);
        listattandance=new ArrayList<>();

        txtlecturepresent=getView().findViewById(R.id.txtlecturepresent);
        txtlectureabsent=getView().findViewById(R.id.txtlectureabsent);

        txtlabpresent=getView().findViewById(R.id.txtLabpresent);
        txtlababsent=getView().findViewById(R.id.txtlababsent);

        txttutorialpresent=getView().findViewById(R.id.txttutorialpresent);
        txttutorialabsent=getView().findViewById(R.id.txttutorialabsent);

        txtlecturepercentage=getView().findViewById(R.id.txtlecturepercentage);
        txtlabpercentage=getView().findViewById(R.id.txtlabpercentage);
        txttutorialpercentage=getView().findViewById(R.id.txttutorialpercentage);
        txttotalpercentage=getView().findViewById(R.id.txtProgress);
        progressBar=getView().findViewById(R.id.progress_bar);
    }

}

