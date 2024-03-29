package com.example.sadnesslearn.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.activities.Authentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

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

    public static String getUID() {
        mAuth = FirebaseAuth.getInstance();
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
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

    public static void deleteAccount(Activity context) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance()
                .getReference(Constants.USERS_KEY).child(UserAuthentification.getUID());

        databaseRef.child(Constants.PROFILE_PHOTO_KEY).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserProfileInformation.ProfilePhoto photo = task.getResult().getValue(UserProfileInformation.ProfilePhoto.class);
                if (photo != null) {
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(photo.ref);
                    reference.delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            deleteUser(context);
                        } else if (!task.isSuccessful()) {
                            Toast.makeText(context, context.getResources().getString(R.string.error_deleting_photo), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    deleteUser(context);
                }
            } else {
                deleteUser(context);
            }
        });

    }

    private static void deleteUser(Activity context) {
        DatabaseReference fullDatabaseRef = FirebaseDatabase.getInstance()
                .getReference(Constants.USERS_KEY).child(UserAuthentification.getUID());
        fullDatabaseRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                user.delete()
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                signOut();
                                Toast.makeText(context, context.getResources().getString(R.string.account_deleted), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(context, Authentification.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                                context.finish();
                            }
                            else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_try_sign_in), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.error_try_sign_in), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void changeUserMail(String email, Activity context) {
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
                                Intent i = new Intent(context, Authentification.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                                context.finish();
                            }
                            else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_try_sign_in), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else {
            Toast.makeText(context, context.getResources().getString(R.string.enter_field), Toast.LENGTH_SHORT).show();
        }
    }

    public static void changeUserPassword(String password, String password_conf, Activity context) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(password.length() != 0 & password_conf.length() != 0) {
            if(password.equals(password_conf) & password.length() >= 6){
                assert user != null;
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, context.getResources().getString(R.string.sign_in_again),
                                        Toast.LENGTH_SHORT).show();
                                signOut();
                                Intent i = new Intent(context, Authentification.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                                context.finish();
                            }
                            else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_try_sign_in), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else {
            Toast.makeText(context, context.getResources().getString(R.string.enter_fields), Toast.LENGTH_SHORT).show();
        }
    }

    public static void confirmationPassword(Context context, EditText password, EditText password_conf, TextView tv_match_passw, int text_color){
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAndSetConfMessage(context, password, password_conf, tv_match_passw, text_color);
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
                checkAndSetConfMessage(context, password, password_conf, tv_match_passw, text_color);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static void checkAndSetConfMessage(Context context, EditText password, EditText password_conf, TextView tv_match_passw, int text_color){
        String password_s, password_conf_s;
        password_s = password.getText().toString();
        password_conf_s = password_conf.getText().toString();
        if(password_s.equals(password_conf_s) && password_s.length() != 0){
            if(password_s.length() < 6){
                tv_match_passw.setTextColor(Color.RED);
                tv_match_passw.setText(context.getResources().getString(R.string.password_length));
            }
            else{
                tv_match_passw.setTextColor(Color.GREEN);
                tv_match_passw.setText(context.getResources().getString(R.string.passwords_match));
            }
        }
        else if(!password_s.equals(password_conf_s)){
            tv_match_passw.setTextColor(Color.RED);
            tv_match_passw.setText(context.getResources().getString(R.string.passwords_not_match));
        }
        else if(password_s.length() == 0){
            tv_match_passw.setTextColor(text_color);
            tv_match_passw.setText(context.getResources().getString(R.string.password_length));
        }
    }

    public static boolean isNetworkAvailable(Activity context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
