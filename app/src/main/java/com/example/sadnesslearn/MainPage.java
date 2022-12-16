package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sadnesslearn.classes.UserAuthentification;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button btn_tasks = findViewById(R.id.btn_main_tasks);
        btn_tasks.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, TaskTypeChoose.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
        Button btn_sandBox = findViewById(R.id.btn_main_sandbox);
        btn_sandBox.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, Sandbox.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        Button btn_exit = findViewById(R.id.btn_main_exit);
        btn_exit.setOnClickListener(view -> {
            UserAuthentification.signOut();
            checkAuthAndRedirect();
        });
    }

    private void checkAuthAndRedirect(){
        if (!UserAuthentification.isSignedIn()){
            startActivity(new Intent(MainPage.this, Authentification.class));
            finish();
        }
    }
}