package com.example.baking_app_dr.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.databinding.ActivityRecipeInfoDetailsBinding;
import com.example.baking_app_dr.fragment.RecipeInfoDetailFragment;
import com.example.baking_app_dr.model.Step;

import java.util.List;

// Created by Deepak Rattan on 23-Jun-19.
public class RecipeInfoDetailActivity extends AppCompatActivity {
    ActivityRecipeInfoDetailsBinding binding;
    List<Step> stepList;
    int step_position;
    Step step;
    public static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_info_details);

        stepList = getIntent().getParcelableArrayListExtra("step_list");
        step_position = getIntent().getExtras().getInt("step_position");

        //Log.d(TAG, "position is  " + step_position);
        //Log.d(TAG, "step_description at position " + step_position + " is " + stepList.get(step_position).getShortDescription());

        step = stepList.get(step_position);

        RecipeInfoDetailFragment recipeInfoDetailFragment = new RecipeInfoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step_desc_key", step);
        recipeInfoDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container_recipe_info_detail, recipeInfoDetailFragment).commit();


    }

    public Step getStepDetailAtClickedIndex() {
        return stepList.get(step_position);
    }

}
