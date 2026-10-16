package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.time.format.DateTimeParseException;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.PantryDAO;
import com.example.smartpantrymanager.model.PantryItem;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtQuantity;
    private EditText edtUnit;
    private EditText edtExpiryDate;

    private Button btnSave;
    private Button btnDelete;

    private PantryDAO pantryDAO;

    //This stores the ID of the item being edited
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_edit_ingredient);

        edtName = findViewById(R.id.edtName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtExpiryDate = findViewById(R.id.edtExpiryDate);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        pantryDAO = new PantryDAO(this);

        //this will get the ID sent from PantryAdapter
        itemId = getIntent().getIntExtra("item_id", -1);

        //if we get an item ID, we are editing
        if (itemId != -1) {

            edtName.setText(
                    getIntent().getStringExtra("item_name")
            );

            edtQuantity.setText(
                    String.valueOf(
                            getIntent().getDoubleExtra(
                                    "item_quantity",
                                    0
                            )
                    )
            );

            edtUnit.setText(
                    getIntent().getStringExtra("item_unit")
            );

            edtExpiryDate.setText(
                    getIntent().getStringExtra("item_expiry")
            );

            TextView title = findViewById(R.id.txtTitle);
            title.setText("Edit Ingredient");

            btnSave.setText("Update Ingredient");

        } else {

            //delete is only needed when editing
            btnDelete.setVisibility(View.GONE);
        }

        //let the user choose an expiry date with the date picker.
        edtExpiryDate.setFocusable(false);
        edtExpiryDate.setClickable(true);
        edtExpiryDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveIngredient());

        btnDelete.setOnClickListener(v -> deleteIngredient());
    }

    //this will open a calendar so the user does not have to type the date manually.
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        String currentDate = edtExpiryDate.getText().toString().trim();
        if (!currentDate.isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(currentDate);
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
            } catch (DateTimeParseException ignored) {
                //Use today's date if the saved date is not in yyyy-MM-dd format.
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year, month + 1, dayOfMonth
                    );
                    edtExpiryDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void saveIngredient() {

        String name = edtName.getText().toString().trim();
        String quantityText = edtQuantity.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String expiryDate = edtExpiryDate.getText().toString().trim();

        //this is for validation so that users are not allowed to add dates that already passed
        if (!expiryDate.isEmpty()) {
            try {
                LocalDate enteredDate = LocalDate.parse(expiryDate);
                LocalDate today = LocalDate.now();

                if (enteredDate.isBefore(today)) {
                    edtExpiryDate.setError(
                            "Expiry date cannot be in the past"
                    );

                    edtExpiryDate.requestFocus();
                    return;
                }

            } catch (DateTimeParseException e) {
                edtExpiryDate.setError(
                        "Use the format YYYY-MM-DD"
                );

                edtExpiryDate.requestFocus();
                return;
            }
        }

        if (name.isEmpty()) {
            edtName.setError("Please enter an ingredient name");
            edtName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            edtQuantity.setError("Please enter a quantity");
            edtQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            edtUnit.setError("Please enter a unit");
            edtUnit.requestFocus();
            return;
        }

        double quantity;

        try {

            quantity = Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            edtQuantity.setError("Please enter a valid number");
            edtQuantity.requestFocus();
            return;
        }

        if (!Double.isFinite(quantity) || quantity <= 0) {

            edtQuantity.setError(
                    "Quantity must be greater than 0"
            );

            edtQuantity.requestFocus();
            return;
        }

        PantryItem item = new PantryItem(
                itemId,
                name,
                quantity,
                unit,
                expiryDate
        );

        long result;

        // If itemId is -1, create a new ingredient
        if (itemId == -1) {

            result = pantryDAO.addItem(item);

        } else {

            // Otherwise update the existing ingredient
            result = pantryDAO.updateItem(item);
        }

        boolean saveSucceeded = itemId == -1 ? result != -1 : result > 0;
        if (saveSucceeded) {

            Toast.makeText(
                    this,
                    itemId == -1
                            ? "Ingredient saved"
                            : "Ingredient updated",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to save ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void deleteIngredient() {

        if (itemId == -1) {
            return;
        }

        // Ask before deleting so an ingredient is not removed accidentally.
        new AlertDialog.Builder(this)
                .setTitle("Delete ingredient?")
                .setMessage("Are you sure you want to remove this ingredient from your pantry?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Delete", (dialog, which) -> {
                    int result = pantryDAO.deleteItem(itemId);
                    if (result > 0) {
                        Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Could not delete ingredient", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
