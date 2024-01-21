package com.termsapp.Pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.termsapp.R;

import java.util.List;

import Database.Repository;
import entites.TermClass;

public class Terms extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        FloatingActionButton addTerms = findViewById(R.id.addTerms);
        addTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terms.this, AddTerms.class);
                startActivity(intent);
            }
        });
        repository = new Repository(getApplication());
        List<TermClass> allTerms = repository.getAllTerms();
        RecyclerView rv = findViewById(R.id.TermsView);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        rv.setAdapter(termsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter.setTermClassList(allTerms);
    }
    @Override
    protected void onResume(){
        super.onResume();
        repository = new Repository(getApplication());
        List<TermClass> allTerms = repository.getAllTerms();
        RecyclerView rv = findViewById(R.id.TermsView);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        rv.setAdapter(termsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter.setTermClassList(allTerms);
    }
}