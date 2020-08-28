package com.example.itunessearch.api;

import com.example.itunessearch.model.landing.ItuneResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search")
    Call<ItuneResponse> getTracks(@Query("term") String term);
}
