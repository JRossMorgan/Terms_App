package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.Repository;
import entites.AssessmentClass;

public class AddAssessments extends AppCompatActivity {
    int id;
    String title;
    Long end;
    String type;
    EditText assessmentTitle;
    TextView assessmentDate;
    RadioButton objective;
    RadioButton performance;
    Repository repository;
    int courseId;
    DatePickerDialog.OnDateSetListener date;
    final Calendar calendar = Calendar.getInstance();
    Button save;
    Button delete;
    Button cancel;

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
        assessmentDate = findViewById(R.id.dateView);
        assessmentDate.setText(getIntent().getStringExtra("End Date"));
        courseId = getIntent().getIntExtra("Course ID", 0);

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

        String alertDate = assessmentDate.getText().toString();
        String format = "MM/dd/yyyy";
        SimpleDateFormat alertFormat = new SimpleDateFormat(format, Locale.US);
        Date alert = null;
        try{
            alert = alertFormat.parse(alertDate);
        }
        catch (ParseException parseException){
            parseException.printStackTrace();
        }
        try{
            long assessmentTrigger = alert.getTime();
            Intent intent = new Intent(AddAssessments.this, AssessmentReceiver.class);
            intent.putExtra("Assessment Alert", assessmentTitle.getText() + " has begun.");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AddAssessments.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, assessmentTrigger, pendingIntent);
        }
        catch (Exception what){
            what.printStackTrace();
        }
        save = findViewById(R.id.saveAssessment);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end = calendar.getTimeInMillis();
                AssessmentClass createAssessment = new AssessmentClass(id, title, type, end, courseId);
                repository.insert(createAssessment);
                Intent savedAssessment = new Intent(AddAssessments.this, Assessments.class);
                startActivity(savedAssessment);
            }
        });
        delete = findViewById(R.id.deleteAssessment);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(AssessmentClass a: repository.getAllAssessments()){
                    if(a.getAssessmentId() == id){
                        repository.delete(a);
                        Intent deletedAssessment = new Intent(AddAssessments.this, Assessments.class);
                        startActivity(deletedAssessment);
                    }
                    else{
                        Toast.makeText(AddAssessments.this, "No such Assessment", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        cancel = findViewById(R.id.cancelAssessment);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelAssessment = new Intent(AddAssessments.this, Assessments.class);
                startActivity(cancelAssessment);
            }
        });
    }
}