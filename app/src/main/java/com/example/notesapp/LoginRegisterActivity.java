package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class LoginRegisterActivity extends AppCompatActivity {

    private Button btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        btnLoginRegister.setOnClickListener(view -> handleLoginRegister());
    }

    private void handleLoginRegister() {

    }
}