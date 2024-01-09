package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.termsapp.R;

import java.time.LocalDate;

import Database.Repository;

public class AddCourses extends AppCompatActivity {
    int id;
    String title;
    TextView courseTitle;
    LocalDate start;
    EditText startDate;
    LocalDate end;
    EditText endDate;
    String status;
    RadioButton inProgress;
    RadioButton complete;
    RadioButton drop;
    RadioButton plan;
    String instructorName;
    TextView name;
    String instructorPhone;
    EditText phone;
    String instructorEmail;
    EditText email;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("Course ID", 1);
        title = getIntent().getStringExtra("Title");
        courseTitle = findViewById(R.id.courseTitle);
        courseTitle.setText(title);
        start = LocalDate.parse(getIntent().getStringExtra("Start Date"));
        startDate = findViewById(R.id.startDate);
        startDate.setText(start.toString());
        end = LocalDate.parse(getIntent().getStringExtra("End Date"));
        endDate = findViewById(R.id.endDate);
        endDate.setText(end.toString());
        inProgress = findViewById(R.id.inProgress);
        complete = findViewById(R.id.completed);
        drop = findViewById(R.id.dropped);
        plan = findViewById(R.id.planned);
        if(inProgress.isSelected()){
            status = "In Progress";
        }
        else if(complete.isSelected()){
            status = "Completed";
        }
        else if(drop.isSelected()){
            status = "Dropped";
        }
        else if(plan.isSelected()){
            status = "Plan to take";
        }
        instructorName = getIntent().getStringExtra("Instructor's Name");
        name = findViewById(R.id.instructorsName);
        name.setText(instructorName);
        instructorPhone = getIntent().getStringExtra("Instructor's Phone Number");
        phone = findViewById(R.id.editTextPhone);
        phone.setText(instructorPhone);
        instructorEmail = getIntent().getStringExtra("Instructor's Email");
        email = findViewById(R.id.editTextEmailAddress);
        email.setText(instructorEmail);

    }
}