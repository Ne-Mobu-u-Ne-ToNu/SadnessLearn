package com.example.sadnesslearn.classes;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import com.example.sadnesslearn.Authentification;
import com.example.sadnesslearn.ResetPassword;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class UserAuthentification {
    private static FirebaseAuth mAuth;

    public static boolean isSignedIn(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
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
}
