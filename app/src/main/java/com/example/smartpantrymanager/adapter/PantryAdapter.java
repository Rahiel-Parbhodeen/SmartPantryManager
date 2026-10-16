package com.example.smartpantrymanager.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.AddEditIngredientActivity;
import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import android.graphics.Color;
import android.content.SharedPreferences;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryItems;

    public PantryAdapter(ArrayList<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        holder.txtName.setText(item.getName());

        holder.txtQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit()
        );

        String expiryDate = item.getExpiryDate();
        SharedPreferences preferences = holder.itemView.getContext().getSharedPreferences("smart_pantry_settings", android.content.Context.MODE_PRIVATE);
        boolean remindersEnabled = preferences.getBoolean("expiry_reminders_enabled", true);
        holder.txtExpiry.setTextColor(Color.rgb(94, 111, 97));
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            holder.txtExpiry.setText("Expiry date: Not set");
        } else {
            try {
                LocalDate date = LocalDate.parse(expiryDate.trim());
                LocalDate today = LocalDate.now();
                if (!remindersEnabled) {
                    holder.txtExpiry.setText("Expiry: " + expiryDate);
                } else if (date.isBefore(today)) {
                    holder.txtExpiry.setText("EXPIRED: " + expiryDate);
                    holder.txtExpiry.setTextColor(Color.rgb(179, 38, 30));
                } else if (!date.isAfter(today.plusDays(3))) {
                    holder.txtExpiry.setText("EXPIRING SOON: " + expiryDate);
                    holder.txtExpiry.setTextColor(Color.rgb(164, 90, 0));
                } else {
                    holder.txtExpiry.setText("Expiry: " + expiryDate);
                }
            } catch (DateTimeParseException e) {
                holder.txtExpiry.setText("Expiry: " + expiryDate + " (check date format)");
            }
        }

        // When the user taps an ingredient
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    AddEditIngredientActivity.class
            );

            intent.putExtra("item_id", item.getId());
            intent.putExtra("item_name", item.getName());
            intent.putExtra("item_quantity", item.getQuantity());
            intent.putExtra("item_unit", item.getUnit());
            intent.putExtra("item_expiry", item.getExpiryDate());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtQuantity;
        TextView txtExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtExpiry = itemView.findViewById(R.id.txtExpiry);
        }
    }
}