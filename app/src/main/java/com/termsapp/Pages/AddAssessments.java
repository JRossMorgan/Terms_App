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
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

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
    DatePickerDialog.OnDateSetListener date;
    final Calendar calendar = Calendar.getInstance();

    private void updateDate(){
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        assessmentDate.setText(sdf.format(calendar.getTime()));
    }
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

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        assessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = assessmentDate.getText().toString();
                if(info.equals("")){
                    info = "01/01/2024";
                }
                try{
                    calendar.setTime(sdf.parse(info));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessments.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}