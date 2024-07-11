package com.example.wilsonpreschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class applicantsAdapter extends FirebaseRecyclerAdapter<applicants, applicantsAdapter.applicantsViewHolder> {

    public applicantsAdapter(@NonNull FirebaseRecyclerOptions<applicants> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull applicantsViewHolder holder, int position, @NonNull applicants model) {
        holder.name.setText(model.getName());
        holder.gender.setText(model.getGender());
        holder.dateofbirth.setText(model.getDateofbirth());
    }

    @NonNull
    @Override
    public applicantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicants, parent, false);
        return new applicantsViewHolder(view);
    }

    // ViewHolder class to hold references to views
    static class applicantsViewHolder extends RecyclerView.ViewHolder {
        TextView name, gender, dateofbirth;

        public applicantsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameApp);
            gender = itemView.findViewById(R.id.genderApp);
            dateofbirth = itemView.findViewById(R.id.birthApp);
        }
    }
}
