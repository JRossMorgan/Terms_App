package com.termsapp.Pages;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import entites.AssessmentClass;
import entites.CourseClass;
import entites.Notes;

public class AddCourses extends AppCompatActivity {
    int id;
    String title;
    EditText courseTitle;
    Long start;
    TextView startDate;
    Long end;
    TextView endDate;
    String status;
    RadioButton inProgress;
    RadioButton complete;
    RadioButton drop;
    RadioButton plan;
    String instructorName;
    EditText name;
    String instructorPhone;
    EditText phone;
    String instructorEmail;
    EditText email;
    int termId;
    Repository repository;
    DatePickerDialog.OnDateSetListener starting;
    DatePickerDialog.OnDateSetListener ending;
    final Calendar startCalendar = Calendar.getInstance();
    final Calendar endCalendar = Calendar.getInstance();
    SwitchMaterial notify;
    boolean notifications;
    SwitchMaterial notifyToo;
    boolean endNotify;
    TextView phoneLabel;
    TextView emailLabel;

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
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        repository = new Repository(getApplication());
        id = getIntent().getIntExtra("Course ID", 0);
        title = getIntent().getStringExtra("Title");
        courseTitle = findViewById(R.id.courseTitle);
        courseTitle.setText(title);
        startDate = findViewById(R.id.startDate);
        startDate.setText(getIntent().getStringExtra("Start Date"));
        endDate = findViewById(R.id.endDate);
        endDate.setText(getIntent().getStringExtra("End Date"));
        termId = getIntent().getIntExtra("Term ID", 0);
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        starting = (view, year, monthOfYear, dayOfMonth) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        };
        ending = (view, year, month, dayOfMonth) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, month);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnd();
        };
        startDate.setOnClickListener(v -> {
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
        });
        endDate.setOnClickListener(v -> {
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
        });

        status = getIntent().getStringExtra("Status");
        inProgress = findViewById(R.id.inProgress);
        complete = findViewById(R.id.completed);
        drop = findViewById(R.id.dropped);
        plan = findViewById(R.id.planned);
        if(status != null){
            switch (status) {
                case "In Progress":
                    inProgress.setChecked(true);
                    break;
                case "Completed":
                    complete.setChecked(true);
                    break;
                case "Dropped":
                    drop.setChecked(true);
                    break;
                case "Plan To Take":
                    plan.setChecked(true);
                    break;
            }
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

        notify = findViewById(R.id.switch2);
        notifications = getIntent().getBooleanExtra("Notify", false);
        notify.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            notifications = isChecked;
            Toast.makeText(AddCourses.this, "You enabled start notifications", Toast.LENGTH_LONG).show();
        }));
        if(notifications){
            notify.setChecked(true);
        }
        notifyToo = findViewById(R.id.endNotifications);
        endNotify = getIntent().getBooleanExtra("Notify End", false);
        notifyToo.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            endNotify = isChecked;
            Toast.makeText(AddCourses.this, "You enabled end notifications", Toast.LENGTH_LONG).show();
        }));
        if(endNotify){
            notifyToo.setChecked(true);
        }
        phoneLabel = findViewById(R.id.textView15);
        phoneLabel.setText("Instructor's Phone");
        emailLabel = findViewById(R.id.textView16);
        emailLabel.setText("Instructor's Email");

        RecyclerView recyclerView = findViewById(R.id.associatedAssessments);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<AssessmentClass> courseAssessments = new ArrayList<>();
        for(AssessmentClass a : repository.getAllAssessments()){
            if(a.getCourseId() == id){
                courseAssessments.add(a);
            }
        }
        assessmentAdapter.setAssessmentClassList(courseAssessments);

        RecyclerView rv = findViewById(R.id.associatedNotes);
        final NoteAdapter noteAdapter = new NoteAdapter(this);
        rv.setAdapter(noteAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Notes> courseNotes = new ArrayList<>();
        for(Notes notes : repository.getAllNotes()){
            if(notes.getCourseId() == id){
                courseNotes.add(notes);
            }
        }
        noteAdapter.setNotesClassList(courseNotes);

        String startCourse = startDate.getText().toString();
        String startFormat = "MM/dd/yyyy";
        SimpleDateFormat starting = new SimpleDateFormat(startFormat, Locale.US);
        Date startAlert = null;
        try{
            startAlert = starting.parse(startCourse);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        try{
            if(startAlert != null){
                if(notifications){
                    long startTrigger = startAlert.getTime();
                    Intent startIntent = new Intent(AddCourses.this, CourseStart.class);
                    startIntent.putExtra("Start Course", courseTitle.getText() + " has started");
                    PendingIntent sendStart = PendingIntent.getBroadcast(AddCourses.this, ++MainActivity.alertCount, startIntent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager startAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    startAlarm.set(AlarmManager.RTC_WAKEUP, startTrigger, sendStart);
                }
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        String endCourse = endDate.getText().toString();
        String endFormat = "MM/dd/yyyy";
        SimpleDateFormat ending = new SimpleDateFormat(endFormat, Locale.US);
        Date endAlert = null;
        try{
            endAlert = ending.parse(endCourse);
        }
        catch (ParseException parseException){
            parseException.printStackTrace();
        }
        try{
            if(endAlert != null){
                if(endNotify) {
                    long endTrigger = endAlert.getTime();
                    Intent endIntent = new Intent(AddCourses.this, CourseEnd.class);
                    endIntent.putExtra("End Course", courseTitle.getText() + " has ended.");
                    PendingIntent sendEnd = PendingIntent.getBroadcast(AddCourses.this, ++MainActivity.alertCount, endIntent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager endAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    endAlarm.set(AlarmManager.RTC_WAKEUP, endTrigger, sendEnd);
                }
            }
        }
        catch (Exception what){
            what.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.assessment){
            if(id == 0){
                Toast.makeText(AddCourses.this, "Please save the course before adding assessments", Toast.LENGTH_LONG).show();
            }
            else{
                Intent assessmentIntent = new Intent(AddCourses.this, AddAssessments.class);
                assessmentIntent.putExtra("Course ID", id);
                startActivity(assessmentIntent);
                return true;
            }
        }
        if(menuItem.getItemId() == R.id.addNote){
            if(id == 0){
                Toast.makeText(AddCourses.this, "Please save the course before adding notes", Toast.LENGTH_LONG).show();
            }
            else{
                Intent noteIntent = new Intent(AddCourses.this, NotesPage.class);
                noteIntent.putExtra("Course Id", id);
                startActivity(noteIntent);
                return true;
            }
        }
        if(menuItem.getItemId() == R.id.deleteCourse){
            CourseClass currentCourse = null;
            for(CourseClass c : repository.getAllCourses()){
                if(c.getCourseId() == id){
                    currentCourse = c;
                }
            }
            int numAssessments = 0;
            int numNotes = 0;
            for(AssessmentClass a : repository.getAllAssessments()){
                if(currentCourse != null){
                    if(a.getCourseId() == currentCourse.getCourseId()){
                        numAssessments++;
                    }
                }

            }
            for(Notes n : repository.getAllNotes()){
                if(currentCourse != null){
                    if(n.getCourseId() == currentCourse.getCourseId()){
                        numNotes++;
                    }
                }
            }
            if(numAssessments == 0 && numNotes == 0){
                if(currentCourse != null){
                    repository.delete(currentCourse);
                    Toast.makeText(AddCourses.this, currentCourse.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    Intent backToCourses = new Intent(AddCourses.this, Courses.class);
                    startActivity(backToCourses);
                }
            }
            else{
                Toast.makeText(AddCourses.this, "Cannot delete courses with associated assessments and notes", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(menuItem.getItemId() == R.id.save){
            start = startCalendar.getTimeInMillis();
            end = endCalendar.getTimeInMillis();
            if(inProgress.isChecked()){
                status = "In Progress";
            }
            else if(complete.isChecked()){
                status = "Completed";
            }
            else if(drop.isChecked()){
                status = "Dropped";
            }
            else if(plan.isChecked()){
                status = "Plan To Take";
            }
            if(notify.isChecked()){
                notifications = true;
            }
            if(id == 0){
                if(repository.getAllCourses().size() ==0){
                    id = 1;
                }
                else{
                    id = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseId() +1;
                }
                CourseClass courseClass = new CourseClass(id, courseTitle.getText().toString(), start, end, status, name.getText().toString(), phone.getText().toString(), email.getText().toString(), notifications, endNotify, termId);
                repository.insert(courseClass);
                Intent i =new Intent(AddCourses.this, Courses.class);
                startActivity(i);
                }
                else{
                    CourseClass updateCourse = new CourseClass(id, courseTitle.getText().toString(), start, end, status, name.getText().toString(), phone.getText().toString(), email.getText().toString(), notifications, endNotify, termId);
                    repository.update(updateCourse);
                    Intent i =new Intent(AddCourses.this, Courses.class);
                    startActivity(i);
                }

            }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    protected void onResume(){
        super.onResume();

    }
}