package com.example.baking_app_dr.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.adapter.IngredientsAdapter;
import com.example.baking_app_dr.adapter.StepAdapter;
import com.example.baking_app_dr.databinding.ActivityTabBinding;
import com.example.baking_app_dr.model.Ingredient;
import com.example.baking_app_dr.model.Step;
import com.example.baking_app_dr.util.RecipeOnItemClickListener;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TabActivity extends AppCompatActivity {
    ActivityTabBinding activityTabBinding;
    public static final String TAG = "test";
    List<Ingredient> ingredientList;
    List<Step> stepList;
    SimpleExoPlayer mExoPlayer;
    Step step;
    String recipe_name;
    StepAdapter stepAdapter;
    IngredientsAdapter ingredientsAdapter;
    RecyclerView.LayoutManager layoutManager, layoutManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tab);
        activityTabBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab);

        recipe_name = getIntent().getExtras().getString("recipe_name");
        Log.d(TAG, "RecipeInfoActivity:RecipeName" + recipe_name);
        ingredientList = getIntent().getParcelableArrayListExtra("ingredient_list");
        Log.d(TAG, "Size in RecipeInfoActivity " + ingredientList.size());
        stepList = getIntent().getParcelableArrayListExtra("step_list");
        activityTabBinding.txtName.setText(recipe_name);

        //Step Adapter
        stepAdapter = new StepAdapter(this, stepList, new RecipeOnItemClickListener() {
            @Override
            public void onRecipeItemClick(int pos) {
                String shortDescription = stepList.get(pos).getShortDescription();
                step = stepList.get(pos);
                Toast.makeText(TabActivity.this, "Short Description " + shortDescription, Toast.LENGTH_SHORT).show();
                load();
            }
        });


        layoutManager = new LinearLayoutManager(this);
        activityTabBinding.rvSteps.setLayoutManager(layoutManager);
        activityTabBinding.rvSteps.setAdapter(stepAdapter);

        //Ingredient Adapter
        ingredientsAdapter = new IngredientsAdapter(this, ingredientList);
        layoutManager1 = new LinearLayoutManager(this);
        activityTabBinding.rvIngredients.setLayoutManager(layoutManager1);
        ingredientsAdapter = new IngredientsAdapter(this, ingredientList);
        activityTabBinding.rvIngredients.setAdapter(ingredientsAdapter);


    }

    private void load() {

        activityTabBinding.tv1StepShortDescription.setText(step.getShortDescription());
        activityTabBinding.txt1StepDescription.setText(step.getDescription());

        // General Case
        // 1 - If Video exists: Show Video
        // 2 - If Thumbnail exists: Show image view with the thumbnail where at the place where the video would normally show up.

        // If video exists, initialize player (General case 1)
        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializerPlayer(Uri.parse(step.getVideoURL()));
        else if (!step.getThumbnailURL().isEmpty()) {
            // if Thumbnail exists but no video exists (General case 2)
            activityTabBinding.imgThumbnailView1.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext())
                    .load(step.getThumbnailURL())
                    .into(activityTabBinding.imgThumbnailView1);
        }
    }

    Step getSelectedStepDescription() {
        return step;
    }

    private void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            activityTabBinding.playerView1.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext(), trackSelector, loadControl);
            activityTabBinding.playerView1.setPlayer(mExoPlayer);

            activityTabBinding.playerView1.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getApplicationContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getApplicationContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }


    }

    private long position = -1;
    private boolean playWhenReady = true;

    @Override
    protected void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}
