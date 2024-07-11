package com.example.wilsonpreschool;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegistrationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private applicantsAdapter adapter;
    private DatabaseReference mbase;
    String[] items = {"Male", "Female"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    private EditText mName, mGender, mBirthdate, mAddress, mParName, mHomPhone, mParPhone;
    private String selectedGender;
    private Button mSaveBtn;
    private ProgressDialog pd;
    private FirebaseFirestore db;
    ImageView cal;
    EditText dateTXT;
    private int mDate, mMonth, mYear;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbase = FirebaseDatabase.getInstance().getReference().child("applicants");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler2);
        autoCompleteTxt = view.findViewById(R.id.auto1);
        adapterItems = new ArrayAdapter<>(getActivity(), R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener((parent, view1, position, id) -> {
            selectedGender = parent.getItemAtPosition(position).toString();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configure FirebaseRecyclerOptions and Adapter
        FirebaseRecyclerOptions<applicants> options =
                new FirebaseRecyclerOptions.Builder<applicants>()
                        .setQuery(mbase, applicants.class)
                        .build();

        adapter = new applicantsAdapter(options);
        recyclerView.setAdapter(adapter);

        Log.d("RegistrationFragment", "Adapter set to RecyclerView");

        mBirthdate = view.findViewById(R.id.textInputLayout4);
        cal = view.findViewById(R.id.datepicker);

        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Cal = Calendar.getInstance();
                mDate = Cal.get(Calendar.DATE);
                mMonth = Cal.get(Calendar.MONTH);
                mYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int date) {
                        String formattedDate = String.format("%02d/%02d/%d", date, month + 1, year);
                        mBirthdate.setText(formattedDate);
                    }
                }, mYear,mMonth,mDate);
                datePickerDialog.show();
            }
        });


        // Initialize Views
        TextInputLayout nameInputLayout = view.findViewById(R.id.textInputLayout1);
        mName = nameInputLayout.getEditText(); // Corrected
        TextInputLayout genderInputLayout = view.findViewById(R.id.textInputLayout2);
        mGender = genderInputLayout.getEditText(); // Corrected
//        TextInputLayout birthdateInputLayout = view.findViewById(R.id.textInputLayout4);
//        mBirthdate = birthdateInputLayout.getEditText();// Corrected
        TextInputLayout addressInputLayout = view.findViewById(R.id.textInputLayout5);
        mAddress = addressInputLayout.getEditText();
        TextInputLayout parnameInputLayout = view.findViewById(R.id.textInputLayout6);
        mParName = parnameInputLayout.getEditText();
        TextInputLayout homphoneInputLayout = view.findViewById(R.id.textInputLayout7);
        mHomPhone = homphoneInputLayout.getEditText();
        TextInputLayout parphoneInputLayout = view.findViewById(R.id.textInputLayout8);
        mParPhone = parphoneInputLayout.getEditText();
        mSaveBtn = view.findViewById(R.id.btnPost);

        // Initialize Progress Dialog and Firestore Instance
        pd = new ProgressDialog(getActivity());
        db = FirebaseFirestore.getInstance();

        // Save Button Click Listener
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String gender = selectedGender;
                String birthdate = mBirthdate.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String parentName = mParName.getText().toString().trim();
                String homePhone = mHomPhone.getText().toString().trim();
                String parentPhone = mParPhone.getText().toString().trim();
                uploadData(name, gender, birthdate, address, parentName, homePhone, parentPhone);
            }
        });

        return view;
    }

    // Method to upload data to Firestore and Realtime Database
    private void uploadData(String name, String gender, String birthdate, String address, String parentName, String homePhone, String parentPhone) {
        pd.setTitle("Adding Data to Firestore");
        pd.show();
        final String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("name", name);
        doc.put("gender", gender);
        doc.put("dateofbirth", birthdate);
        doc.put("address", address);
        doc.put("parent", parentName);
        doc.put("phone", homePhone);
        doc.put("parentnum", parentPhone);

        // Add data to Firestore
        db.collection("applicants").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Uploaded...", Toast.LENGTH_SHORT).show();
                            clearForm();
                            // Add data to Realtime Database to update the RecyclerView
                            mbase.child(id).setValue(doc)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("RegistrationFragment", "Data added to Realtime Database");
                                                // Refresh the adapter to reflect new data
                                                adapter.notifyDataSetChanged();
                                                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("RegistrationFragment", "Failed to add data to Realtime Database", e);
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Failed to upload data...", Toast.LENGTH_SHORT).show();
                        }
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

    // Method to clear form fields
    private void clearForm() {
        mName.setText("");
        mGender.setText("");
        mBirthdate.setText("");
        mAddress.setText("");
        mParName.setText("");
        mHomPhone.setText("");
        mParPhone.setText("");
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
