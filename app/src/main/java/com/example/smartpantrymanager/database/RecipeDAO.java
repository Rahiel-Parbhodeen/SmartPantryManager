package com.example.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.smartpantrymanager.model.RecipeIngredient;

import com.example.smartpantrymanager.model.Recipe;

import java.util.ArrayList;

public class RecipeDAO {

    private DatabaseHelper databaseHelper;

    public RecipeDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    //this will add a recipe
    public long addRecipe(Recipe recipe) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", recipe.getName());
        values.put("method", recipe.getMethod());

        long id = db.insert("recipes", null, values);

        db.close();

        return id;
    }

    //this will get all recipes
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipes",
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                String method = cursor.getString(
                        cursor.getColumnIndexOrThrow("method")
                );

                Recipe recipe = new Recipe(
                        id,
                        name,
                        method
                );

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipes;
    }

    //add an ingredient required by a recipe
    public long addRecipeIngredient(int recipeId, String ingredientName, double requiredQuantity, String unit) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("recipe_id", recipeId);
        values.put("ingredient_name", ingredientName);
        values.put("required_quantity", requiredQuantity);
        values.put("unit", unit);

        long id = db.insert(
                "recipe_ingredients",
                null,
                values
        );

        db.close();

        return id;
    }

    //this will add a sample recipe for testing
    public void seedRecipes() {

        // Add or repair the built-in recipes even if an older database already
        // contains only some of them. This prevents a partial first seed from
        // leaving the app with only Tomato Pasta.
            addSeedRecipe(
                    "Tomato Pasta",
                    "1. Boil the pasta.\n" +
                            "2. Cook the tomatoes in a pan.\n" +
                            "3. Add the pasta to the pan.\n" +
                            "4. Add the cheese and mix well.",
                    new String[]{"pasta", "tomato", "cheese"},
                    new double[]{200, 2, 50},
                    new String[]{"g", "pieces", "g"}
            );

            addSeedRecipe(
                    "Cheese Omelette",
                    "1. Crack the eggs into a bowl.\n" +
                            "2. Beat the eggs.\n" +
                            "3. Cook the eggs in a pan.\n" +
                            "4. Add the cheese and fold the omelette.",
                    new String[]{"egg", "cheese"},
                    new double[]{2, 30},
                    new String[]{"pieces", "g"}
            );

            addSeedRecipe(
                    "Chicken Pasta",
                    "1. Boil the pasta.\n" +
                            "2. Cook the chicken.\n" +
                            "3. Add the pasta.\n" +
                            "4. Mix well and serve.",
                    new String[]{"pasta", "chicken"},
                    new double[]{200, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Chicken Rice",
                    "1. Cook the rice.\n" +
                            "2. Cook the chicken.\n" +
                            "3. Mix the chicken and rice together.",
                    new String[]{"rice", "chicken"},
                    new double[]{200, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Tomato Soup",
                    "1. Cook the tomatoes.\n" +
                            "2. Add water and simmer.\n" +
                            "3. Blend until smooth.",
                    new String[]{"tomato", "water"},
                    new double[]{4, 500},
                    new String[]{"pieces", "ml"}
            );

            addSeedRecipe(
                    "Grilled Cheese Sandwich",
                    "1. Place cheese between two slices of bread.\n" +
                            "2. Toast the sandwich in a pan.\n" +
                            "3. Cook until golden brown.",
                    new String[]{"bread", "cheese"},
                    new double[]{2, 50},
                    new String[]{"pieces", "g"}
            );

            addSeedRecipe(
                    "Chicken Sandwich",
                    "1. Cook the chicken.\n" +
                            "2. Place chicken between slices of bread.\n" +
                            "3. Add tomato and serve.",
                    new String[]{"bread", "chicken", "tomato"},
                    new double[]{2, 150, 1},
                    new String[]{"pieces", "g", "pieces"}
            );

            addSeedRecipe(
                    "Egg Sandwich",
                    "1. Cook the eggs.\n" +
                            "2. Place the eggs between slices of bread.\n" +
                            "3. Serve warm.",
                    new String[]{"bread", "egg"},
                    new double[]{2, 2},
                    new String[]{"pieces", "pieces"}
            );

            addSeedRecipe(
                    "Rice and Vegetables",
                    "1. Cook the rice.\n" +
                            "2. Cook the vegetables.\n" +
                            "3. Mix together and serve.",
                    new String[]{"rice", "vegetable"},
                    new double[]{200, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Chicken and Vegetables",
                    "1. Cook the chicken.\n" +
                            "2. Cook the vegetables.\n" +
                            "3. Mix together and serve.",
                    new String[]{"chicken", "vegetable"},
                    new double[]{200, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Cheesy Rice",
                    "1. Cook the rice.\n" +
                            "2. Add cheese.\n" +
                            "3. Mix until the cheese melts.",
                    new String[]{"rice", "cheese"},
                    new double[]{200, 50},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Tomato Rice",
                    "1. Cook the rice.\n" +
                            "2. Cook the tomatoes.\n" +
                            "3. Mix the tomatoes with the rice.",
                    new String[]{"rice", "tomato"},
                    new double[]{200, 2},
                    new String[]{"g", "pieces"}
            );

            addSeedRecipe(
                    "Chicken and Cheese",
                    "1. Cook the chicken.\n" +
                            "2. Add cheese.\n" +
                            "3. Cook until the cheese melts.",
                    new String[]{"chicken", "cheese"},
                    new double[]{200, 50},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Pasta with Chicken",
                    "1. Boil the pasta.\n" +
                            "2. Cook the chicken.\n" +
                            "3. Mix the chicken with the pasta.",
                    new String[]{"pasta", "chicken"},
                    new double[]{250, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Pasta with Vegetables",
                    "1. Boil the pasta.\n" +
                            "2. Cook the vegetables.\n" +
                            "3. Mix the vegetables with the pasta.",
                    new String[]{"pasta", "vegetable"},
                    new double[]{200, 200},
                    new String[]{"g", "g"}
            );

            addSeedRecipe(
                    "Egg Fried Rice",
                    "1. Cook the rice.\n" +
                            "2. Cook the eggs.\n" +
                            "3. Mix the eggs and rice together.",
                    new String[]{"rice", "egg"},
                    new double[]{200, 2},
                    new String[]{"g", "pieces"}
            );

            addSeedRecipe(
                    "Chicken Tomato Pasta",
                    "1. Boil the pasta.\n" +
                            "2. Cook the chicken and tomatoes.\n" +
                            "3. Add the pasta and mix.",
                    new String[]{"pasta", "chicken", "tomato"},
                    new double[]{200, 150, 2},
                    new String[]{"g", "g", "pieces"}
            );

            addSeedRecipe(
                    "Cheese and Tomato Toast",
                    "1. Place cheese and tomato on bread.\n" +
                            "2. Toast until the cheese melts.\n" +
                            "3. Serve warm.",
                    new String[]{"bread", "cheese", "tomato"},
                    new double[]{2, 40, 1},
                    new String[]{"pieces", "g", "pieces"}
            );

            addSeedRecipe(
                    "Chicken Cheese Rice",
                    "1. Cook the rice.\n" +
                            "2. Cook the chicken.\n" +
                            "3. Add cheese and mix together.",
                    new String[]{"rice", "chicken", "cheese"},
                    new double[]{200, 150, 40},
                    new String[]{"g", "g", "g"}
            );

            addSeedRecipe(
                    "Vegetable Omelette",
                    "1. Beat the eggs.\n" +
                            "2. Add vegetables.\n" +
                            "3. Cook in a pan until done.",
                    new String[]{"egg", "vegetable"},
                    new double[]{2, 100},
                    new String[]{"pieces", "g"}
            );
    }

    private void addSeedRecipe(
            String name,
            String method,
            String[] ingredientNames,
            double[] quantities,
            String[] units
    ) {
        // Reuse an existing recipe ID if this recipe was partly saved before.
        int recipeId = -1;
        SQLiteDatabase readDb = databaseHelper.getReadableDatabase();
        Cursor existing = readDb.rawQuery(
                "SELECT id FROM recipes WHERE LOWER(name) = LOWER(?) LIMIT 1",
                new String[]{name}
        );
        if (existing.moveToFirst()) {
            recipeId = existing.getInt(existing.getColumnIndexOrThrow("id"));
        }
        existing.close();
        readDb.close();

        if (recipeId == -1) {
            long insertedId = addRecipe(new Recipe(0, name, method));
            if (insertedId == -1) {
                return;
            }
            recipeId = (int) insertedId;
        } else {
            SQLiteDatabase updateDb = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("method", method);
            updateDb.update("recipes", values, "id = ?",
                    new String[]{String.valueOf(recipeId)});
            // Rebuild only this recipe's ingredient rows so incomplete or old
            // ingredient data cannot make the app report false missing items.
            updateDb.delete("recipe_ingredients", "recipe_id = ?",
                    new String[]{String.valueOf(recipeId)});
            updateDb.close();
        }

        for (int i = 0; i < ingredientNames.length; i++) {
            addRecipeIngredient(recipeId, ingredientNames[i], quantities[i], units[i]);
        }
    }

    // Return every recipe that has ingredient details, even if some ingredients are missing.
    public ArrayList<Recipe> getRecipesForSuggestions() {

        ArrayList<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT r.id, r.name, r.method FROM recipes r " +
                        "WHERE EXISTS (SELECT 1 FROM recipe_ingredients ri " +
                        "WHERE ri.recipe_id = r.id) ORDER BY r.name COLLATE NOCASE",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                recipes.add(new Recipe(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("method"))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recipes;
    }

    // Returns the usable pantry quantity in base units (g, ml, pieces, or the same custom unit).
    public double getAvailableQuantityInBaseUnits(String ingredientName, String requiredUnit) {

        double availableQuantity = 0;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT name, quantity, unit, expiryDate FROM pantry",
                null
        );

        String requiredDimension = getUnitDimension(requiredUnit);

        if (cursor.moveToFirst()) {
            do {
                String pantryName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String pantryUnit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));

                if (!normaliseIngredientName(ingredientName).equals(normaliseIngredientName(pantryName))) {
                    continue;
                }
                if (isExpired(expiryDate)) {
                    continue;
                }

                if (requiredDimension.equals("unknown")) {
                    if (normaliseUnit(pantryUnit).equals(normaliseUnit(requiredUnit))) {
                        availableQuantity += cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                    }
                } else if (getUnitDimension(pantryUnit).equals(requiredDimension)) {
                    availableQuantity += convertToBaseQuantity(
                            cursor.getDouble(cursor.getColumnIndexOrThrow("quantity")), pantryUnit);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return availableQuantity;
    }

    // Gives the user a clear status and names the ingredients or quantities still needed.
    public String getRecipeAvailabilitySummary(int recipeId) {

        ArrayList<RecipeIngredient> ingredients = getRecipeIngredients(recipeId);
        int availableCount = 0;
        ArrayList<String> missingIngredients = new ArrayList<>();

        for (RecipeIngredient ingredient : ingredients) {
            double available = getAvailableQuantityInBaseUnits(
                    ingredient.getIngredientName(), ingredient.getUnit());
            double required = convertToBaseQuantity(
                    ingredient.getRequiredQuantity(), ingredient.getUnit());

            if (available + 0.000001 >= required) {
                availableCount++;
            } else {
                double shortage = required - available;
                missingIngredients.add(ingredient.getIngredientName() + " (need " +
                        formatQuantity(shortage) + " " + getBaseUnitLabel(ingredient.getUnit()) + ")");
            }
        }

        if (ingredients.isEmpty()) {
            return "Ingredient details are not available yet.";
        }
        if (missingIngredients.isEmpty()) {
            return "Ready to cook! You have all " + ingredients.size() + " ingredients.";
        }

        StringBuilder summary = new StringBuilder();
        summary.append("You have enough of ").append(availableCount).append(" of ").append(ingredients.size()).append(" ingredients.\nMissing: ");
        for (int i = 0; i < missingIngredients.size(); i++) {
            if (i > 0) summary.append(", ");
            summary.append(missingIngredients.get(i));
        }
        return summary.toString();
    }

    // Converts unit names into the label used when showing quantities to the user.
    public String getBaseUnitLabel(String unit) {
        String value = normaliseUnit(unit);
        if (value.equals("kg") || value.equals("g") || value.equals("mg")) return "g";
        if (value.equals("l") || value.equals("ml")) return "ml";
        if (value.equals("piece")) return "pieces";
        return value;
    }

    public double getRequiredQuantityInBaseUnits(double quantity, String unit) {
        return convertToBaseQuantity(quantity, unit);
    }

    private String formatQuantity(double quantity) {
        if (Math.abs(quantity - Math.round(quantity)) < 0.000001) {
            return String.valueOf(Math.round(quantity));
        }
        return String.format(java.util.Locale.getDefault(), "%.2f", quantity);
    }

    // Only return recipes when every required ingredient and quantity is available.
    public ArrayList<Recipe> getMatchingRecipes() {

        ArrayList<Recipe> matchingRecipes = new ArrayList<>();
        ArrayList<Recipe> allRecipes = getAllRecipes();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        for (Recipe recipe : allRecipes) {
            boolean recipeMatches = true;
            boolean hasRequiredIngredients = false;

            Cursor requiredCursor = db.rawQuery(
                    "SELECT ingredient_name, required_quantity, unit " +
                            "FROM recipe_ingredients WHERE recipe_id = ?",
                    new String[]{String.valueOf(recipe.getId())}
            );

            if (requiredCursor.moveToFirst()) {
                hasRequiredIngredients = true;
                do {
                    String requiredName = requiredCursor.getString(
                            requiredCursor.getColumnIndexOrThrow("ingredient_name"));
                    double requiredQuantity = requiredCursor.getDouble(
                            requiredCursor.getColumnIndexOrThrow("required_quantity"));
                    String requiredUnit = requiredCursor.getString(
                            requiredCursor.getColumnIndexOrThrow("unit"));

                    double availableQuantity = 0;
                    Cursor pantryCursor = db.rawQuery(
                            "SELECT name, quantity, unit, expiryDate FROM pantry",
                            null
                    );

                    if (pantryCursor.moveToFirst()) {
                        do {
                            String pantryName = pantryCursor.getString(pantryCursor.getColumnIndexOrThrow("name"));
                            String pantryUnit = pantryCursor.getString(pantryCursor.getColumnIndexOrThrow("unit"));
                            double pantryQuantity = pantryCursor.getDouble(pantryCursor.getColumnIndexOrThrow("quantity"));
                            String expiryDate = pantryCursor.getString(pantryCursor.getColumnIndexOrThrow("expiryDate"));

                            if (!normaliseIngredientName(requiredName).equals(normaliseIngredientName(pantryName))) {
                                continue;
                            }

                            //if the ingredients are expired they are not treated as usable pantry stock.
                            if (isExpired(expiryDate)) {
                                continue;
                            }

                            double convertedQuantity = convertToBaseQuantity(pantryQuantity, pantryUnit);
                            String pantryDimension = getUnitDimension(pantryUnit);
                            String requiredDimension = getUnitDimension(requiredUnit);

                            if (pantryDimension.equals(requiredDimension)) {
                                availableQuantity += convertedQuantity;
                            }
                        } while (pantryCursor.moveToNext());
                    }
                    pantryCursor.close();

                    double convertedRequired = convertToBaseQuantity(requiredQuantity, requiredUnit);
                    String requiredDimension = getUnitDimension(requiredUnit);
                    if (requiredDimension.equals("unknown")) {
                        // For custom units, only identical normalised units are considered compatible.
                        availableQuantity = getAvailableCustomUnitQuantity(db, requiredName, requiredUnit);
                        convertedRequired = requiredQuantity;
                    }

                    // If any required ingredient or quantity is missing, reject the entire recipe.
                    if (availableQuantity + 0.000001 < convertedRequired) {
                        recipeMatches = false;
                        break;
                    }
                } while (requiredCursor.moveToNext());
            }
            requiredCursor.close();

            // A recipe with no ingredient rows is incomplete and must never be suggested.
            if (recipeMatches && hasRequiredIngredients) {
                matchingRecipes.add(recipe);
            }
        }
        db.close();
        return matchingRecipes;
    }

    private double getAvailableCustomUnitQuantity(SQLiteDatabase db, String requiredName, String requiredUnit) {
        double total = 0;
        Cursor cursor = db.rawQuery(
                "SELECT name, quantity, unit, expiryDate FROM pantry",
                null
        );
        if (cursor.moveToFirst()) {
            do {
                String pantryName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String pantryUnit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiryDate"));
                if (normaliseIngredientName(requiredName).equals(normaliseIngredientName(pantryName))
                        && normaliseUnit(pantryUnit).equals(normaliseUnit(requiredUnit))
                        && !isExpired(expiryDate)) {
                    total += cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return total;
    }

    private String normaliseIngredientName(String name) {
        if (name == null) return "";
        String result = name.toLowerCase().trim().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ");
        // Accept a common spelling variant users may enter for tomato.
        result = result.replaceAll("\\btomatoe\\b", "tomato");
        if (result.endsWith("ies") && result.length() > 3) {
            result = result.substring(0, result.length() - 3) + "y";
        } else if (result.endsWith("oes") && result.length() > 4) {
            result = result.substring(0, result.length() - 2);
        } else if (result.endsWith("s") && !result.endsWith("ss") && result.length() > 3) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private String normaliseUnit(String unit) {
        if (unit == null) return "";
        String value = unit.toLowerCase().trim().replace(".", "");
        if (value.equals("gram") || value.equals("grams") || value.equals("gm") || value.equals("gms")) return "g";
        if (value.equals("kilogram") || value.equals("kilograms") || value.equals("kgs") || value.equals("kg")) return "kg";
        if (value.equals("milligram") || value.equals("milligrams") || value.equals("mgs") || value.equals("mg")) return "mg";
        if (value.equals("millilitre") || value.equals("millilitres") || value.equals("milliliter") || value.equals("milliliters") || value.equals("mls")) return "ml";
        if (value.equals("litre") || value.equals("litres") || value.equals("liter") || value.equals("liters")) return "l";
        if (value.equals("piece") || value.equals("pieces") || value.equals("pc") || value.equals("pcs") || value.equals("each") || value.equals("count")) return "piece";
        return value;
    }

    private String getUnitDimension(String unit) {
        String value = normaliseUnit(unit);
        if (value.equals("g") || value.equals("kg") || value.equals("mg")) return "mass";
        if (value.equals("ml") || value.equals("l")) return "volume";
        if (value.equals("piece")) return "count";
        return "unknown";
    }

    // Converts compatible units to a common base (grams, millilitres or pieces).
    private double convertToBaseQuantity(double quantity, String unit) {
        String value = normaliseUnit(unit);
        if (value.equals("kg")) return quantity * 1000.0;
        if (value.equals("mg")) return quantity / 1000.0;
        if (value.equals("l")) return quantity * 1000.0;
        return quantity;
    }

    private boolean isExpired(String expiryDate) {
        if (expiryDate == null || expiryDate.trim().isEmpty()) return false;
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(expiryDate.trim());
            return date.isBefore(java.time.LocalDate.now());
        } catch (java.time.format.DateTimeParseException e) {
            // An invalid date should not crash recipe matching; form validation prevents new invalid dates.
            return false;
        }
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients(int recipeId) {

        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM recipe_ingredients WHERE recipe_id = ?",
                new String[]{String.valueOf(recipeId)}
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                int recipeID = cursor.getInt(
                        cursor.getColumnIndexOrThrow("recipe_id")
                );

                String ingredientName = cursor.getString(
                        cursor.getColumnIndexOrThrow("ingredient_name")
                );

                double requiredQuantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("required_quantity")
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit")
                );

                RecipeIngredient ingredient =
                        new RecipeIngredient(
                                id,
                                recipeID,
                                ingredientName,
                                requiredQuantity,
                                unit
                        );

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredients;
    }
}