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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlogFragment extends Fragment {

    private RecyclerView recyclerView;
    private commentsAdapter adapter;
    private FirebaseFirestore db;
    private CollectionReference commentsRef;

    private EditText mName, mEmail, mComment;
    private Button mSaveBtn;
    private ProgressDialog pd;

    public BlogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        commentsRef = db.collection("comments");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blog, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set up FirestoreRecyclerOptions
        Query query = commentsRef.orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<comments> options = new FirestoreRecyclerOptions.Builder<comments>()
                .setQuery(query, comments.class)
                .build();

        adapter = new commentsAdapter(options);
        recyclerView.setAdapter(adapter);

        Log.d("BlogFragment", "Adapter set to RecyclerView");

        // Correctly set up the input fields and button
        TextInputLayout nameInputLayout = view.findViewById(R.id.textInputLayout1);
        mName = nameInputLayout.getEditText();
        TextInputLayout emailInputLayout = view.findViewById(R.id.textInputLayout2);
        mEmail = emailInputLayout.getEditText();
        TextInputLayout commentInputLayout = view.findViewById(R.id.textInputLayout4);
        mComment = commentInputLayout.getEditText();
        mSaveBtn = view.findViewById(R.id.btnPost);

        pd = new ProgressDialog(getActivity());
        db = FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() | !validateEmail() | !validateComment()) {
                    // If any field is invalid, do not proceed with the upload
                } else {
                    // Proceed with upload if all fields are valid
                    String name = mName.getText().toString().trim();
                    String email = mEmail.getText().toString().trim();
                    String comment = mComment.getText().toString().trim();
                    uploadData(name, email, comment);
                }
            }
        });

        return view;
    }

    private Boolean validateName() {
        String val = mName.getText().toString();
        if (val.isEmpty()) {
            mName.setError("Name cannot be empty");
            return false;
        } else {
            mName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = mEmail.getText().toString();
        if (val.isEmpty()) {
            mEmail.setError("Email cannot be empty");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }

    private Boolean validateComment() {
        String val = mComment.getText().toString();
        if (val.isEmpty()) {
            mComment.setError("Comment cannot be empty");
            return false;
        } else {
            mComment.setError(null);
            return true;
        }
    }

    private void uploadData(final String name, final String email, final String comment) {
        pd.setTitle("Uploading");
        pd.show();
        final String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("name", name);
        doc.put("email", email);
        doc.put("comment", comment);
        doc.put("timestamp", System.currentTimeMillis());

        db.collection("comments").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Uploaded...", Toast.LENGTH_SHORT).show();
                        clearForm();
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
