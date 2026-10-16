# Smart Pantry Manager

Smart Pantry Manager is a Java Android application that helps users track ingredients in their pantry and find recipes they can make with ingredients they already have. Suggested Recipes shows every seeded recipe with ingredient details, clearly marks whether it is ready to cook, and lists the missing ingredients and extra quantities needed. Expired pantry stock is not counted as usable.

## Features

- Add, view, edit and delete pantry ingredients.
- Save pantry items locally with SQLite so they remain after the app closes.
- Validate ingredient names, positive quantities, units and expiry dates.
- Show expired and soon-to-expire pantry items when expiry reminders are enabled.
- Browse a seeded collection of 20 recipes, each with ingredient quantities, units and preparation steps.
- Show ingredient availability for every recipe, including missing ingredients and quantity shortages.
- Compare common unit variations (g/kg/mg, ml/l and piece synonyms), and simple singular/plural ingredient names.
- Do not suggest recipes that rely on expired ingredients.
- Open a recipe detail screen to compare required quantities with usable pantry stock and view preparation steps.
- Use a consistent green-and-cream theme and simple navigation between screens.

## Database

The app uses SQLite through `SQLiteOpenHelper` in `DatabaseHelper.java`. SQLite is appropriate for this assignment because pantry data is stored locally on the device and does not require a server or network connection.

The database contains:

- `pantry`: ingredient name, quantity, unit and optional expiry date.
- `recipes`: recipe name and preparation method.
- `recipe_ingredients`: each recipe's required ingredient name, quantity and unit.

The initial recipe collection is inserted when the app opens if the recipes table is empty.

## Open and run

1. Install Android Studio with a compatible Android SDK.
2. Extract the project ZIP.
3. In Android Studio, choose **Open** and select the `SmartPantryManager` project folder (the folder containing `settings.gradle.kts`).
4. Allow Gradle sync to complete. Internet may be required the first time to download the Gradle distribution and dependencies.
5. Select an emulator or connect an Android device, then press **Run**.

## How to test key requirements

1. Add pantry items and confirm they appear in **My Pantry**.
2. Tap an item to edit it; test saving changes and deleting after confirming the prompt.
3. Try a blank name, zero/negative quantity, and an expiry date in the past; the form should reject invalid values.
4. Open **Suggested Recipes** with an empty or partial pantry. All seeded recipes with ingredient details should still appear.
5. Check that each recipe lists missing ingredients and the extra quantity needed. Add the missing ingredients and confirm the recipe changes to **Ready to cook** when enough usable stock is available.
6. Test plural ingredient names and compatible unit conversions such as 1 kg versus 1000 g.
7. Add an expiry date within three days and check the expiry warning. Toggle expiry reminders in **Settings** and verify the warnings summary changes.
8. Close and reopen the app and confirm pantry data remains saved.

## Important files

- `HomeActivity.java`: home dashboard and expiry summary.
- `MainActivity.java`: pantry list screen.
- `AddEditIngredientActivity.java`: pantry form, validation and CRUD actions.
- `SuggestedRecipesActivity.java`: recipe suggestions and empty-state message.
- `adapter/RecipeAdapter.java`: recipe cards with ingredient availability summaries.
- `RecipeDetailActivity.java`: recipe ingredients and method.
- `SettingsActivity.java`: saved expiry-reminder preference.
- `database/DatabaseHelper.java`: SQLite schema and database versioning.
- `database/PantryDAO.java`: pantry database operations.
- `database/RecipeDAO.java`: recipe seeding, ingredient retrieval, unit conversion and availability summaries.

