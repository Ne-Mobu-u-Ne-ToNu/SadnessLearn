package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.UserAuthentification;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    private TextView tv_hello_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        settingsButtonsActions();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        Toolbar tlb_profile = findViewById(R.id.tlb_profile);
        setSupportActionBar(tlb_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tv_hello_user = findViewById(R.id.tv_profile_user_name);
        tv_hello_user.setText(greetingMessage());

        TextView tv_user_mail = findViewById(R.id.tv_profile_user_mail);
        tv_user_mail.setText(UserAuthentification.getUserMail());
    }

    private void settingsButtonsActions() {
        LinearLayout lin_lay_change_name = findViewById(R.id.lin_lay_profile_change_name);
        lin_lay_change_name.setOnClickListener(view -> {
            showNameWindow();
        });

        LinearLayout lin_lay_change_mail = findViewById(R.id.lin_lay_profile_change_mail);
        lin_lay_change_mail.setOnClickListener(view -> {
            showMailWindow();
        });

        LinearLayout lin_lay_change_password = findViewById(R.id.lin_lay_profile_change_password);
        lin_lay_change_password.setOnClickListener(view -> {
            showPasswordWindow();
        });

        LinearLayout lin_lay_exit = findViewById(R.id.lin_lay_profile_sign_out);
        lin_lay_exit.setOnClickListener(view -> {
            showExitWindow();
        });

        LinearLayout lin_lay_delete = findViewById(R.id.lin_lay_profile_delete_account);
        lin_lay_delete.setOnClickListener(view -> {
            showDeleteWindow();
        });
    }

    private void checkAuthAndRedirect(){
        if (!UserAuthentification.isSignedIn()){
            startActivity(new Intent(Profile.this, Authentification.class));
            finish();
        }
    }

    private void showNameWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.change_name));

        View window_name = LayoutInflater.from(this).inflate(R.layout.window_profile_settings, null);

        EditText edTex_name = window_name.findViewById(R.id.edTex_window_profile_settings_1);
        edTex_name.setHint(getResources().getString(R.string.name_hint));

        dialog.setView(window_name);

        dialog.setPositiveButton(getResources().getString(R.string.ok), (dialogInterface, i) -> {
            if (edTex_name.getText().toString().trim().length() != 0) {
                UserAuthentification.changeUserName(edTex_name.getText().toString());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv_hello_user.setText(greetingMessage());
            }
            else {
                Toast.makeText(Profile.this, getResources().getString(R.string.enter_field), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private void showMailWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.change_mail));

        View window_name = LayoutInflater.from(this).inflate(R.layout.window_profile_settings, null);

        EditText edTex_mail = window_name.findViewById(R.id.edTex_window_profile_settings_1);
        edTex_mail.setHint(getResources().getString(R.string.email_hint));

        dialog.setView(window_name);

        dialog.setPositiveButton(getResources().getString(R.string.ok), (dialogInterface, i) -> {
            UserAuthentification.changeUserMail(edTex_mail.getText().toString().trim(), this);
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private void showPasswordWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.change_password));

        View window_name = LayoutInflater.from(this).inflate(R.layout.window_profile_settings, null);

        EditText edTex_passw = window_name.findViewById(R.id.edTex_window_profile_settings_1);
        edTex_passw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edTex_passw.setHint(getResources().getString(R.string.password_hint));

        EditText edTex_passw_conf = window_name.findViewById(R.id.edTex_window_profile_settings_2);
        edTex_passw_conf.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showView(edTex_passw_conf);
        edTex_passw_conf.setHint(getResources().getString(R.string.password_conf_hint));

        TextView tv_info = window_name.findViewById(R.id.tv_window_profile_info);
        int textColor = tv_info.getCurrentTextColor();
        showView(tv_info);

        UserAuthentification.confirmationPassword(this, edTex_passw, edTex_passw_conf, tv_info, textColor);

        dialog.setView(window_name);

        dialog.setPositiveButton(getResources().getString(R.string.ok), (dialogInterface, i) -> {
            UserAuthentification.changeUserPassword(edTex_passw.getText().toString().trim(),
                    edTex_passw_conf.getText().toString().trim(), this);
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private void showExitWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.exit));
        dialog.setMessage(getResources().getString(R.string.sure_to_exit));

        dialog.setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> {
            UserAuthentification.signOut();
            checkAuthAndRedirect();
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private void showDeleteWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.delete_account));
        dialog.setMessage(getResources().getString(R.string.sure_to_delete));

        dialog.setPositiveButton(getResources().getString(R.string.yes), (dialogInterface, i) -> {
            UserAuthentification.deleteAccount(this);
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private String greetingMessage() {
        return getResources().getString(R.string.greeting_message) + " " + UserAuthentification.getUserName() + "!";
    }

    private void showView(View v) {
        v.setVisibility(View.VISIBLE);
    }
}