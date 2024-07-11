package com.example.wilsonpreschool.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wilsonpreschool.LoginActivity;
import com.example.wilsonpreschool.SignupActivity;
import com.example.wilsonpreschool.databinding.ActivityIntroBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wilsonpreschool.SignupActivity;
import com.example.wilsonpreschool.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Delay for 3 seconds before starting the SignupActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, SignupActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        }, 3000); // 3000 milliseconds equals 3 seconds
    }
}