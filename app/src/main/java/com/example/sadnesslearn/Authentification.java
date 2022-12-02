package com.example.sadnesslearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sadnesslearn.classes.UserAuthentification;

public class Authentification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        Button btn_register = findViewById(R.id.btn_auth_register);
        Button btn_sign_in = findViewById(R.id.btn_auth_sign_in);

        btn_register.setOnClickListener(view -> {
            startActivity(new Intent(
                    Authentification.this, Registration.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        btn_sign_in.setOnClickListener(view -> {
            startActivity(new Intent(
                    Authentification.this, SignIn.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(UserAuthentification.isSignedIn()){
            startActivity(new Intent(this, MainPage.class));
        }
    }
}