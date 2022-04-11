package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecView;
    private FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // intialize the views
        initViews();
        
        fabEdit.setOnClickListener(view -> onEdit());
    }

    private void initViews(){
        fabEdit = findViewById(R.id.fabEdit);
        notesRecView = findViewById(R.id.notesRecView);
    }

    private void onEdit(){
        Toast.makeText(this, "fab clicked", Toast.LENGTH_SHORT).show();
    }
}