package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapter.RecipeAdapter;
import com.example.smartpantrymanager.database.RecipeDAO;
import com.example.smartpantrymanager.model.Recipe;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView txtNoRecipes;

    private RecipeDAO recipeDAO;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggested_recipes);

        Button btnRecipesHome = findViewById(R.id.btnRecipesHome);
        btnRecipesHome.setOnClickListener(v -> finish());

        recyclerRecipes = findViewById(R.id.recyclerRecipes);

        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        recipeDAO = new RecipeDAO(this);
        recipeDAO.seedRecipes();

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recipeDAO != null && recyclerRecipes != null) {
            loadRecipes();
        }
    }

    private void loadRecipes() {

        ArrayList<Recipe> recipes = recipeDAO.getRecipesForSuggestions();

        if (recipes.isEmpty()) {
            txtNoRecipes.setVisibility(TextView.VISIBLE);
            recyclerRecipes.setVisibility(RecyclerView.GONE);
        } else {
            txtNoRecipes.setVisibility(TextView.GONE);
            recyclerRecipes.setVisibility(RecyclerView.VISIBLE);

            recipeAdapter = new RecipeAdapter(recipes, recipeDAO);
            recyclerRecipes.setAdapter(recipeAdapter);
        }
    }
}