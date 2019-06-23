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
import com.example.baking_app_dr.databinding.StepRowBinding;
import com.example.baking_app_dr.model.Ingredient;
import com.example.baking_app_dr.model.Step;
import com.example.baking_app_dr.util.RecipeOnItemClickListener;

import java.util.List;

// Created by Deepak Rattan on 15-Jun-19.
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private Context context;
    private List<Step> stepList;
    int count = 0;
    RecipeOnItemClickListener recipeOnItemClickListener;

    public StepAdapter(Context context, List<Step> stepList, RecipeOnItemClickListener recipeOnItemClickListener) {
        this.context = context;
        this.stepList = stepList;
        this.recipeOnItemClickListener = recipeOnItemClickListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        StepRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_row, viewGroup, false);
        return new StepAdapter.StepViewHolder(binding.getRoot(), recipeOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int pos) {

        Step step = stepList.get(pos);
        if (pos == 0)
            stepViewHolder.setDescription(step.getShortDescription());
        else {
            count++;
            stepViewHolder.setDescription(count, step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        StepRowBinding binding;
        RecipeOnItemClickListener recipeOnItemClickListener;

        public StepViewHolder(@NonNull View itemView, RecipeOnItemClickListener recipeOnItemClickListener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            this.recipeOnItemClickListener = recipeOnItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void setDescription(String description) {
            binding.txtStepName.setText(description);
        }


        public void setDescription(int count, String shortDescription) {
            binding.txtStepName.setText(count + ". " + shortDescription);
        }

        @Override
        public void onClick(View view) {
            recipeOnItemClickListener.onRecipeItemClick(getAdapterPosition());
        }
    }
}