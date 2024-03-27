package com.termsapp.Pages;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.Repository;
import entites.AssessmentClass;

public class AddAssessments extends AppCompatActivity {
    int id;
    String title;
    Long start;
    Long end;
    String type;
    EditText assessmentTitle;
    TextView assessmentDate;
    TextView assessmentStart;
    RadioButton objective;
    RadioButton performance;
    Repository repository;
    int courseId;
    DatePickerDialog.OnDateSetListener starting;
    DatePickerDialog.OnDateSetListener date;
    final Calendar calendar = Calendar.getInstance();
    final Calendar anotherCalendar = Calendar.getInstance();
    Button save;
    Button delete;
    Button cancel;
    SwitchMaterial allow;
    boolean notifications;

    private void updateDate(){
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        assessmentDate.setText(sdf.format(calendar.getTime()));
    }
    private void updateStart(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        assessmentStart.setText(simpleDateFormat.format(anotherCalendar.getTime()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessments);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("Assessment ID", 0);
        title = getIntent().getStringExtra("Title");
        assessmentTitle = findViewById(R.id.assessmentTitle);
        assessmentTitle.setText(title);
        objective = findViewById(R.id.assessmentO);
        performance = findViewById(R.id.assessmentP);
        allow = findViewById(R.id.switch1);
        notifications = getIntent().getBooleanExtra("Notify", true);
        allow.setOnCheckedChangeListener((buttonView, isChecked) -> notifications = isChecked);
        if(notifications){
            allow.setChecked(true);
        }
        type = getIntent().getStringExtra("Type");
        if(type != null){
            switch (type){
                case "Objective Assessment":
                    objective.setChecked(true);
                    break;
                case"Performance Assessment":
                    performance.setChecked(true);
                    break;
            }
        }
        assessmentDate = findViewById(R.id.dateView);
        assessmentDate.setText(getIntent().getStringExtra("End Date"));
        assessmentStart = findViewById(R.id.assessStart);
        assessmentStart.setText(getIntent().getStringExtra("Start Date"));

        courseId = getIntent().getIntExtra("Course ID", 0);

        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        date = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        };
        starting = (view, year, month, dayOfMonth) -> {
            anotherCalendar.set(Calendar.YEAR, year);
            anotherCalendar.set(Calendar.MONTH, month);
            anotherCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        };

        assessmentDate.setOnClickListener(v -> {
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
        });

        assessmentStart.setOnClickListener(v -> {
            String info = assessmentStart.getText().toString();
            if(info.equals("")){
                info = "01/01/2024";
            }
            try{
                anotherCalendar.setTime(sdf.parse(info));
            }
            catch(ParseException exception){
                exception.printStackTrace();
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
            if(notifications){
                long assessmentTrigger = alert.getTime();
                Intent intent = new Intent(AddAssessments.this, AssessmentReceiver.class);
                intent.putExtra("Assessment Alert", assessmentTitle.getText() + " has ended.");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddAssessments.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, assessmentTrigger, pendingIntent);}
        }
        catch (NullPointerException what){
            what.printStackTrace();
        }

        String oiBlyat = assessmentStart.getText().toString();
        String anotherFormat = "MM/dd/yyyy";
        SimpleDateFormat alertAgain = new SimpleDateFormat(anotherFormat, Locale.US);
        Date anotherAlert = null;
        try{
            anotherAlert = alertAgain.parse(oiBlyat);
        }
        catch(ParseException blin){
            blin.printStackTrace();
        }
        try{
            if(notifications){
                long anotherTrigger = anotherAlert.getTime();
                Intent intent = new Intent(AddAssessments.this, AssessmentStartReceiver.class);
                intent.putExtra("Starting Alert", assessmentTitle.getText() + " has begun.");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddAssessments.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, anotherTrigger, pendingIntent);
            }
        }
        catch (NullPointerException oof){
            oof.printStackTrace();
        }

        save = findViewById(R.id.saveAssessment);
        save.setOnClickListener(v -> {
            end = calendar.getTimeInMillis();
            if(objective.isChecked()){
                type = "Objective Assessment";
            }
            else if(performance.isChecked()){
                type = "Performance Assessment";
            }
            if(allow.isChecked()){
                notifications = true;
            }
            if (id == 0){
                if(repository.getAllAssessments().size() == 0){
                    id = 1;
                }
                else{
                    id = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() +1;
                }
                AssessmentClass createAssessment = new AssessmentClass(id, assessmentTitle.getText().toString(), type, start, end, notifications, courseId);
                repository.insert(createAssessment);
                Intent savedAssessment = new Intent(AddAssessments.this, Assessments.class);
                startActivity(savedAssessment);
            }
            else{
                AssessmentClass updateAssessment = new AssessmentClass(id, assessmentTitle.getText().toString(), type, start, end, notifications, courseId);
                repository.update(updateAssessment);
                Intent assessmentUpdated = new Intent(AddAssessments.this, Assessments.class);
                startActivity(assessmentUpdated);
            }
        });
        delete = findViewById(R.id.deleteAssessment);
        delete.setOnClickListener(v -> {
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
        });
        cancel = findViewById(R.id.cancelAssessment);
        cancel.setOnClickListener(v -> {
            Intent cancelAssessment = new Intent(AddAssessments.this, Assessments.class);
            startActivity(cancelAssessment);
        });
    }
}