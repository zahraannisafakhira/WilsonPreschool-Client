package com.example.wilsonpreschool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    Button updateButton;
    EditText nameApp, genderApp, birthApp, addressApp, parentApp, homeApp, parentsApp;
    String name, gender, birth, address, parent, home, parents;
    String key;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateButton = findViewById(R.id.updateButton);
        nameApp = findViewById(R.id.nameApp);
        genderApp = findViewById(R.id.genderApp);
        birthApp = findViewById(R.id.birthApp);
        addressApp = findViewById(R.id.addressApp);
        parentApp = findViewById(R.id.parentApp);
        homeApp = findViewById(R.id.homeApp);
        parentsApp = findViewById(R.id.parentsApp);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            nameApp.setText(bundle.getString("Name"));
            genderApp.setText(bundle.getString("Gender"));
            birthApp.setText(bundle.getString("Date of Birth"));
            addressApp.setText(bundle.getString("Address"));
            parentApp.setText(bundle.getString("Parent's Name"));
            homeApp.setText(bundle.getString("Home Phone Number"));
            parentsApp.setText(bundle.getString("Parent's Phone Number"));
            key = bundle.getString("Key");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("applicants").child(key);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        name = nameApp.getText().toString().trim();
        gender = genderApp.getText().toString().trim();
        birth = birthApp.getText().toString().trim();
        address = addressApp.getText().toString().trim();
        parent = parentApp.getText().toString().trim();
        home = parentApp.getText().toString().trim();
        parents = parentsApp.getText().toString().trim();

        DataClass dataClass = new DataClass(name, gender, birth, address, parent, home, parents);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    // Navigate back to the registration fragment
                    Intent intent = new Intent(UpdateActivity.this, RegistrationFragment.class);
                    startActivity(intent);
                    finish(); // Close the UpdateActivity
                } else {
                    Toast.makeText(UpdateActivity.this, "Update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                Toast.makeText(UpdateActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
