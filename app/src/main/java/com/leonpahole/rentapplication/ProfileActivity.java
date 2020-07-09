package com.leonpahole.rentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {

    TextView txtFullName, txtUsername;
    TextInputLayout txtNameProfile, txtPasswordProfile, txtEmailProfile, txtPhoneNoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtFullName = findViewById(R.id.txtFullname);
        txtUsername = findViewById(R.id.txtUsername);
        txtNameProfile = findViewById(R.id.txtNameProfile);
        txtPasswordProfile = findViewById(R.id.txtPasswordProfile);
        txtEmailProfile = findViewById(R.id.txtEmailProfile);
        txtPhoneNoProfile = findViewById(R.id.txtPhoneProfile);

        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phoneNo = intent.getStringExtra("phoneNo");

        txtFullName.setText(name);
        txtUsername.setText(username);
        txtEmailProfile.getEditText().setText(email);
        txtNameProfile.getEditText().setText(name);
        txtPasswordProfile.getEditText().setText(password);
        txtPhoneNoProfile.getEditText().setText(phoneNo);
    }
}