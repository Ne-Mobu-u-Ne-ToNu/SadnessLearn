package com.example.sadnesslearn.classes;

import android.app.Activity;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
            Toast.makeText(currentActivity, "Подтверите ваш email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean emailValidator(String email, Activity currentActivity){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(currentActivity, "Введите правильный email!", Toast.LENGTH_SHORT).show();
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
}
