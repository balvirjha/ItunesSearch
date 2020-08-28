package com.example.itunessearch.model;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class BaseResponse {
    private String title;
    private String description;

    public BaseResponse() {
        title = "";
        description = "";
    }

    public BaseResponse(@NonNull String title, @NonNull String description) {
        this.title = title;
        this.description = description;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
