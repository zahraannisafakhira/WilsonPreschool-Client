package com.example.wilsonpreschool;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wilsonpreschool.comments;
import com.example.wilsonpreschool.commentsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlogFragment extends Fragment {

    private RecyclerView recyclerView;
    private commentsAdapter adapter;
    private DatabaseReference mbase;

    private EditText mName, mEmail, mComment;
    private Button mSaveBtn;
    private ProgressDialog pd;
    private FirebaseFirestore db;

    public BlogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbase = FirebaseDatabase.getInstance().getReference().child("comments");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<comments> options = new FirebaseRecyclerOptions.Builder<comments>()
                .setQuery(mbase, comments.class)
                .build();

        adapter = new commentsAdapter(options);
        recyclerView.setAdapter(adapter);

        Log.d("BlogFragment", "Adapter set to RecyclerView");

        // Correctly set up the input fields and button
        TextInputLayout nameInputLayout = view.findViewById(R.id.textInputLayout1);
        mName = nameInputLayout.getEditText(); // Corrected
        TextInputLayout emailInputLayout = view.findViewById(R.id.textInputLayout2);
        mEmail = emailInputLayout.getEditText(); // Corrected
        TextInputLayout commentInputLayout = view.findViewById(R.id.textInputLayout4);
        mComment = commentInputLayout.getEditText(); // Corrected
        mSaveBtn = view.findViewById(R.id.btnPost);

        pd = new ProgressDialog(getActivity());
        db = FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String comment = mComment.getText().toString().trim();
                uploadData(name, email, comment);
            }
        });

        return view;
    }

    private void uploadData(final String name, final String email, final String comment) {
        pd.setTitle("Adding Data to Firestore");
        pd.show();
        final String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("name", name);
        doc.put("email", email);
        doc.put("comment", comment);

        db.collection("comments").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Uploaded...", Toast.LENGTH_SHORT).show();
                        clearForm();

                        // Add data to Realtime Database to update the RecyclerView
                        mbase.child(id).setValue(doc)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("BlogFragment", "Data added to Realtime Database");
                                            // Refresh the adapter to reflect new data
                                            adapter.notifyDataSetChanged();
                                            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("BlogFragment", "Failed to add data to Realtime Database", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearForm() {
        mName.setText("");
        mEmail.setText("");
        mComment.setText("");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
            Log.d("BlogFragment", "Adapter started listening");
        } else {
            Log.w("BlogFragment", "Adapter is null in onStart");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
            Log.d("BlogFragment", "Adapter stopped listening");
        } else {
            Log.w("BlogFragment", "Adapter is null in onStop");
        }
    }
}
