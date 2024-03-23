package com.termsapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.termsapp.R;

public class MainActivity extends AppCompatActivity {
    public static int alertCount;
    public static int alertIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button termsButton = findViewById(R.id.testButton);
        termsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Terms.class);
            startActivity(intent);
        });
        Button coursesButton = findViewById(R.id.Course);
        coursesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Courses.class);
            startActivity(intent);
        });
        Button assessmentButton = findViewById(R.id.Assessment);
        assessmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Assessments.class);
            startActivity(intent);
        });
    }
}