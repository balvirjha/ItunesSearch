package com.example.itunessearch.landingpage;

import com.example.itunessearch.model.Event;
import com.example.itunessearch.model.ItuneResult;
import com.example.itunessearch.model.Resource;
import com.example.itunessearch.model.landing.ItuneResponse;
import com.example.itunessearch.model.landing.Result;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

@SuppressWarnings("UnusedAssignment")
public class LandingPageViewModal extends ViewModel {
    LiveData<Event<Resource<List<Result>>>> searchEventLiveData = new MutableLiveData<>();
    LiveData<Event<Resource<List<Result>>>> sortEventLiveData = new MutableLiveData<>();
    final MutableLiveData<SearchParam> callSearch = new MutableLiveData<>();
    final MutableLiveData<SearchState> callSort = new MutableLiveData<>();
    private final LandingPageUseCase landingPageUseCase;
    private List<Result> resultListOriginal = new ArrayList<>();

    public LandingPageViewModal(LandingPageUseCase landingPageUseCase) {
        this.landingPageUseCase = landingPageUseCase;
        searchEventLiveData = Transformations.switchMap(callSearch, input -> landingPageUseCase.getSearchedTracks(input.getSearchState(), resultListOriginal, input.getSearchText()));
        sortEventLiveData = Transformations.switchMap(callSort, input -> landingPageUseCase.getSortedTracks(input, resultListOriginal));
    }

    public LiveData<Event<Resource<List<Result>>>> getSortEventLiveData() {
        return sortEventLiveData;
    }

    public LiveData<Event<Resource<ItuneResult>>> getTracks() {
        MediatorLiveData<Event<Resource<ItuneResult>>> mediatorLiveData = new MediatorLiveData<>();
        LiveData<Event<Resource<ItuneResult>>> eventLiveData = landingPageUseCase.getTracks();
        mediatorLiveData.removeSource(eventLiveData);
        mediatorLiveData.addSource(eventLiveData, resourceEvent -> {
            Resource<ItuneResult> ituneResultResource = resourceEvent.getContentIfNotHandled();
            if (ituneResultResource != null) {
                ItuneResult data = ituneResultResource.data;
                if (data != null) {
                    ItuneResponse ituneResponse = data.getItuneResponse();
                    if (ituneResponse != null) {
                        resultListOriginal = ituneResponse.getResults();
                        mediatorLiveData.postValue(new Event<>(Resource.success(data)));
                    } else {
                        mediatorLiveData.postValue(new Event<>(Resource.error("", data)));
                    }
                }
            }
        });
        return mediatorLiveData;
    }

    public LiveData<Event<Resource<List<Result>>>> getSearchEventLiveData() {
        return searchEventLiveData;
    }

    public void getSearchedTracks(SearchParam searchParam) {
        callSearch.postValue(searchParam);
    }

    public void sortData(SearchState sortState) {
        callSort.postValue(sortState);
    }
}
