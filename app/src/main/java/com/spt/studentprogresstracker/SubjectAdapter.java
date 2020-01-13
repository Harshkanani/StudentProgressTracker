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

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private List<Subject> subjectlist;
    private Context mcontext;

    public SubjectAdapter(Context c,List<Subject> sub)
    {
        super(c,R.layout.subject_listview_row,sub);
        this.subjectlist=sub;
        this.mcontext=c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View view=inflater.inflate(R.layout.subject_listview_row,null,true);

        TextView txtsubcode=(TextView)view.findViewById(R.id.txtsubcode);
        TextView txtsubname=(TextView)view.findViewById(R.id.txtsubname);

        Subject objSub=subjectlist.get(position);
        txtsubcode.setText(objSub.getSubcode());
        txtsubname.setText(objSub.getSubname());

        return view;
    }
}
