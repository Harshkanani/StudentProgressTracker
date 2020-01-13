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

public class NoticeAdapter extends ArrayAdapter<Notice> {

    private List<Notice> noticelist;
    private Context mcontext;

    public NoticeAdapter(Context c,List<Notice> notice)
    {
        super(c,R.layout.notice_listview_row,notice);
        this.noticelist=notice;
        this.mcontext=c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View view=inflater.inflate(R.layout.notice_listview_row,null,true);

        TextView txtnoticetitle=(TextView)view.findViewById(R.id.txtnoticetitle);
        TextView txtnoticedate=(TextView)view.findViewById(R.id.txtnoticedate);
        TextView txtnoticename=(TextView)view.findViewById(R.id.txtnoticepostedname);

        Notice objNotice=noticelist.get(position);
        txtnoticetitle.setText(objNotice.getTitle());
        txtnoticedate.setText(objNotice.getDate());
        txtnoticename.setText(objNotice.getName());

        return view;
    }
}
