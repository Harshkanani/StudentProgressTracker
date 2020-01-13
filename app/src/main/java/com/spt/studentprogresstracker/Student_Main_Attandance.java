package com.spt.studentprogresstracker;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Main_Attandance extends Fragment {

//    public static final String  URL="https://studentprogresstracker.000webhostapp.com/m/fetch_subject.php";

    ListView listView;
    DatabaseHelper databaseHelper;
    private ArrayList<String> subcode = new ArrayList<String>();
    private ArrayList<String> subname = new ArrayList<String>();
    List<Subject> listsubject;
    String value;



    public Student_Main_Attandance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student__main__attandance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControll();

        showSubject();

        listViewSelectItem();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((Student_Main) getActivity()).getSupportActionBar().hide();
        super.onActivityCreated(savedInstanceState);
    }

    public void showSubject()
    {

        Cursor cursor = databaseHelper.getStudentSubject();

        if (cursor.moveToFirst()) {

            do {
                subcode.add(cursor.getString(cursor.getColumnIndex("subcode")));

                subname.add(cursor.getString(cursor.getColumnIndex("subname")));

            } while (cursor.moveToNext());

        }

        for(int i=0;i<subcode.size();i++)
        {
            Subject sub=new Subject(subcode.get(i),subname.get(i));
            listsubject.add(sub);
        }

        SubjectAdapter adapter = new SubjectAdapter(getContext(), listsubject);

        listView.setAdapter(adapter);

    }

    public void findControll()
    {
        listView=getView().findViewById(R.id.subListview);
        listsubject=new ArrayList<>();
        databaseHelper=new DatabaseHelper(getActivity());
    }

    public  void listViewSelectItem()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s = listView.getItemAtPosition(i+1).toString();

                if(getArguments()!=null)
                {
                    Student_Main_AttandanceArgs args=Student_Main_AttandanceArgs.fromBundle(getArguments());

                    value=args.getClickCheck();
                }

                if(value.equals("Attandance")) {

                    Student_Main_AttandanceDirections.ActionStudentMainAttandanceToStudentMainAttandance1 action = Student_Main_AttandanceDirections.actionStudentMainAttandanceToStudentMainAttandance1();

                    action.setSubcode(s);

                    Navigation.findNavController(view).navigate(action);
                }
                else if(value.equals("Result"))
                {
                    Student_Main_AttandanceDirections.ActionStudentMainAttandanceToStudentMainResult action=Student_Main_AttandanceDirections.actionStudentMainAttandanceToStudentMainResult();

                    action.setSubcode(s);

                    Navigation.findNavController(view).navigate(action);
                }
            }
        });
    }

}
