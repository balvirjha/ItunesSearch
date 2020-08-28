package com.example.itunessearch.model;

import androidx.annotation.NonNull;

import com.example.itunessearch.model.landing.ItuneResponse;

public class ItuneResult extends BaseResponse {
    private ItuneResponse ituneResponse;

    public ItuneResult() {
    }

    public ItuneResult(@NonNull String title, @NonNull String description) {
        super(title, description);
    }

    public ItuneResult(ItuneResponse ituneResponse) {
        this.ituneResponse = ituneResponse;
    }

    public ItuneResponse getItuneResponse() {
        return ituneResponse;
    }

    public void setItuneResponse(ItuneResponse ituneResponse) {
        this.ituneResponse = ituneResponse;
    }
}