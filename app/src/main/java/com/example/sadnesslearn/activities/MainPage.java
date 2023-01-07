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
import com.example.sadnesslearn.classes.SettingsHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
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

        LinearLayout btn_profile = findViewById(R.id.lin_lay_main_btn_profile);
        btn_profile.setOnClickListener(view -> {
            startActivity(new Intent(MainPage.this, Profile.class));
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        });
    }

    private void showLanguageWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.programming_language));
        dialog.setMessage(getResources().getString(R.string.choose_programming_language));

        View window_choose_lang = LayoutInflater.from(this).inflate(R.layout.window_choose_prog_language, null);
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

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (SettingsHelper.settingsChanged(this)) {
            startActivity(getIntent());
            finish();
        }
    }
}