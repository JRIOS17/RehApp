package com.example.a485groupproject;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class SignUpActivity extends AppCompatActivity {



    private ImageView profileImageView;
    private Button selectImageButton;
    private EditText emailTextField;
    private EditText userTypeTextField;
    private EditText passwordTextField;
    private Button signUpButton;
    private Button signInButton;
    private ProgressDialog progressDialog;
    String path;
    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(SignUpActivity.this);

        profileImageView = findViewById(R.id.profileImageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        emailTextField = findViewById(R.id.emailTextField);
        userTypeTextField = findViewById(R.id.userTypeTextField);
        passwordTextField = findViewById(R.id.passwordTextField);
        signUpButton = findViewById(R.id.signUpButton);
        signInButton = findViewById(R.id.signInButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkIfFormEmpty();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


    private void signUserUp() {

        progressDialog.show();
        ParseUser user = new ParseUser();
        // Set the user's username and password, which can be obtained by a forms
        user.setUsername(emailTextField.getText().toString());
        user.setPassword(passwordTextField.getText().toString());
        user.put("userType", userTypeTextField.getText().toString());
        user.put("email", emailTextField.getText().toString());

        user.put("photoURL", path);
        user.signUpInBackground(e -> {
            progressDialog.dismiss();
            if (e == null) {
               goToUserProfile();
            } else {
                ParseUser.logOut();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("SignUp", e.getMessage());
            }
        });
    }


    private void showAlert(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
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


    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    profileImageView.setImageURI(selectedImageUri);
                    Log.i("Image View ", selectedImageUri.toString());
                    path = selectedImageUri.toString();
                   // Log.i("ImagePath: ", getImagePath(selectedImageUri));


                    Log.i("ImagePath: ", selectedImageUri.getPath());

                }
            }
        }


    }


    private void goToUserProfile() {
        Intent i = new Intent(this, UserProfileActivity.class);
        startActivity(i);
        finish();
    }

    private void checkIfFormEmpty() {
        String tempEmail = this.emailTextField.getText().toString();
        String tempPass = this.passwordTextField.getText().toString();
        String tempRole = this.userTypeTextField.getText().toString();
        if( tempEmail.matches("") || tempPass.matches("") || tempRole.matches("")) {
            this.showAlert("Oops!", "Please complete the form to continue.");
        }else{
            this.signUserUp();
        }
    }



}
