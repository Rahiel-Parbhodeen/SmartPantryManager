package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "smart_pantry_settings";
    private static final String KEY_EXPIRY_REMINDERS = "expiry_reminders_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchExpiryReminders = findViewById(R.id.switchExpiryReminders);
        Button btnSettingsHome = findViewById(R.id.btnSettingsHome);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        switchExpiryReminders.setChecked(preferences.getBoolean(KEY_EXPIRY_REMINDERS, true));

        switchExpiryReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.edit().putBoolean(KEY_EXPIRY_REMINDERS, isChecked).apply();
            Toast.makeText(this,
                    isChecked ? "Expiry reminders enabled" : "Expiry reminders disabled",
                    Toast.LENGTH_SHORT).show();
        });

        btnSettingsHome.setOnClickListener(v -> finish());
    }
}
