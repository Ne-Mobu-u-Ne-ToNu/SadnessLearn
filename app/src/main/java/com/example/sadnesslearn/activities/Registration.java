package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.AnimationHelper;
import com.example.sadnesslearn.classes.SettingsHelper;
import com.example.sadnesslearn.classes.UserAuthentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText email, name, password, password_conf;
    private TextView tv_match_passw;
    private int text_color;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
        setContentView(R.layout.activity_registration);
        init();

        UserAuthentification.confirmationPassword(this, password, password_conf, tv_match_passw, text_color);
        AnimationHelper.buttonAnimation(btn_register, this);

        btn_register.setOnClickListener(view -> {
            try {
                register();
            } catch (NullPointerException e){
                Toast.makeText(Registration.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        Toolbar tlb_registration = findViewById(R.id.tlb_registration);
        setSupportActionBar(tlb_registration);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edTex_reg_email);
        name = findViewById(R.id.edTex_reg_name);
        password = findViewById(R.id.edTex_reg_password);
        password_conf = findViewById(R.id.edTex_reg_confPassword);
        btn_register = findViewById(R.id.btn_reg_register);
        tv_match_passw = findViewById(R.id.tv_reg_match_passwords);
        text_color = tv_match_passw.getCurrentTextColor();
    }

    private void register(){
        String email_s, name_s, password_s, password_conf_s;
        email_s = email.getText().toString().trim();
        name_s = name.getText().toString().trim();
        password_s = password.getText().toString().trim();
        password_conf_s = password_conf.getText().toString().trim();
        if(email_s.length() == 0 || name_s.length() == 0
                || password_s.length() == 0  || password_conf_s.length()  == 0){
            throw new NullPointerException(getResources().getString(R.string.enter_fields));
        }
        if (password_s.length() >= 6 && password_s.equals(password_conf_s)
                && UserAuthentification.emailValidator(email_s, this)){
            mAuth.createUserWithEmailAndPassword(email_s, password_s)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            UserAuthentification.changeUserName(name_s);

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            assert user != null;
                            user.sendEmailVerification();
                            Toast.makeText(this, getResources().getString(R.string.confirm_email), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this, Authentification.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registration.this, getResources().getString(R.string.user_already_exists),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}