package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.termsapp.R;

import java.time.LocalDateTime;

import Database.Repository;

public class AddAssessments extends AppCompatActivity {
    int id;
    String title;
    LocalDateTime end;
    String type;
    EditText assessmentTitle;
    TextView assessmentDate;
    RadioButton objective;
    RadioButton performance;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessments);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("Assessment ID", 1);
        title = getIntent().getStringExtra("Title");
        assessmentTitle = findViewById(R.id.assessmentTitle);
        assessmentTitle.setText(title);
        objective = findViewById(R.id.assessmentO);
        performance = findViewById(R.id.assessmentP);
        if(objective.isSelected()){
            type = "Objective";
        }
        else if(performance.isSelected()){
            type = "Performance";
        }
        end = LocalDateTime.parse(getIntent().getStringExtra("End Date"));
        assessmentDate = findViewById(R.id.dateView);
        assessmentDate.setText(end.toString());
    }
}