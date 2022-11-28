package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadnesslearn.classes.UserAuthentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Registration extends AppCompatActivity {
    private UserAuthentification auth_Methods;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText email, name, password, password_conf;
    private TextView tv_match_passw;
    private int text_color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth_Methods = new UserAuthentification();

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edTex_reg_email);
        name = findViewById(R.id.edTex_reg_name);
        password = findViewById(R.id.edTex_reg_password);
        password_conf = findViewById(R.id.edTex_reg_confPassword);
        Button btn_register = findViewById(R.id.btn_reg_register);
        tv_match_passw = findViewById(R.id.tv_reg_match_passwords);
        text_color = tv_match_passw.getCurrentTextColor();

        confirmationPassword();

        btn_register.setOnClickListener(view -> {
            try {
                register();
            } catch (NullPointerException e){
                Toast.makeText(Registration.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(){
        String email_s, name_s, password_s, password_conf_s;
        email_s = email.getText().toString();
        name_s = name.getText().toString();
        password_s = password.getText().toString();
        password_conf_s = password_conf.getText().toString();
        if(email_s.length() == 0 || name_s.length() == 0
                || password_s.length() == 0  || password_conf_s.length()  == 0){
            throw new NullPointerException("Заполните все поля!");
        }
        if (password_s.length() >= 6 && password_s.equals(password_conf_s)
                && auth_Methods.emailValidator(email_s, this)){
            mAuth.createUserWithEmailAndPassword(email_s, password_s)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Registration.this, "Регистрация прошла успешно!",
                                    Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name_s).build();
                            user.updateProfile(profileUpdates);

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            assert user != null;
                            user.sendEmailVerification();
                            startActivity(new Intent(Registration.this, Authentification.class));
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registration.this, "Такой пользователь уже сушествует!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void confirmationPassword(){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAndSetConfMessage();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        password_conf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAndSetConfMessage();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkAndSetConfMessage(){
        String password_s, password_conf_s;
        password_s = password.getText().toString();
        password_conf_s = password_conf.getText().toString();
        if(password_s.equals(password_conf_s) && password_s.length() != 0){
            if(password_s.length() < 6){
                tv_match_passw.setTextColor(Color.RED);
                tv_match_passw.setText("Длина пароля минимум 6 символов");
            }
            else{
                tv_match_passw.setTextColor(Color.GREEN);
                tv_match_passw.setText("Пароли совпадают");
            }
        }
        else if(!password_s.equals(password_conf_s)){
            tv_match_passw.setTextColor(Color.RED);
            tv_match_passw.setText("Пароли не совпадают");
        }
        else if(password_s.length() == 0){
            tv_match_passw.setTextColor(text_color);
            tv_match_passw.setText("Длина пароля минимум 6 символов");
        }
    }
}