package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.termsapp.R;

import java.util.List;

import Database.Repository;
import entites.AssessmentClass;

public class Assessments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        FloatingActionButton addAssessments = findViewById(R.id.addAssessments);
        addAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Assessments.this, AddAssessments.class);
                startActivity(intent);
            }
        });
        Repository repository = new Repository(getApplication());
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
        Repository repository = new Repository(getApplication());
        List<AssessmentClass> allAssessments = repository.getAllAssessments();
        RecyclerView rv = findViewById(R.id.assessmentView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        rv.setAdapter(assessmentAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessmentClassList(allAssessments);
    }


}