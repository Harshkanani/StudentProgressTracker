package com.spt.studentprogresstracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AttandanceAdapter extends ArrayAdapter<Attandance> implements Filterable {

    private List<Attandance> attandanceList;
    private Context mcontext;

    public AttandanceAdapter(Context c, List<Attandance> attandances) {
        super(c, R.layout.attandance_listview_row, attandances);
        this.attandanceList = attandances;
        this.mcontext = c;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.attandance_listview_row, null, true);

        TextView txtdate = (TextView) view.findViewById(R.id.txtdate);
        TextView txtstatus = (TextView) view.findViewById(R.id.txtstatus);
        TextView txttype=(TextView) view.findViewById(R.id.txttype);

        Attandance objatt = attandanceList.get(position);
        txtdate.setText(objatt.getDate());
        txtstatus.setText(objatt.getStatus());
        txttype.setText(objatt.getType());

        return view;
    }
}

