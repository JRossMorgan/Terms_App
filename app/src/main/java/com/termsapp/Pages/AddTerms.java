package com.termsapp.Pages;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.termsapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.Repository;
import entites.CourseClass;
import entites.TermClass;

public class AddTerms extends AppCompatActivity {
    int id;
    String title;
    EditText termTitle;
    Long start;
    TextView startBox;
    Long end;
    TextView endBox;
    Repository repository;
    DatePickerDialog.OnDateSetListener termS;
    DatePickerDialog.OnDateSetListener termE;
    final Calendar termStart = Calendar.getInstance();
    final Calendar termEnd = Calendar.getInstance();
    Button addCourse;
    Button save;
    Button deleteTerm;
    SwitchMaterial allow;
    boolean notifications;
    SwitchMaterial alsoAllow;
    boolean endNotifications;

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
        id = getIntent().getIntExtra("Term ID", 0);
        title = getIntent().getStringExtra("Term Title");
        termTitle = findViewById(R.id.termTitle);
        termTitle.setText(title);
        startBox = findViewById(R.id.termStart);
        startBox.setText(getIntent().getStringExtra("Start Date"));
        endBox = findViewById(R.id.termEnd);
        endBox.setText(getIntent().getStringExtra("End Date"));
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termS = (view, year, month, dayOfMonth) -> {
            termStart.set(Calendar.YEAR, year);
            termStart.set(Calendar.MONTH, month);
            termStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        };
        termE = (view, year, month, dayOfMonth) -> {
            termEnd.set(Calendar.YEAR, year);
            termEnd.set(Calendar.MONTH, month);
            termEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnd();
        };
        startBox.setOnClickListener(v -> {
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
        });
        endBox.setOnClickListener(v -> {
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
        });
        allow = findViewById(R.id.switch3);
        notifications = getIntent().getBooleanExtra("Notify", false);
        allow.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            notifications = isChecked;
            Toast.makeText(AddTerms.this, "You allowed term start notifications.", Toast.LENGTH_LONG).show();
        }));
        if(notifications){
            allow.setChecked(true);
        }

        alsoAllow = findViewById(R.id.switch4);
        endNotifications = getIntent().getBooleanExtra("Also Notify", false);
        allow.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            endNotifications = isChecked;
            Toast.makeText(AddTerms.this, "You allowed term end notifications.", Toast.LENGTH_LONG).show();
        }));
        if(endNotifications){
            alsoAllow.setChecked(true);
        }

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
        addCourse.setOnClickListener(v -> {
            Intent intent = new Intent(AddTerms.this, AddCourses.class);
            intent.putExtra("Term ID", id);
            startActivity(intent);
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
            if(notifications) {
                long startTrigger = startAlert.getTime();
                Intent intent = new Intent(AddTerms.this, TermStartReceiver.class);
                intent.putExtra("Term Start", termTitle.getText() + " has started.");
                PendingIntent sendStart = PendingIntent.getBroadcast(AddTerms.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startManager.set(AlarmManager.RTC_WAKEUP, startTrigger, sendStart);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String endingAlert = endBox.getText().toString();
        String endFormat = "MM/dd/yyyy";
        SimpleDateFormat endingFormat = new SimpleDateFormat(endFormat, Locale.US);
        Date endAlert = null;
        try{
            endAlert = endingFormat.parse(endingAlert);
        }
        catch (ParseException exception){
            exception.printStackTrace();
        }
        try{
            if(endNotifications) {
                long endTrigger = endAlert.getTime();
                Intent endIntent = new Intent(AddTerms.this, TermEndReceiver.class);
                endIntent.putExtra("Term End", termTitle.getText() + " has ended.");
                PendingIntent sendEnd = PendingIntent.getBroadcast(AddTerms.this, ++MainActivity.alertCount, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, endTrigger, sendEnd);
            }

        }
        catch (Exception what){
            what.printStackTrace();
        }
        save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            start = termStart.getTimeInMillis();
            end = termEnd.getTimeInMillis();
            if(allow.isChecked()){
                notifications = true;
            }
            if(id == 0) {
                if(repository.getAllTerms().size() == 0){
                    id = 1;
                }
                else{
                    id = repository.getAllTerms().get(repository.getAllTerms().size() -1).getTermId()+1;
                }
                TermClass termClass = new TermClass(id, termTitle.getText().toString(), start, end, notifications, endNotifications);
                repository.insert(termClass);
                finishAfterTransition();
            }
            else{
                TermClass updateTerm = new TermClass(id, termTitle.getText().toString(), start, end, notifications, endNotifications);
                repository.update(updateTerm);
                finishAfterTransition();
            }
        });
        deleteTerm = findViewById(R.id.deleteButton);
        deleteTerm.setOnClickListener(v -> {
            TermClass currentTerm = null;
            for(TermClass term : repository.getAllTerms()){
                if(term.getTermId() == id){
                    currentTerm = term;
                }
            }
            int numCourses = 0;
            for(CourseClass c : repository.getAllCourses()){
                if(c.getTermId() == currentTerm.getTermId()){
                    numCourses++;
                }
            }
            if(numCourses == 0){
                repository.delete(currentTerm);
                Toast.makeText(AddTerms.this, currentTerm.getTermTitle() + " was deleted", Toast.LENGTH_LONG).show();
                finishAfterTransition();
            }
            else{
                Toast.makeText(AddTerms.this, "Cannot delete a term with associated courses.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository = new Repository(getApplication());
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