package com.example.smartpantrymanager.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.RecipeDetailActivity;
import com.example.smartpantrymanager.database.RecipeDAO;
import com.example.smartpantrymanager.model.Recipe;

import java.util.ArrayList;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    private RecipeDAO recipeDAO;

    public RecipeAdapter(ArrayList<Recipe> recipes, RecipeDAO recipeDAO) {
        this.recipes = recipes;
        this.recipeDAO = recipeDAO;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipes.get(position);
        holder.txtRecipeName.setText(recipe.getName());
        holder.txtRecipeAvailability.setText(
                recipeDAO.getRecipeAvailabilitySummary(recipe.getId()));
        holder.txtRecipeName.setTextColor(0xFF000000);
        holder.txtRecipeAvailability.setTextColor(0xFF000000);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra("recipe_id", recipe.getId());
            intent.putExtra("recipe_name", recipe.getName());
            intent.putExtra("recipe_method", recipe.getMethod());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtRecipeAvailability;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            txtRecipeAvailability = itemView.findViewById(R.id.txtRecipeAvailability);
        }
    }
}
