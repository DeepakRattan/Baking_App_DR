package com.example.baking_app_dr.network;

import com.example.baking_app_dr.model.BakingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<BakingResponse>> getBakingData();
}
