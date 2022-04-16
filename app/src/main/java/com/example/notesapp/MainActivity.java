package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.models.Note;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private static final String TAG = "MainActivity";

    private RecyclerView notesRecView;
    private FloatingActionButton fabEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the views
        initViews();
        
        fabEdit.setOnClickListener(view -> handleCreateNote());
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

    private void handleCreateNote(){
        // now show alert dialog
        final EditText edtTxtNote = new EditText(this);

        new AlertDialog.Builder(this)
                .setTitle("Add Note")
                .setView(edtTxtNote)
                .setPositiveButton("Add", (dialogInterface, i) -> {
                    Log.d(TAG, "onEdit: " + edtTxtNote.getText());
                    addNote(edtTxtNote.getText().toString());
                })
                .setNegativeButton("Cancel",null)
                .show();
    }

    private void addNote(String text){

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Note note = new Note(text,false,new Timestamp(new Date()),userId);

        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Note Added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                    }
                });
    }

    private void logoutUser(){
        Toast.makeText(this, "User signed out", Toast.LENGTH_SHORT).show();
        AuthUI.getInstance().signOut(this);
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser()==null){
            // if no user then start login register activity
            startLoginRegisterActivity();
            return;
        }
        firebaseAuth
            .getCurrentUser()
            .getIdToken(true)
            .addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    Log.d(TAG, "onSuccess: "+getTokenResult.getToken());
                }
            });
    }
}