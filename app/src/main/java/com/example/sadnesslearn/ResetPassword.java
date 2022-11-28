package com.example.sadnesslearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sadnesslearn.classes.UserAuthentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    private UserAuthentification auth_Methods;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        auth_Methods = new UserAuthentification();

        mAuth = FirebaseAuth.getInstance();

        Button btn_reset_password = findViewById(R.id.btn_res_reset_password);
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

    private boolean emailValidator(String email){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Введите правильный email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void resetPassword(){
        String email_s = email.getText().toString();
        if(email_s.length() == 0){
            throw new NullPointerException("Заполните поле!");
        }

        if(emailValidator(email_s)){
            mAuth.sendPasswordResetEmail(email_s)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Сообщение отправлено",
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
                            Toast.makeText(ResetPassword.this, "Пользователя с таким email не существует!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}