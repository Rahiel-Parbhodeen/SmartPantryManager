package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.PantryDAO;
import com.example.smartpantrymanager.database.RecipeDAO;
import android.content.SharedPreferences;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import com.example.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private TextView txtPantrySummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtPantrySummary = findViewById(R.id.txtPantrySummary);

        // Add the starter recipe collection the first time the app opens.
        new RecipeDAO(this).seedRecipes();

        Button btnHomePantry = findViewById(R.id.btnHomePantry);
        Button btnHomeRecipes = findViewById(R.id.btnHomeRecipes);
        Button btnHomeSettings = findViewById(R.id.btnHomeSettings);

        btnHomePantry.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, MainActivity.class)));

        btnHomeRecipes.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, SuggestedRecipesActivity.class)));

        btnHomeSettings.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        PantryDAO pantryDAO = new PantryDAO(this);
        ArrayList<PantryItem> items = pantryDAO.getAllItems();
        int expiringSoon = 0;
        int expired = 0;
        LocalDate today = LocalDate.now();
        for (PantryItem item : items) {
            String expiry = item.getExpiryDate();
            if (expiry == null || expiry.trim().isEmpty()) {
                continue;
            }
            try {
                LocalDate expiryDate = LocalDate.parse(expiry.trim());
                if (expiryDate.isBefore(today)) {
                    expired++;
                } else if (!expiryDate.isAfter(today.plusDays(3))) {
                    expiringSoon++;
                }
            } catch (DateTimeParseException ignored) {
                // Invalid legacy dates are ignored here and can be corrected by editing the item.
            }
        }
        SharedPreferences preferences = getSharedPreferences("smart_pantry_settings", MODE_PRIVATE);
        boolean remindersEnabled = preferences.getBoolean("expiry_reminders_enabled", true);
        String summary = "You currently have " + items.size() + " ingredient(s) in your pantry.";
        if (remindersEnabled && (expiringSoon > 0 || expired > 0)) {
            summary += "\n\nExpiry check: " + expiringSoon + " expiring within 3 days, " + expired + " expired.";
        } else if (items.isEmpty()) {
            summary += "\nAdd ingredients to start finding recipes.";
        }
        txtPantrySummary.setText(summary);
    }
}
