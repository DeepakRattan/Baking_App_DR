package com.example.baking_app_dr.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.adapter.MyPagerAdaper;
import com.example.baking_app_dr.databinding.ActivityRecipeInfoBinding;
import com.example.baking_app_dr.fragment.IngredientsFragment;
import com.example.baking_app_dr.model.BakingResponse;
import com.example.baking_app_dr.model.Ingredient;
import com.example.baking_app_dr.model.Step;

import java.util.List;

public class RecipeInfoActivity extends AppCompatActivity {

    ActivityRecipeInfoBinding activityRecipeInfoBinding;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    public BakingResponse response;
    public static final String TAG = "test";
    List<Ingredient> ingredientList;
    List<Step> stepList;
    String recipe_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecipeInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_info);
        setSupportActionBar(activityRecipeInfoBinding.toolbar);

        //Adding Tabs
        activityRecipeInfoBinding.tabLayout.addTab(activityRecipeInfoBinding.tabLayout.newTab().setText("Ingredients"));
        activityRecipeInfoBinding.tabLayout.addTab(activityRecipeInfoBinding.tabLayout.newTab().setText("Steps"));

        adapter = new MyPagerAdaper(getSupportFragmentManager(), activityRecipeInfoBinding.tabLayout.getTabCount());
        activityRecipeInfoBinding.viewPager.setAdapter(adapter);
        activityRecipeInfoBinding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        activityRecipeInfoBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                activityRecipeInfoBinding.viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recipe_name = getIntent().getExtras().getString("");
        Log.d(TAG, "RecipeInfoActivity:RecipeName" + recipe_name);
        ingredientList = getIntent().getParcelableArrayListExtra("ingredient_list");
        Log.d(TAG, "Size in RecipeInfoActivity " + ingredientList.size());
        stepList = getIntent().getParcelableArrayListExtra("step_list");


    }


    public String getRecipeName() {
        return recipe_name;

    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }


}
