package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.UserAuthentification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        LinearLayout lin_lay_plot = findViewById(R.id.lin_lay_main_btn_comics);
        lin_lay_plot.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, ComicsActivity.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        LinearLayout lin_lay_tasks = findViewById(R.id.lin_lay_main_btn_tasks);
        lin_lay_tasks.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, TaskTypeChoose.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        LinearLayout lin_lay_sandBox = findViewById(R.id.lin_lay_main_btn_sandBox);
        lin_lay_sandBox.setOnClickListener(view -> showLanguageWindow());

        LinearLayout lin_lay_settings = findViewById(R.id.lin_lay_main_btn_settings);
        lin_lay_settings.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, Settings.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        LinearLayout btn_exit = findViewById(R.id.lin_lay_main_btn_profile);
        btn_exit.setOnClickListener(view -> {
            UserAuthentification.signOut();
            checkAuthAndRedirect();
        });
    }

    private void checkAuthAndRedirect(){
        if (!UserAuthentification.isSignedIn()){
            startActivity(new Intent(MainPage.this, Authentification.class));
            finish();
        }
    }

    private void showLanguageWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Язык программирования");
        dialog.setMessage("Выберите язык программирования");

        View window_choose_lang = LayoutInflater.from(this).inflate(R.layout.window_choose_language, null);
        dialog.setView(window_choose_lang);

        List<String> listLang = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        ListView lv_langs = window_choose_lang.findViewById(R.id.lv_window_choose_lang);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listLang);
        lv_langs.setAdapter(adapter);

        lv_langs.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainPage.this, Sandbox.class);
            intent.putExtra("language", listLang.get(i));
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });

        dialog.setNegativeButton("Отмена", (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }
}