package com.example.sadnesslearn.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.activities.Authentification;
import com.example.sadnesslearn.activities.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserAuthentification {
    private static FirebaseAuth mAuth;

    public static boolean isSignedIn(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public static boolean isVerified(Activity currentActivity){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        if(!currentUser.isEmailVerified()){
            Toast.makeText(currentActivity, currentActivity.getResources().getString(R.string.confirm_email), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean emailValidator(String email, Context context){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, context.getResources().getString(R.string.wrong_email_type), Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    public static void signOut(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    public static String getUserName() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        return currentUser.getDisplayName();
    }

    public static String getUserMail() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        return currentUser.getEmail();
    }

    public static void changeUserName(String name) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        assert user != null;
        user.updateProfile(profileUpdates);
    }

    public static void deleteAccount(Context context) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        assert user != null;
        user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signOut();
                        Toast.makeText(context, "Аккаунт удалён!", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Authentification.class));
                    }
                    else {
                        Toast.makeText(context, "Ошибка, попробуйте перезайти в свой аккаунт", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void changeUserMail(String email, Context context) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (email.length() != 0) {
            if (emailValidator(email, context)) {
                assert user != null;
                user.updateEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                user.sendEmailVerification();
                                Toast.makeText(context, context.getResources().getString(R.string.confirm_email),
                                        Toast.LENGTH_SHORT).show();
                                signOut();
                                context.startActivity(new Intent(context, Authentification.class));
                            }
                            else {
                                Toast.makeText(context, "Ошибка, попробуйте перезайти в свой аккаунт", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else {
            Toast.makeText(context, "Введите поле!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void changeUserPassword(String password, String password_conf, Context context) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(password.length() != 0 & password_conf.length() != 0) {
            if(password.equals(password_conf) & password.length() >= 6){
                assert user != null;
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Перезайдите в учётную запись!",
                                        Toast.LENGTH_SHORT).show();
                                signOut();
                                context.startActivity(new Intent(context, Authentification.class));
                            }
                            else {
                                Toast.makeText(context, "Ошибка, попробуйте перезайти в свой аккаунт", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else {
            Toast.makeText(context, "Введите все поля!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void confirmationPassword(EditText password, EditText password_conf, TextView tv_match_passw, int text_color){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAndSetConfMessage(password, password_conf, tv_match_passw, text_color);
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
                checkAndSetConfMessage(password, password_conf, tv_match_passw, text_color);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void checkAndSetConfMessage(EditText password, EditText password_conf, TextView tv_match_passw, int text_color){
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
