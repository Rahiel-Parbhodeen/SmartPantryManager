package com.example.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Pantry table
        db.execSQL(
                "CREATE TABLE pantry (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "quantity REAL NOT NULL," +
                        "unit TEXT NOT NULL," +
                        "expiryDate TEXT)"
        );

        // Recipe table
        db.execSQL(
                "CREATE TABLE recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "method TEXT NOT NULL)"
        );

        // Ingredients required by each recipe
        db.execSQL(
                "CREATE TABLE recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "recipe_id INTEGER NOT NULL," +
                        "ingredient_name TEXT NOT NULL," +
                        "required_quantity REAL NOT NULL," +
                        "unit TEXT NOT NULL," +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id))"
        );
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {

        // Add the recipe tables when upgrading
        if (oldVersion < 2) {

            db.execSQL(
                    "CREATE TABLE recipes (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL," +
                            "method TEXT NOT NULL)"
            );

            db.execSQL(
                    "CREATE TABLE recipe_ingredients (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "recipe_id INTEGER NOT NULL," +
                            "ingredient_name TEXT NOT NULL," +
                            "required_quantity REAL NOT NULL," +
                            "unit TEXT NOT NULL," +
                            "FOREIGN KEY(recipe_id) REFERENCES recipes(id))"
            );
        }
    }
}