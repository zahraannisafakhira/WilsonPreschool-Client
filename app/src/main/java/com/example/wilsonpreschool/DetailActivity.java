package com.example.wilsonpreschool;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    TextView nameApp, genderApp, birthApp, addressApp, parentApp, homeApp, parentsApp;
    FloatingActionButton deleteButton, editButton;
    String key = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameApp = findViewById(R.id.nameApp);
        genderApp = findViewById(R.id.genderApp);
        birthApp = findViewById(R.id.birthApp);
        addressApp = findViewById(R.id.addressApp);
        parentApp = findViewById(R.id.parentApp);
        homeApp = findViewById(R.id.homeApp);
        parentsApp = findViewById(R.id.parentsApp);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
                reference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        navigateToRegistrationFragment();
                    }
                });
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Title", nameApp.getText().toString())
                        .putExtra("Description", genderApp.getText().toString())
                        .putExtra("Language", birthApp.getText().toString())
                        .putExtra("Title", addressApp.getText().toString())
                        .putExtra("Description", parentApp.getText().toString())
                        .putExtra("Language", parentApp.getText().toString())
                        .putExtra("Title", parentsApp.getText().toString())
                        .putExtra("Key", key);
                startActivity(intent);
                finish(); // Finish DetailActivity when starting UpdateActivity
            }
        });
    }

    private void navigateToRegistrationFragment() {
        // Navigate directly to RegistrationFragment
        getSupportFragmentManager().popBackStack(); // Clear back stack
        finish(); // Finish DetailActivity
    }
}
