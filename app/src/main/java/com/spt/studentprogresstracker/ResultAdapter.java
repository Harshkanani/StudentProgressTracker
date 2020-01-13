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

public class ResultAdapter extends ArrayAdapter<Result> {

    private List<Result> resultlist;
    private Context mcontext;

    public ResultAdapter(Context c,List<Result> result)
    {
        super(c,R.layout.marks_listview_row,result);
        this.resultlist=result;
        this.mcontext=c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View view=inflater.inflate(R.layout.marks_listview_row,null,true);

        TextView txtmarksdate=(TextView)view.findViewById(R.id.txtmarksdate);
        TextView txtmarkstype=(TextView)view.findViewById(R.id.txtmarkstitle);
        TextView txtmarks=(TextView)view.findViewById(R.id.txtmarks);
        TextView txtmarksstatus=(TextView)view.findViewById(R.id.txtmarksstatus);

        Result objRes=resultlist.get(position);
        txtmarksdate.setText(objRes.getDate());
        txtmarkstype.setText(objRes.getType());
        txtmarks.setText(objRes.getMarks());
        txtmarksstatus.setText(objRes.getStatus());

        return view;
    }


}
