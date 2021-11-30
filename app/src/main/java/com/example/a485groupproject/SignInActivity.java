package com.example.a485groupproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignInActivity extends AppCompatActivity {

    public static final String TAG = "SignInActivity";
    private EditText username;
    private EditText password;
    private Button forgotButton;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username = findViewById(R.id.emailTextField);
        password = findViewById(R.id.passwordTextField);
        forgotButton = findViewById(R.id.forgotButton);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick signIn button");
                checkIfFormEmpty();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));

            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    private void signInUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                //TODO: navigate to the main activity if the user has signed in properly
                goProfile();
                Toast.makeText(SignInActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAlert(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void goProfile() {
        Intent i = new Intent(this, UserProfileActivity.class);
        startActivity(i);
        finish();
    }

    private void checkIfFormEmpty() {
        String tempEmail = this.username.getText().toString();
        String tempPass = this.password.getText().toString();

        if( tempEmail.matches("") || tempPass.matches("")) {
            this.showAlert("Oops!", "Please complete the form to continue.");
        }else{
            this.signInUser(tempEmail,tempPass);
        }
    }


}