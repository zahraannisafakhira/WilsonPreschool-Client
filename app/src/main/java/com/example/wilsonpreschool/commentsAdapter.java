package com.example.wilsonpreschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class commentsAdapter extends FirebaseRecyclerAdapter<
        comments, commentsAdapter.commentsViewholder>{

    public commentsAdapter(
            @NonNull FirebaseRecyclerOptions<comments> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull commentsViewholder holder,
                     int position, @NonNull comments model)
    {

        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.name.setText(model.getName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.email.setText(model.getEmail());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.comment.setText(model.getComment());
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public commentsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments, parent, false);
        return new commentsAdapter.commentsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class commentsViewholder
            extends RecyclerView.ViewHolder {
        TextView name, email, comment;
        public commentsViewholder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
