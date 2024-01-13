package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.termsapp.R;

import java.time.LocalDate;

import Database.Repository;

public class AddTerms extends AppCompatActivity {
    int id;
    String title;
    EditText termTitle;
    LocalDate start;
    EditText startBox;
    LocalDate end;
    EditText endBox;
    Repository repository;

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

    }
}