package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.UserAuthentification;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        Toolbar tlb_profile = findViewById(R.id.tlb_profile);
        setSupportActionBar(tlb_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView tv_hello_user = findViewById(R.id.tv_profile_user_name);
        tv_hello_user.setText("Добро пожаловать, " + UserAuthentification.getUserName() + "!");

        TextView tv_user_mail = findViewById(R.id.tv_profile_user_mail);
        tv_user_mail.setText(UserAuthentification.getUserMail());
    }

    private void checkAuthAndRedirect(){
        if (!UserAuthentification.isSignedIn()){
            startActivity(new Intent(Profile.this, Authentification.class));
            finish();
        }
    }
}