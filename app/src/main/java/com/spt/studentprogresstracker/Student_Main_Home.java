package com.spt.studentprogresstracker;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Main_Home extends Fragment {


    CardView cardAttandance;
    CardView cardResult;
    CardView cardNotice;
    public Student_Main_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_student__main__home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findcontrol();

        cardAttandance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student_Main_HomeDirections.ActionStudentMainHomeToStudentMainAttandance action=Student_Main_HomeDirections.actionStudentMainHomeToStudentMainAttandance();

                action.setClickCheck("Attandance");

                Navigation.findNavController(view).navigate(action);
            }
        });

        cardResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student_Main_HomeDirections.ActionStudentMainHomeToStudentMainAttandance action=Student_Main_HomeDirections.actionStudentMainHomeToStudentMainAttandance();

                action.setClickCheck("Result");

                Navigation.findNavController(view).navigate(action);
            }
        });
        cardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_student_Main_Home_to_student_Main_Notice);
            }
        });
    }

    public void findcontrol()
    {
        cardAttandance=getView().findViewById(R.id.c1);
        cardResult=getView().findViewById(R.id.c2);
        cardNotice=getView().findViewById(R.id.c4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((Student_Main) getActivity()).getSupportActionBar().show();
        ((Student_Main) getActivity()).getSupportActionBar().setTitle("Home");
        super.onActivityCreated(savedInstanceState);
    }
}
