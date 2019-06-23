package com.example.baking_app_dr.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.baking_app_dr.fragment.IngredientsFragment;
import com.example.baking_app_dr.fragment.StepsFragment;

// Created by Deepak Rattan on 15-Jun-19.
public class MyPagerAdaper extends FragmentStatePagerAdapter {
    private int tabCount;

    public MyPagerAdaper(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                return ingredientsFragment;
            case 1:
                StepsFragment stepsFragment = new StepsFragment();
                return stepsFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}