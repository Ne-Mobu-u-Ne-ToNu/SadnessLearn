package com.example.sadnesslearn.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.InterfaceLangArrayAdapter;
import com.example.sadnesslearn.classes.InterfaceLangItem;
import com.example.sadnesslearn.classes.SettingsHelper;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Settings extends AppCompatActivity implements ColorPickerDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SettingsHelper.themeExists(this)) {
            setTheme(SettingsHelper.getThemeFromPrefs(this));
        }
        setContentView(R.layout.activity_settings);

        init();
        settingsButtonsActions();
    }

    private void init() {
        Toolbar tlb_settings = findViewById(R.id.tlb_settings);
        setSupportActionBar(tlb_settings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
    }

    private void settingsButtonsActions() {
        LinearLayout lin_lay_theme = findViewById(R.id.lin_lay_settings_theme);
        lin_lay_theme.setOnClickListener(view -> showThemeDialog());

        LinearLayout lin_lay_lang = findViewById(R.id.lin_lay_settings_lang);
        lin_lay_lang.setOnClickListener(view -> showLangDialog());
    }

    private void showThemeDialog() {
        int[] presets = {getResources().getColor(R.color.primaryRed), getResources().getColor(R.color.primary),
                getResources().getColor(R.color.primaryPurple), getResources().getColor(R.color.primaryDeepPurple),
                getResources().getColor(R.color.primaryIndigo), getResources().getColor(R.color.primaryBlue),
                getResources().getColor(R.color.primaryCyan), getResources().getColor(R.color.primaryTeal),
                getResources().getColor(R.color.primaryBlueGrey), getResources().getColor(R.color.primaryGreen),
                getResources().getColor(R.color.primaryLime), getResources().getColor(R.color.primaryYellow),
                getResources().getColor(R.color.primaryOrange), getResources().getColor(R.color.primaryDeepOrange)};

        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setColor(presets[0])
                .setPresets(presets)
                .setAllowCustom(false)
                .setShowColorShades(false)
                .setColorShape(ColorShape.CIRCLE)
                .setDialogId(1)
                .show(this);

    }

    private void showLangDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.lang));
        dialog.setMessage(getResources().getString(R.string.choose_interf_lang));

        View window_choose_lang = LayoutInflater.from(this).inflate(R.layout.window_choose_interface_language, null);
        dialog.setView(window_choose_lang);

        ListView lv_langs = window_choose_lang.findViewById(R.id.lv_window_choose_interface_lang);
        InterfaceLangArrayAdapter adapter = new InterfaceLangArrayAdapter(this, getLangList());
        lv_langs.setAdapter(adapter);
        lv_langs.setOnItemClickListener((adapterView, view, i, l) -> {
            CheckBox cb_lang = adapter.getView(i, view, adapterView).findViewById(R.id.cb_item_interface_lang);
            adapter.setSelectedPosition((Integer)cb_lang.getTag());
        });

        dialog.setPositiveButton(getResources().getString(R.string.ok), (dialogInterface, i) -> {
            String locale = SettingsHelper.getStringLocaleFromPosition(adapter.getSelectedPosition());
            SettingsHelper.saveLocale(locale, this);
            SettingsHelper.changeLocale(locale, this);
            recreate();
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());

        dialog.show();
    }

    private List<InterfaceLangItem> getLangList() {
        String[] languages = getResources().getStringArray(R.array.interface_langs);
        int[] imageIds = {R.drawable.ic_flag_ru, R.drawable.ic_flag_en};

        List<InterfaceLangItem> result = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            InterfaceLangItem item = new InterfaceLangItem(languages[i], imageIds[i]);
            result.add(item);
        }

        return result;
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        SettingsHelper.saveTheme(this, SettingsHelper.getThemeFromColor(this, color));
        recreate();
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}