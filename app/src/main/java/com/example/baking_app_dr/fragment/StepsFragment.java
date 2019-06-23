package com.example.baking_app_dr.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.activity.RecipeInfoActivity;
import com.example.baking_app_dr.activity.RecipeInfoDetailActivity;
import com.example.baking_app_dr.adapter.StepAdapter;
import com.example.baking_app_dr.databinding.FragmentStepsBinding;
import com.example.baking_app_dr.model.Step;
import com.example.baking_app_dr.util.RecipeOnItemClickListener;

import java.util.ArrayList;
import java.util.List;


// Created by Deepak Rattan on 15-Jun-19.
public class StepsFragment extends Fragment {
    List<Step> stepList;
    StepAdapter stepAdapter;
    FragmentStepsBinding binding;
    Step step;
    public static final String TAG = "test";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);
        View view = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvSteps.setLayoutManager(layoutManager);
        binding.rvSteps.setHasFixedSize(true);
        initView();
        return view;
    }

    private void initView() {
        stepList = ((RecipeInfoActivity) getActivity()).getStepList();
        for (int i = 0; i < stepList.size(); i++) {
            step = stepList.get(i);
            Log.d(TAG, "At position " + i + " description is " + step.getShortDescription());

        }
        stepAdapter = new StepAdapter(getActivity(), stepList, new RecipeOnItemClickListener() {
            @Override
            public void onRecipeItemClick(int pos) {
                // step = stepList.get(pos);
                Intent intent = new Intent(getActivity(), RecipeInfoDetailActivity.class);
                intent.putExtra("step_position", pos);
                intent.putParcelableArrayListExtra("step_list", (ArrayList<? extends Parcelable>) stepList);
                //Toast.makeText(getActivity(), "Description is " + step.getShortDescription() + "Video URL " + step.getVideoURL(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        binding.rvSteps.setAdapter(stepAdapter);

    }


}