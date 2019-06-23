package com.example.baking_app_dr.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.adapter.MainAdapter;
import com.example.baking_app_dr.databinding.ActivityMainBinding;
import com.example.baking_app_dr.model.BakingResponse;
import com.example.baking_app_dr.model.Ingredient;
import com.example.baking_app_dr.model.Step;
import com.example.baking_app_dr.network.APIClient;
import com.example.baking_app_dr.network.APIInterface;
import com.example.baking_app_dr.network.ConnectivityHelper;
import com.example.baking_app_dr.util.DeviceTypeCheck;
import com.example.baking_app_dr.util.RecipeOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private ActivityMainBinding binding;
    private GridLayoutManager layoutManager;
    private APIInterface apiInterface;
    private MainAdapter adapter;
    List<Ingredient> ingredientList;
    List<Step> stepList;

    //Checking for internet connectivity
    boolean isConnectedToInternet;
    private String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // setTitle(getString(R.string.baking_application_project));

        context = this;
        layoutManager = new GridLayoutManager(this, 2);
        binding.rv.setLayoutManager(layoutManager);

        setSupportActionBar(binding.toolbarMain);
        getSupportActionBar().setTitle(getString(R.string.baking_application_project));

        isConnectedToInternet = ConnectivityHelper.isConnectedToNetwork(MainActivity.this);

        if (isConnectedToInternet)
            loadDataFromServer();
        else
            Toast.makeText(this, "Please check internet connectivity", Toast.LENGTH_SHORT).show();
    }


    private void loadDataFromServer() {
        //binding.mainLayoutSwipe.setRefreshing(true);
        apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);
        Call<List<BakingResponse>> call = apiInterface.getBakingData();
        call.enqueue(new Callback<List<BakingResponse>>() {
            @Override
            public void onResponse(Call<List<BakingResponse>> call, Response<List<BakingResponse>> response) {
                Log.d(TAG, "onResponse: ");
                List<BakingResponse> bakingResponse = response.body();
                Log.d(TAG, "onResponse: " + bakingResponse.get(0).getName());


                adapter = new MainAdapter(MainActivity.this, bakingResponse, new RecipeOnItemClickListener() {
                    @Override
                    public void onRecipeItemClick(int pos) {
                        BakingResponse bakingResponse1 = adapter.getRecipeAtIndex(pos);
                        String name = bakingResponse1.getName();
                        ingredientList = bakingResponse1.getIngredients();
                        stepList = bakingResponse1.getSteps();
                        if (DeviceTypeCheck.isTablet(getApplicationContext())) {
                            Log.d(TAG, "onCreate: tab");
                            Toast.makeText(MainActivity.this, "Tablet", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, TabActivity.class);
                            intent.putParcelableArrayListExtra("ingredient_list", (ArrayList<? extends Parcelable>) ingredientList);
                            intent.putParcelableArrayListExtra("step_list", (ArrayList<? extends Parcelable>) stepList);
                            intent.putExtra("recipe_name", name);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, RecipeInfoActivity.class);
                            intent.putParcelableArrayListExtra("ingredient_list", (ArrayList<? extends Parcelable>) ingredientList);
                            intent.putParcelableArrayListExtra("step_list", (ArrayList<? extends Parcelable>) stepList);
                            intent.putExtra("recipe_name", name);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Item is " + bakingResponse1.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                binding.rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<BakingResponse>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                //binding.mainLayoutSwipe.setRefreshing(false);


            }
        });

    }
}
