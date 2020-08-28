package com.example.itunessearch;

import android.app.Application;

import com.example.itunessearch.api.APIClient;
import com.example.itunessearch.api.ApiService;

public class SearchApplication extends Application {
    private ApiService apiService;
    private static SearchApplication instance;

    public SearchApplication() {
        //nothing to handle
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static SearchApplication getInstance() {
        return instance;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = APIClient.getClient().create(ApiService.class);
        }
        return apiService;
    }
}
