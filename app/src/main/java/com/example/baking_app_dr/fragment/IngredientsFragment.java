package com.example.baking_app_dr.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.activity.RecipeInfoActivity;
import com.example.baking_app_dr.adapter.IngredientsAdapter;
import com.example.baking_app_dr.databinding.FragmentIngredientsBinding;
import com.example.baking_app_dr.model.BakingResponse;
import com.example.baking_app_dr.model.Ingredient;

import java.util.List;

// Created by Deepak Rattan on 15-Jun-19.
public class IngredientsFragment extends Fragment {
    FragmentIngredientsBinding binding;
    IngredientsAdapter adapter;
    List<Ingredient> ingredientList;
    BakingResponse response;
    public static final String TAG = "test";
    String recipe_name;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);
        View view = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvIngredients.setLayoutManager(layoutManager);
        binding.rvIngredients.setHasFixedSize(true);
        initViews();
        return view;
    }


    private void initViews() {
        ingredientList = ((RecipeInfoActivity) getActivity()).getIngredientList();
        adapter = new IngredientsAdapter(getActivity(), ingredientList);
        binding.rvIngredients.setAdapter(adapter);

    }


}


