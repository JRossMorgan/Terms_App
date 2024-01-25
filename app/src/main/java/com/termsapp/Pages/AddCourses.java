package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import Database.Repository;

public class AddCourses extends AppCompatActivity {
    int id;
    String title;
    TextView courseTitle;
    LocalDate start;
    TextView startDate;
    LocalDate end;
    TextView endDate;
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
    DatePickerDialog.OnDateSetListener starting;
    DatePickerDialog.OnDateSetListener ending;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();

    private void updateStart(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        startDate.setText(sdf.format(startCalendar.getTime()));
    }
    private void updateEnd(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        endDate.setText(sdf.format(endCalendar.getTime()));
    }
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
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        starting = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStart();
            }
        };
        ending = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEnd();
            }
        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = startDate.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    startCalendar.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourses.this, starting, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = endDate.getText().toString();
                if(info.equals("")){
                   info = "01/01/2024";
                }
                try{
                    endCalendar.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourses.this, ending, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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