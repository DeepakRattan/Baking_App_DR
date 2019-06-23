package com.example.baking_app_dr.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baking_app_dr.R;
import com.example.baking_app_dr.databinding.SingleRowMainCardBinding;
import com.example.baking_app_dr.model.BakingResponse;
import com.example.baking_app_dr.util.RecipeOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

// Created by Deepak Rattan on 31-May-19.
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<BakingResponse> bakingResponse;
    RecipeOnItemClickListener recipeOnItemClickListener;

    public MainAdapter(Context context, List<BakingResponse> bakingResponse, RecipeOnItemClickListener recipeOnItemClickListener) {
        this.context = context;
        this.bakingResponse = bakingResponse;
        this.recipeOnItemClickListener = recipeOnItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SingleRowMainCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.single_row_main_card, viewGroup, false);
        return new MyViewHolder(binding.getRoot(), recipeOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        myViewHolder.binding.tvName.setText(bakingResponse.get(pos).getName());
        myViewHolder.setImage(bakingResponse.get(pos).getImage());
    }

    @Override
    public int getItemCount() {
        return bakingResponse.size();
    }

    public BakingResponse getRecipeAtIndex(int pos) {
        return bakingResponse.get(pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SingleRowMainCardBinding binding;
        RecipeOnItemClickListener recipeOnItemClickListener;

        public MyViewHolder(@NonNull View itemView, RecipeOnItemClickListener recipeOnItemClickListener) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            this.recipeOnItemClickListener = recipeOnItemClickListener;
            itemView.setOnClickListener(this);
        }

        void setImage(String image) {
            if (image.trim().equals("")) {
                binding.iv.setImageResource(R.drawable.ic_image_black_24dp);
            } else {
                Picasso.with(context)
                        .load(image)
                        .into(binding.iv);
            }
        }

        @Override
        public void onClick(View view) {
            recipeOnItemClickListener.onRecipeItemClick(getAdapterPosition());
        }


    }
}