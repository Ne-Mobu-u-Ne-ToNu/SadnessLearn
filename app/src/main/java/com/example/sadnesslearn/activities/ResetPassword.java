package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.AnimationHelper;
import com.example.sadnesslearn.classes.SettingsHelper;
import com.example.sadnesslearn.classes.UserAuthentification;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
        setContentView(R.layout.activity_reset_password);

        Toolbar tlb_reset_password = findViewById(R.id.tlb_reset_password);
        setSupportActionBar(tlb_reset_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        mAuth = FirebaseAuth.getInstance();

        Button btn_reset_password = findViewById(R.id.btn_res_reset_password);
        AnimationHelper.buttonAnimation(btn_reset_password, this);
        email = findViewById(R.id.edTex_res_email);

        btn_reset_password.setOnClickListener(view -> {
            try {
                resetPassword();
            }
            catch (NullPointerException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetPassword(){
        String email_s = email.getText().toString().trim();
        if(email_s.length() == 0){
            throw new NullPointerException(getResources().getString(R.string.enter_field));
        }

        if(UserAuthentification.emailValidator(email_s, this)){
            mAuth.sendPasswordResetEmail(email_s)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, getResources().getString(R.string.letter_sent),
                                    Toast.LENGTH_SHORT).show();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(ResetPassword.this, Authentification.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ResetPassword.this, getResources().getString(R.string.user_not_exist),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}