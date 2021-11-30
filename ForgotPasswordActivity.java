package com.example.a485groupproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailTextField;
    private Button resetPasswordButton;
    private Button cancelButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailTextField = findViewById(R.id.emailTextField);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        cancelButton = findViewById(R.id.cancelButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.requestPasswordResetInBackground(emailTextField.getText().toString(),
                        new RequestPasswordResetCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    showAlert("Success!", "Follow the instructions sent to your email.");
                                } else {
                                    showAlert("Failed!", e.toString());
                                }
                            }
                        });
                    }
                 });
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showAlert(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        finish();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}

