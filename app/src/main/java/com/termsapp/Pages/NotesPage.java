package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.termsapp.R;

import DAO.NotesDAO;
import Database.Repository;
import entites.Notes;

public class NotesPage extends AppCompatActivity {
    int noteID;
    String theNote;
    EditText note;
    Button save;
    Button cancel;
    int courseId;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        courseId = getIntent().getIntExtra("Course ID", 1);
        note = findViewById(R.id.noteBody);
        theNote = note.getText().toString();
        save = findViewById(R.id.saveNote);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes newNote;
                if(noteID == -1){
                    if(repository.getAllNotes().size() == 0){
                        noteID = 1;
                    }
                    else{
                        noteID = repository.getAllNotes().get(repository.getAllNotes().size() -1).getNoteId() + 1;
                    }
                }
                newNote = new Notes(noteID, theNote, courseId);
                repository.insert(newNote);
                Intent intent = new Intent(NotesPage.this, AddCourses.class);
                startActivity(intent);
            }
        });
        cancel = findViewById(R.id.onCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelButton = new Intent(NotesPage.this, AddCourses.class);
                startActivity(cancelButton);
            }
        });
    }
}