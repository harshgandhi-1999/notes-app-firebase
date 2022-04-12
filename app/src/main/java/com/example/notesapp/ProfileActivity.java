package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Button btnUpdateProfile;
    private CircleImageView imgProfilePicture;
    private TextInputEditText inputDisplayName;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        inputDisplayName = findViewById(R.id.inputDisplayName);
        loader = findViewById(R.id.loader);

        btnUpdateProfile.setOnClickListener(view -> updateProfile());
    }

    private void updateProfile() {
        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
    }
}