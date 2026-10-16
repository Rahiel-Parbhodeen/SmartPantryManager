package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartpantrymanager.adapter.PantryAdapter;
import com.example.smartpantrymanager.database.PantryDAO;
import com.example.smartpantrymanager.model.PantryItem;
import com.example.smartpantrymanager.database.RecipeDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private PantryAdapter pantryAdapter;
    private PantryDAO pantryDAO;
    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnBackHome = findViewById(R.id.btnBackHome);
        btnBackHome.setOnClickListener(v -> finish());

        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );
            startActivity(intent);
        });

        recyclerPantry = findViewById(R.id.recyclerPantry);

        pantryDAO = new PantryDAO(this);
        RecipeDAO recipeDAO = new RecipeDAO(this);
        recipeDAO.seedRecipes();

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );
        loadPantryItems();
    }

    private void loadPantryItems() {

        ArrayList<PantryItem> pantryItems = pantryDAO.getAllItems();
        TextView txtPantryEmpty = findViewById(R.id.txtPantryEmpty);
        if (pantryItems.isEmpty()) {
            txtPantryEmpty.setVisibility(TextView.VISIBLE);
            recyclerPantry.setVisibility(RecyclerView.GONE);
        } else {
            txtPantryEmpty.setVisibility(TextView.GONE);
            recyclerPantry.setVisibility(RecyclerView.VISIBLE);
        }
        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantry.setAdapter(pantryAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }
}