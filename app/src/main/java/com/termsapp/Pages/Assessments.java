package com.termsapp.Pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.termsapp.R;

import java.util.List;

import Database.Repository;
import entites.AssessmentClass;

public class Assessments extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        FloatingActionButton addAssessments = findViewById(R.id.addAssessments);
        addAssessments.setOnClickListener(v -> {
            Intent intent = new Intent(Assessments.this, AddAssessments.class);
            startActivity(intent);
        });
        repository = new Repository(getApplication());
        List<AssessmentClass> allAssessments = repository.getAllAssessments();
        RecyclerView rv = findViewById(R.id.assessmentView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        rv.setAdapter(assessmentAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessmentClassList(allAssessments);
    }
    @Override
    protected void onResume(){
        super.onResume();
        repository = new Repository(getApplication());
        List<AssessmentClass> allAssessments = repository.getAllAssessments();
        RecyclerView rv = findViewById(R.id.assessmentView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        rv.setAdapter(assessmentAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessmentClassList(allAssessments);
    }


}