package com.termsapp.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.termsapp.R;

import Database.Repository;
import entites.Notes;

public class NotesPage extends AppCompatActivity {
    int noteID;
    String theNote;
    EditText note;
    Button save;
    Button cancel;
    Button share;
    Button delete;
    int courseId;
    Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        repository = new Repository(getApplication());
        noteID = getIntent().getIntExtra("Note ID", 0);
        courseId = getIntent().getIntExtra("Course Id", 0);
        note = findViewById(R.id.noteBody);
        theNote = getIntent().getStringExtra("Note Body");
        note.setText(theNote);
        save = findViewById(R.id.saveNote);

        save.setOnClickListener(v -> {
            Notes newNote;
            if (note.getText().toString().isEmpty()) {
                Toast.makeText(NotesPage.this, "Cannot save a note without text.", Toast.LENGTH_LONG).show();
            }
            else{
                if (noteID == 0) {
                    if (repository.getAllNotes().size() == 0) {
                        noteID = 1;
                    }
                    else {
                        noteID = repository.getAllNotes().get(repository.getAllNotes().size() - 1).getNoteId() + 1;
                    }
                    newNote = new Notes(noteID, note.getText().toString(), courseId);
                    repository.insert(newNote);
                    finishAfterTransition();
                }
                else{
                    newNote = new Notes(noteID, note.getText().toString(), courseId);
                    repository.update(newNote);
                    finishAfterTransition();
                }
            }
        });
        cancel = findViewById(R.id.onCancel);
        cancel.setOnClickListener(v -> {
            Intent cancelButton = new Intent(NotesPage.this, AddCourses.class);
            startActivity(cancelButton);
        });
        share = findViewById(R.id.shareNote);
        share.setOnClickListener(v -> {
            if(theNote != null) {
                Intent sendNote = new Intent();
                sendNote.setAction(Intent.ACTION_SEND);
                sendNote.putExtra(Intent.EXTRA_TEXT, theNote);
                sendNote.putExtra(Intent.EXTRA_TITLE, "A Shared Note!");
                sendNote.setType("text/plain");
                Intent sending = Intent.createChooser(sendNote, null);
                startActivity(sending);
            }
            else{
                Toast.makeText(NotesPage.this, "Cannot share a note without text.", Toast.LENGTH_LONG).show();
            }
        });
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            for(Notes notes : repository.getAllNotes()){
                if(notes.getNoteId() == noteID){
                    repository.delete(notes);
                    Toast.makeText(NotesPage.this, "No such Note.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}