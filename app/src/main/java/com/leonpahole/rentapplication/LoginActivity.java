package com.leonpahole.rentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnNewUserSignUp, loginBtn;
    TextInputLayout username, password;
    TextView logoText, sloganText;
    ImageView logoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        logoImg = findViewById(R.id.imgLoginLogo);
        logoText = findViewById(R.id.txtLoginLogo);
        ;
        sloganText = findViewById(R.id.txtLoginSlogan);
        username = findViewById(R.id.iptUsername);
        password = findViewById(R.id.iptPassword);
        loginBtn = findViewById(R.id.btnLogin);
        btnNewUserSignUp = findViewById(R.id.btnNewUserSignUp);

        btnNewUserSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(logoImg, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_input");
                pairs[4] = new Pair<View, String>(password, "password_input");
                pairs[5] = new Pair<View, String>(loginBtn, "go_button");
                pairs[6] = new Pair<View, String>(btnNewUserSignUp, "login_signup_button");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUsername() | !validatePassword()) {
                    return;
                } else {
                    isUser();
                }
            }
        });
    }

    private void isUser() {
        final String usernameVal = username.getEditText().getText().toString();
        final String passwordVal = password.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(usernameVal);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(usernameVal).child("password").getValue(String.class);
                    if(passwordFromDB.equals(passwordVal)) {

                        String nameFromDB = dataSnapshot.child(usernameVal).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(usernameVal).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(usernameVal).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(usernameVal).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }
                    else {
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No such user");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {

            username.setError("Cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}