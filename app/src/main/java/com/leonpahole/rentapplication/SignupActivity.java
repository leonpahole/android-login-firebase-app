package com.leonpahole.rentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        regName = findViewById(R.id.regIptName);
        regUsername = findViewById(R.id.regIptUsername);
        regEmail = findViewById(R.id.regIptEmail);
        regPhoneNo = findViewById(R.id.regIptPhone);
        regPassword = findViewById(R.id.regIptPassword);

        regBtn = findViewById(R.id.btnSignup);
        regToLoginBtn = findViewById(R.id.btnToLogin);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validateEmail() | !validatePassword() | !validatePhoneNo() | !validateUsername()) {
                    return;
                }

                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelper user = new UserHelper(name, username, email, phoneNo, password);

                reference.child(username).setValue(user);
            }
        });
    }

    private Boolean validateName() {

        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validateUsername() {

        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White space not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validateEmail() {

        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePhoneNo() {

        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }


    private Boolean validatePassword() {

        String val = regPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (val.length() < 6) {
            regPassword.setError("Password should be at least 6 characters long");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }
}