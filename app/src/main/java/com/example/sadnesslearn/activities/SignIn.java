package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.UserAuthentification;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,  password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar tlb_sign_in = findViewById(R.id.tlb_sign_in);
        setSupportActionBar(tlb_sign_in);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edTex_sign_email);
        password = findViewById(R.id.edTex_sign_password);

        TextView tv_reset_password = findViewById(R.id.tv_sign_in_reset_pass);

        tv_reset_password.setOnClickListener(view -> {
            startActivity(new Intent(SignIn.this, ResetPassword.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        Button btn_sign_in = findViewById(R.id.btn_s_sign_in);

        btn_sign_in.setOnClickListener(view -> {
            try{
                signIn();
            } catch (NullPointerException e){
                Toast.makeText(SignIn.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void signIn(){
        String email_s, password_s;
        email_s = email.getText().toString();
        password_s = password.getText().toString();
        if(email_s.length() == 0 || password_s.length() == 0){
            throw new NullPointerException("Заполните все поля!");
        }
        mAuth.signInWithEmailAndPassword(email_s, password_s)
                .addOnCompleteListener(SignIn.this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        if(UserAuthentification.isVerified(this)){
                            Toast.makeText(SignIn.this, "Вход выполнен!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignIn.this, Authentification.class));
                            finish();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignIn.this, "Неверный email или пароль!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}