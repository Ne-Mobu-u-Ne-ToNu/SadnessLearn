package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sadnesslearn.classes.UserAuthentification;

public class MainPage extends AppCompatActivity {
    private UserAuthentification authMethods;
    private Button btn_sandBox, btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        btn_sandBox = findViewById(R.id.btn_main_sandbox);
        btn_sandBox.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, Sandbox.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        btn_exit = findViewById(R.id.btn_main_exit);
        btn_exit.setOnClickListener(view -> {
            authMethods = new UserAuthentification();
            authMethods.signOut();
        });
    }
}