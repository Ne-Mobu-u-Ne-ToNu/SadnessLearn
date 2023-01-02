package com.example.sadnesslearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.AnimationHelper;
import com.example.sadnesslearn.classes.UserAuthentification;

public class Authentification extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!onStartCheck()){
            init();
        }
    }

    private void init() {
        setTheme(R.style.Theme_SadnessLearn_Pink);
        setContentView(R.layout.activity_authentification);

        Button btn_register = findViewById(R.id.btn_auth_register);
        AnimationHelper.buttonAnimation(btn_register, this);
        Button btn_sign_in = findViewById(R.id.btn_auth_sign_in);
        AnimationHelper.buttonAnimation(btn_sign_in, this);

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

    private boolean onStartCheck() {
        // Check if user is signed in (non-null) and update UI accordingly.
        if(UserAuthentification.isSignedIn()){
            if(UserAuthentification.isVerified(this)){
                startActivity(new Intent(this, MainPage.class));
                return true;
            }
            else {
                UserAuthentification.signOut();
                return false;
            }
        }
        return false;
    }
}