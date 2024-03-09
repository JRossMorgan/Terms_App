package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Spinner spinAssessment;
    Spinner spinNote;

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
                long startTrigger = startAlert.getTime();
                Intent startIntent = new Intent(AddCourses.this, CourseStart.class);
                startIntent.putExtra("Start Course", courseTitle.getText() + " has started");
                PendingIntent sendStart = PendingIntent.getBroadcast(AddCourses.this, ++MainActivity.alertCount, startIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, startTrigger, sendStart);
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
                long endTrigger = endAlert.getTime();
                Intent endIntent = new Intent(AddCourses.this, CourseEnd.class);
                endIntent.putExtra("End Course", courseTitle.getText() + " has ended.");
                PendingIntent sendEnd = PendingIntent.getBroadcast(AddCourses.this, ++MainActivity.alertCount, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, endTrigger, sendEnd);
            }
        }
        catch (Exception what){
            what.printStackTrace();
        }
        ArrayList<AssessmentClass> assessmentList = new ArrayList<>();
        for(AssessmentClass a: repository.getAllAssessments()){
            if(a.getCourseId() == id){
                assessmentList.add(a);
            }
        }
        ArrayList<String> assessmentName = new ArrayList<>();
        for(AssessmentClass b: assessmentList){
            assessmentName.add(b.getTitle());
        }
        ArrayAdapter assessAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, assessmentName);
        spinAssessment = findViewById(R.id.assessmentSpinner);
        spinAssessment.setAdapter(assessAdapter);

        ArrayList<Notes> noteAdapter = new ArrayList<>();
        for(Notes note : repository.getAllNotes()){
            if(note.getCourseTitle().equals(title)){
                noteAdapter.add(note);
            }
        }
        ArrayList<String> noteName = new ArrayList<>();
        for(Notes n : noteAdapter){
            noteName.add(n.getBody());
        }
        ArrayAdapter adaptNotes = new ArrayAdapter(this, android.R.layout.simple_spinner_item, noteName);
        spinNote = findViewById(R.id.noteSpinner);
        spinNote.setAdapter(adaptNotes);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(menuItem.getItemId() == R.id.assessment){
            Intent assessmentIntent = new Intent(AddCourses.this, AddAssessments.class);
            assessmentIntent.putExtra("Course ID", id);
            startActivity(assessmentIntent);
            return true;
        }
        if(menuItem.getItemId() == R.id.addNote){
            if(title.isEmpty()){
                Toast.makeText(AddCourses.this, "Please enter a course title", Toast.LENGTH_LONG).show();
            }
            else{
                Intent noteIntent = new Intent(AddCourses.this, NotesPage.class);
                noteIntent.putExtra("Title", title);
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
                    if(n.getCourseTitle() == currentCourse.getTitle()){
                        numNotes++;
                    }
                }
            }
            if(numAssessments == 0 && numNotes == 0){
                if(currentCourse != null){
                    repository.delete(currentCourse);
                    Toast.makeText(AddCourses.this, currentCourse.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
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
            int minNotes = 0;
            for(Notes note : repository.getAllNotes()){
                if(note.getCourseTitle().equals(title)){
                    minNotes++;
                }
            }
            if(minNotes == 0){
                Toast.makeText(AddCourses.this, "Must add at least one note to the course.", Toast.LENGTH_LONG).show();
            }
            else{
                if(id == 0){
                    if(repository.getAllCourses().size() ==0){
                        id = 1;
                    }
                    else{
                        id = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseId() +1;
                    }
                    CourseClass courseClass = new CourseClass(id, title, start, end, status, instructorName, instructorPhone, instructorEmail, termId);
                    repository.insert(courseClass);
                    Intent i =new Intent(AddCourses.this, Courses.class);
                    startActivity(i);
                }
                else{
                    CourseClass updateCourse = new CourseClass(id, title, start, end, status, instructorName, instructorPhone, instructorEmail, termId);
                    repository.update(updateCourse);
                    Intent i =new Intent(AddCourses.this, Courses.class);
                    startActivity(i);
                }
            }

        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    protected void onResume(){
        super.onResume();
        repository = new Repository(getApplication());
        ArrayList<AssessmentClass> assessmentList = new ArrayList<>();
        for(AssessmentClass a: repository.getAllAssessments()){
            if(a.getCourseId() == id){
                assessmentList.add(a);
            }
        }
        ArrayList<String> assessmentName = new ArrayList<>();
        for(AssessmentClass b: assessmentList){
            assessmentName.add(b.getTitle());
        }
        ArrayAdapter assessAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, assessmentName);
        spinAssessment.setAdapter(assessAdapter);

        ArrayList<Notes> noteAdapter = new ArrayList<>();
        for(Notes note : repository.getAllNotes()){
            if(note.getCourseTitle().equals(title)){
                noteAdapter.add(note);
            }
        }
        ArrayList<String> noteName = new ArrayList<>();
        for(Notes n : noteAdapter){
            noteName.add(n.getBody());
        }
        ArrayAdapter adaptNotes = new ArrayAdapter(this, android.R.layout.simple_spinner_item, noteName);
        spinNote.setAdapter(adaptNotes);
    }
}