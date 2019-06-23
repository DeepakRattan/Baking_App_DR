package com.example.baking_app_dr.fragment;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.activity.RecipeInfoDetailActivity;
import com.example.baking_app_dr.databinding.FragmentRecipeInfoDetailBinding;
import com.example.baking_app_dr.model.Step;
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

// Created by Deepak Rattan on 23-Jun-19.
public class RecipeInfoDetailFragment extends Fragment {
    FragmentRecipeInfoDetailBinding binding;
    Step step;
    SimpleExoPlayer mExoPlayer;
    RecipeInfoDetailActivity recipeInfoDetailActivity;
    public static final String TAG = "test";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        if (bundle != null)
            step = bundle.getParcelable("step_desc_key");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_info_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.tvStepShortDescription.setText(step.getShortDescription());
        binding.txtStepDescription.setText(step.getDescription());

        // General Case
        // 1 - If Video exists: Show Video
        // 2 - If Thumbnail exists: Show image view with the thumbnail where at the place where the video would normally show up.

        // If video exists, initialize player (General case 1)
        if (!TextUtils.isEmpty(step.getVideoURL()))
            initializerPlayer(Uri.parse(step.getVideoURL()));
        else if (!step.getThumbnailURL().isEmpty()) {
            // if Thumbnail exists but no video exists (General case 2)
            binding.imgThumbnailView.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(step.getThumbnailURL())
                    .into(binding.imgThumbnailView);
        }


    }


    private void initializerPlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            binding.playerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            binding.playerView.setPlayer(mExoPlayer);

            binding.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (position > 0)
                mExoPlayer.seekTo(position);
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }

    private long position = -1;
    private boolean playWhenReady = true;


    @Override
    public void onPause() {
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
