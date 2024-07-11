package com.example.wilsonpreschool;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProgramsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        Button daycareButton = view.findViewById(R.id.daycare3);
        Button preKindergartenButton = view.findViewById(R.id.prekindergarten3);
        Button kindergartenButton = view.findViewById(R.id.kindergarten3);

        daycareButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), DaycareActivity.class)));
        preKindergartenButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), PrekindergartenActivity.class)));
        kindergartenButton.setOnClickListener(v -> startActivity(new Intent(getActivity(), KindergartenActivity.class)));

        return view;
    }
}