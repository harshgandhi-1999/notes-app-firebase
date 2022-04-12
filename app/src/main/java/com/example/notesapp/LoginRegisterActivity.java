package com.example.notesapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginRegisterActivity extends AppCompatActivity {
    private static final String TAG = "LoginRegisterActivity";

    private Button btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        btnLoginRegister.setOnClickListener(view -> handleLoginRegister());

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            // go back to main activity
            Intent intent = new Intent(this,MainActivity.class);
            //finish this activity
            finish();
        }
    }

    private void handleLoginRegister() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI
                .getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTosAndPrivacyPolicyUrls("https://example.com/terms.htmlm","https://example.com/privacy.html")
                .setLogo(R.drawable.notes)
                .setAlwaysShowSignInMethodScreen(true)
                .build();

        signInLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> onSignInResult(result)
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            Log.d(TAG, "onSignInResult: " + user.getEmail());
            // or we have a new user

            if(user.getMetadata().getCreationTimestamp()==user.getMetadata().getLastSignInTimestamp()){
                // new user
                Toast.makeText(this, "Welcome new user", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Welcome back again",Toast.LENGTH_SHORT).show();
            }

            // make user login to main activity
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            // finish the login activity so that user cannot go back to this activity
            finish();
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.

            if(response==null){
                Log.d(TAG, "onSignInResult: the user has cancelled the signin request");
            }else{
                Log.d(TAG, "onSignInResult: "+response.getError());
            }
        }
    }
}