package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sadnesslearn.classes.UserAuthentification;

public class MainPage extends AppCompatActivity {
    private UserAuthentification authMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        authMethods = new UserAuthentification();
        authMethods.signOut();
    }
}