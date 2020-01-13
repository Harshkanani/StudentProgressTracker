package com.spt.studentprogresstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FacultyAdapter extends ArrayAdapter<Faculty> {

    public List<Faculty> facultyList;
    public Context mcontext;

    public FacultyAdapter(Context c, List<Faculty> faculty)
    {
        super(c,R.layout.faculty_listview_row,faculty);
        this.facultyList=faculty;
        this.mcontext=c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View view=inflater.inflate(R.layout.faculty_listview_row,null,true);

        TextView txtfname=(TextView)view.findViewById(R.id.fname);
        TextView txtfemail=(TextView)view.findViewById(R.id.femail);
        TextView txtfsub=(TextView)view.findViewById(R.id.fsub);

        Faculty objFac=facultyList.get(position);
        txtfname.setText(objFac.getFname());
        txtfemail.setText(objFac.getFemail());
        txtfsub.setText(objFac.getFsub());

        return view;
    }

}
