package com.example.a485groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class UserProfileActivity extends AppCompatActivity {
    public static final String TAG = "UserProfileActivity";
    private EditText name;
    private EditText username;
    private Button btnEdit;
    private EditText college;
    private EditText bio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
    }
}
