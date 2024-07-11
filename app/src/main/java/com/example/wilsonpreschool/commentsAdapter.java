package com.example.wilsonpreschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class commentsAdapter extends FirestoreRecyclerAdapter<comments, commentsAdapter.commentsViewHolder> {

    public commentsAdapter(@NonNull FirestoreRecyclerOptions<comments> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull commentsViewHolder holder, int position, @NonNull comments model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.comment.setText(model.getComment());
    }

    @NonNull
    @Override
    public commentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments, parent, false);
        return new commentsViewHolder(view);
    }

    class commentsViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, comment;

        public commentsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
