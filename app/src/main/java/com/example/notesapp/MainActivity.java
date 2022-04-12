package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView notesRecView;
    private FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the views
        initViews();
        
        fabEdit.setOnClickListener(view -> onEdit());

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            // if no user then start login register activity
            startLoginRegisterActivity();
        }
    }

    private void startLoginRegisterActivity() {

        Intent intent =  new Intent(this,LoginRegisterActivity.class);
        startActivity(intent);

        // also finish the current activity
        finish();
    }

    private void initViews(){
        fabEdit = findViewById(R.id.fabEdit);
        notesRecView = findViewById(R.id.notesRecView);
    }

    private void onEdit(){
        Toast.makeText(this, "fab clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_logout:
                logoutUser();
                return true;
            case R.id.action_profile:
                Toast.makeText(this, "ACTION PROFILE", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser(){
        Toast.makeText(this, "User signed out", Toast.LENGTH_SHORT).show();
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            // start the login and register activity
                            startLoginRegisterActivity();
                        }else{
                            Log.e(TAG, "onComplete: "+task.getException());
                        }
                    }
                });
    }
}