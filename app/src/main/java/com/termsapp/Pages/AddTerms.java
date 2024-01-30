package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    Button addCourse;
    Button save;
    Button delete;

    private void updateStart(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startBox.setText(sdf.format(termStart.getTime()));
    }
    private void updateEnd(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        endBox.setText(sdf.format(termEnd.getTime()));
    }
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
                updateStart();
            }
        };
        termE = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                termEnd.set(Calendar.YEAR, year);
                termEnd.set(Calendar.MONTH, month);
                termEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEnd();
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

        addCourse = findViewById(R.id.addCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTerms.this, AddCourses.class);
                intent.putExtra("Term ID", id);
                startActivity(intent);
            }
        });
        String getAlertDate = startBox.getText().toString();
        String startFormat = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(startFormat, Locale.US);
        Date startAlert = null;
        try{
            startAlert = simpleDateFormat.parse(getAlertDate);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        try{
            long startTrigger = startAlert.getTime();
            Intent intent = new Intent(AddTerms.this, TermStartReceiver.class);
            intent.putExtra("Term Start", termTitle.getText() + " has started.");
            PendingIntent sendStart = PendingIntent.getBroadcast(AddTerms.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager startManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            startManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sendStart);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}