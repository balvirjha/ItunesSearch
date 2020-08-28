package com.example.itunessearch.model;

import com.example.itunessearch.model.landing.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedTracks implements Serializable {
    List<Result> results = new ArrayList<>();

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
