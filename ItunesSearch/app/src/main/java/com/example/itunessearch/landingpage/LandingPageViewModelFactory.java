package com.example.itunessearch.landingpage;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LandingPageViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final LandingPageUseCase landingPageUseCase;

    public LandingPageViewModelFactory(LandingPageUseCase landingPageUseCase) {
        this.landingPageUseCase = landingPageUseCase;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LandingPageViewModal(landingPageUseCase);
    }
}