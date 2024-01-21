package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import Database.Repository;
import entites.CourseClass;

public class AddTerms extends AppCompatActivity {
    int id;
    String title;
    EditText termTitle;
    LocalDate start;
    TextView startBox;
    LocalDate end;
    TextView endBox;
    Repository repository;
    DatePickerDialog.OnDateSetListener termS;
    DatePickerDialog.OnDateSetListener termE;
    final Calendar termStart = Calendar.getInstance();
    final Calendar termEnd = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terms);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("Term ID", 1);
        title = getIntent().getStringExtra("Term Title");
        termTitle = findViewById(R.id.termTitle);
        termTitle.setText(title);
        start = LocalDate.parse(getIntent().getStringExtra("Start Date"));
        startBox = findViewById(R.id.termStart);
        startBox.setText(start.toString());
        end = LocalDate.parse(getIntent().getStringExtra("End Date"));
        endBox = findViewById(R.id.termEnd);
        endBox.setText(end.toString());
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                termStart.set(Calendar.YEAR, year);
                termStart.set(Calendar.MONTH, month);
                termStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        termE = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                termEnd.set(Calendar.YEAR, year);
                termEnd.set(Calendar.MONTH, month);
                termEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        startBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = startBox.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    termStart.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerms.this, termS, termStart.get(Calendar.YEAR), termStart.get(Calendar.MONTH), termStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = endBox.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    termEnd.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerms.this, termE, termEnd.get(Calendar.YEAR), termEnd.get(Calendar.MONTH), termEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.associatedCourses);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<CourseClass> termCourses = new ArrayList<>();
        for (CourseClass c: repository.getAllCourses()){
            if(c.getTermId() == id){
                termCourses.add(c);
            }
        }
        courseAdapter.setCourseClassList(termCourses);

    }
}