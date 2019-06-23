package com.example.baking_app_dr.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.databinding.IngredientRowBinding;
import com.example.baking_app_dr.model.Ingredient;

import java.util.List;

// Created by Deepak Rattan on 15-Jun-19.
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {
    private Context context;
    private List<Ingredient> ingredientList;

    public IngredientsAdapter(Context context, List<Ingredient> ingredientList) {
        this.context = context;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        IngredientRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.ingredient_row, viewGroup, false);
        return new IngredientsAdapter.IngredientViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int pos) {

        Ingredient ingredient = ingredientList.get(pos);
        ingredientViewHolder.setTitle(ingredient.getIngredient());
        ingredientViewHolder.setDose(ingredient.getQuantity() + " " + ingredient.getMeasure());

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder {
        IngredientRowBinding binding;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void setTitle(String title) {
            binding.txtIngredientName.setText(title);
        }

        void setDose(String dose) {
            binding.txtIngredientDose.setText(dose);
        }
    }
}