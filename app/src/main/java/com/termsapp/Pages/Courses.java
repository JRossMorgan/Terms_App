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
import entites.CourseClass;

public class Courses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        FloatingActionButton addCourses = findViewById(R.id.addCourses);
        addCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Courses.this, AddCourses.class);
                startActivity(intent);
            }
        });
        Repository repository = new Repository(getApplication());
        List<CourseClass> allCourses = repository.getAllCourses();
        RecyclerView rv = findViewById(R.id.courseView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        rv.setAdapter(courseAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourseClassList(allCourses);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Repository repository = new Repository(getApplication());
        List<CourseClass> allCourses = repository.getAllCourses();
        RecyclerView rv = findViewById(R.id.courseView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        rv.setAdapter(courseAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourseClassList(allCourses);
    }
}