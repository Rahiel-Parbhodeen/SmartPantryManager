package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.RecipeDAO;
import com.example.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeDetailName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeMethod;
    private RecipeDAO recipeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Button btnRecipeDetailBack = findViewById(R.id.btnRecipeDetailBack);
        btnRecipeDetailBack.setOnClickListener(v -> finish());

        txtRecipeDetailName = findViewById(R.id.txtRecipeDetailName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeMethod = findViewById(R.id.txtRecipeMethod);
        recipeDAO = new RecipeDAO(this);

        String recipeName = getIntent().getStringExtra("recipe_name");
        String recipeMethod = getIntent().getStringExtra("recipe_method");
        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        txtRecipeDetailName.setText(recipeName == null || recipeName.trim().isEmpty()
                ? "Recipe details" : recipeName);
        txtRecipeMethod.setText(recipeMethod == null ? "Preparation method is not available." : recipeMethod);

        txtRecipeDetailName.setTextColor(0xFF000000);
        txtRecipeIngredients.setTextColor(0xFF000000);
        txtRecipeMethod.setTextColor(0xFF000000);

        ArrayList<RecipeIngredient> ingredients = recipeDAO.getRecipeIngredients(recipeId);
        StringBuilder ingredientText = new StringBuilder();
        int availableCount = 0;

        for (RecipeIngredient ingredient : ingredients) {
            String name = ingredient.getIngredientName();
            String unit = ingredient.getUnit();
            double required = recipeDAO.getRequiredQuantityInBaseUnits(
                    ingredient.getRequiredQuantity(), unit);
            double available = recipeDAO.getAvailableQuantityInBaseUnits(name, unit);
            String displayUnit = recipeDAO.getBaseUnitLabel(unit);

            ingredientText.append("• ").append(name).append(" — need ")
                    .append(formatQuantity(required)).append(" ").append(displayUnit).append("\n");

            if (available + 0.000001 >= required) {
                availableCount++;
                ingredientText.append("   Have enough: ")
                        .append(formatQuantity(available)).append(" ").append(displayUnit).append("\n");
            } else {
                ingredientText.append("   In pantry: ")
                        .append(formatQuantity(available)).append(" ").append(displayUnit)
                        .append(" — add ").append(formatQuantity(required - available))
                        .append(" ").append(displayUnit).append(" more\n");
            }
            ingredientText.append("\n");
        }

        if (ingredients.isEmpty()) {
            ingredientText.append("Ingredient details are not available for this recipe yet.");
        } else if (availableCount == ingredients.size()) {
            ingredientText.insert(0, "You have everything needed for this recipe!\n\n");
        } else {
            ingredientText.insert(0, "You have enough of " + availableCount + " out of " +
                    ingredients.size() + " required ingredients. Add the missing quantities below.\n\n");
        }

        txtRecipeIngredients.setText(ingredientText.toString());
    }

    private String formatQuantity(double quantity) {
        if (Math.abs(quantity - Math.round(quantity)) < 0.000001) {
            return String.valueOf(Math.round(quantity));
        }
        return String.format(Locale.getDefault(), "%.2f", quantity);
    }
}
