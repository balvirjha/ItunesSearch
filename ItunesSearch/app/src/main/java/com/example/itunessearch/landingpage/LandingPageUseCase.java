package com.example.itunessearch.landingpage;

import android.text.TextUtils;

import com.example.itunessearch.R;
import com.example.itunessearch.SearchApplication;
import com.example.itunessearch.api.ApiService;
import com.example.itunessearch.model.DateUtils;
import com.example.itunessearch.model.Event;
import com.example.itunessearch.model.ItuneResult;
import com.example.itunessearch.model.Resource;
import com.example.itunessearch.model.landing.ItuneResponse;
import com.example.itunessearch.model.landing.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@SuppressWarnings({"NullableProblems", "NumberEquality"})
public class LandingPageUseCase {

    final ApiService apiService;

    public LandingPageUseCase(ApiService apiService) {
        this.apiService = apiService;
    }

    private List<Result> getSearchByArtistName(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        List<Result> resultsSearched = new ArrayList<>();
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (!TextUtils.isEmpty(result.getArtistName()) && result.getArtistName().trim().toLowerCase().startsWith(searchText.toLowerCase())) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSearchByTrackName(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        List<Result> resultsSearched = new ArrayList<>();
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (!TextUtils.isEmpty(result.getTrackName()) && result.getTrackName().trim().toLowerCase().startsWith(searchText.toLowerCase())) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSearchByCollectionName(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        List<Result> resultsSearched = new ArrayList<>();
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (!TextUtils.isEmpty(result.getCollectionName()) && result.getCollectionName().trim().toLowerCase().startsWith(searchText.toLowerCase())) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSearchByReleaseDate(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        List<Result> resultsSearched = new ArrayList<>();
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (!TextUtils.isEmpty(result.getReleaseDate()) && result.getReleaseDate().trim().toLowerCase().startsWith(searchText.toLowerCase())) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSearchByCollectionPrice(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        Double searchValue;
        List<Result> resultsSearched = new ArrayList<>();
        try {
            searchValue = Double.parseDouble(searchText);
        } catch (Exception e) {
            return resultsSearched;
        }
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (result.getCollectionPrice() == searchValue) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSearchByArtWork(List<Result> ituneResponse, String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            return ituneResponse;
        }
        List<Result> resultsSearched = new ArrayList<>();
        if (ituneResponse != null && ituneResponse.size() > 0) {

            for (Result result :
                    ituneResponse) {
                if (!TextUtils.isEmpty(result.getArtworkUrl100()) && result.getArtworkUrl100().trim().toLowerCase().startsWith(searchText.toLowerCase())) {
                    resultsSearched.add(result);
                }
            }
        }
        return resultsSearched;
    }

    private List<Result> getSortByArtistName(List<Result> ituneResponse) {
        if (ituneResponse != null && ituneResponse.size() > 0) {
            Collections.sort(ituneResponse, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getArtistName()) && !TextUtils.isEmpty(t1.getArtistName())) {
                    return result.getArtistName().compareTo(t1.getArtistName());
                }
                return 0;
            });
        }
        return ituneResponse;
    }

    private List<Result> getSortByArtWorkUrl100(List<Result> ituneResponse) {
        if (ituneResponse != null && ituneResponse.size() > 0) {
            Collections.sort(ituneResponse, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getArtworkUrl100()) && !TextUtils.isEmpty(t1.getArtworkUrl100())) {
                    return result.getArtworkUrl100().compareTo(t1.getArtworkUrl100());
                }
                return 0;
            });
        }
        return ituneResponse;
    }

    private List<Result> getSortByCollectionName(List<Result> ituneResponse) {
        if (ituneResponse != null && ituneResponse.size() > 0) {
            Collections.sort(ituneResponse, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getCollectionName()) && !TextUtils.isEmpty(t1.getCollectionName())) {
                    return result.getCollectionName().compareTo(t1.getCollectionName());
                }
                return 0;
            });
        }
        return ituneResponse;
    }

    private List<Result> getSortByCollectionPrice(List<Result> ituneResponse) {
        if (ituneResponse != null && ituneResponse.size() > 0) {
            Collections.sort(ituneResponse, (result, t1) -> result.getCollectionPrice().compareTo(t1.getCollectionPrice()));
        }
        return ituneResponse;
    }

    private List<Result> getSortByTrackName(List<Result> ituneResponse) {
        if (ituneResponse != null && ituneResponse.size() > 0) {
            Collections.sort(ituneResponse, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getTrackName()) && !TextUtils.isEmpty(t1.getTrackName())) {
                    return result.getTrackName().compareTo(t1.getTrackName());
                }
                return 0;
            });
        }
        return ituneResponse;
    }

    public LiveData<Event<Resource<List<Result>>>> getSortedTracks(SearchState searchState, List<Result> ituneResponse) {
        final MutableLiveData<Event<Resource<List<Result>>>> liveData = new MutableLiveData<>();
        switch (searchState) {
            case ARTIST_NAME:
                liveData.postValue(new Event<>(Resource.success(getSortByArtistName(ituneResponse))));
                break;
            case ARTWORK_URL_100:
                liveData.postValue(new Event<>(Resource.success(getSortByArtWorkUrl100(ituneResponse))));
                break;
            case COLLECTION_NAME:
                liveData.postValue(new Event<>(Resource.success(getSortByCollectionName(ituneResponse))));
                break;
            case COLLECTION_PRICE:
                liveData.postValue(new Event<>(Resource.success(getSortByCollectionPrice(ituneResponse))));
                break;
            case RELEASE_DATE:
                liveData.postValue(new Event<>(Resource.success(sortAscendingDates(ituneResponse))));
                break;
            case TRACK_NAME:
                liveData.postValue(new Event<>(Resource.success(getSortByTrackName(ituneResponse))));
                break;
        }
        return liveData;
    }

    public LiveData<Event<Resource<List<Result>>>> getSearchedTracks(SearchState searchState, List<Result> ituneResponse, String searchText) {
        final MutableLiveData<Event<Resource<List<Result>>>> liveData = new MutableLiveData<>();
        switch (searchState) {
            case ARTIST_NAME:
                liveData.postValue(new Event<>(Resource.success(getSearchByArtistName(ituneResponse, searchText))));
                break;
            case ARTWORK_URL_100:
                liveData.postValue(new Event<>(Resource.success(getSearchByArtWork(ituneResponse, searchText))));
                break;
            case COLLECTION_NAME:
                liveData.postValue(new Event<>(Resource.success(getSearchByCollectionName(ituneResponse, searchText))));
                break;
            case COLLECTION_PRICE:
                liveData.postValue(new Event<>(Resource.success(getSearchByCollectionPrice(ituneResponse, searchText))));
                break;
            case RELEASE_DATE:
                liveData.postValue(new Event<>(Resource.success(getSearchByReleaseDate(ituneResponse, searchText))));
                break;
            case TRACK_NAME:
                liveData.postValue(new Event<>(Resource.success(getSearchByTrackName(ituneResponse, searchText))));
                break;
        }
        return liveData;
    }

    public LiveData<Event<Resource<ItuneResult>>> getTracks() {
        final MutableLiveData<Event<Resource<ItuneResult>>> liveData = new MutableLiveData<>();
        liveData.postValue(new Event<>(Resource.loading(null)));
        apiService.getTracks("all").enqueue(new Callback<ItuneResponse>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(Call<ItuneResponse> call, Response<ItuneResponse> response) {
                if (response.isSuccessful()) {
                    ItuneResponse ituneResponse = response.body();
                    if (ituneResponse != null && ituneResponse.getResultCount() > 0) {
                        ituneResponse = removeDuplicates(ituneResponse);
                        sortAscendingDates(ituneResponse.getResults());
                        liveData.postValue(new Event<>(Resource.success(new ItuneResult(ituneResponse))));
                    } else {
                        //blank
                        liveData.postValue(new Event<>(Resource.error("", new ItuneResult(ituneResponse))));
                    }

                } else {
                    ItuneResult uiResponse = new ItuneResult();
                    uiResponse.setTitle(SearchApplication.getInstance().getResources().getString(R.string.error));
                    uiResponse.setDescription(SearchApplication.getInstance().getResources().getString(R.string.somethingWentWrong));
                    liveData.postValue(new Event<>(Resource.error("", uiResponse)));
                }
            }

            @Override
            public void onFailure(Call<ItuneResponse> call, Throwable t) {
                ItuneResult uiResponse = new ItuneResult();
                uiResponse.setTitle(SearchApplication.getInstance().getResources().getString(R.string.error));
                uiResponse.setDescription(SearchApplication.getInstance().getResources().getString(R.string.somethingWentWrong));
                liveData.postValue(new Event<>(Resource.error("", uiResponse)));
            }
        });
        return liveData;
    }

    private ItuneResponse removeDuplicates(ItuneResponse ituneResponse) {
        List<Result> results = ituneResponse.getResults();
        if (results != null && results.size() > 0) {
            List<Result> unique = results.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Result::getTrackName))),
                            ArrayList::new));
            ituneResponse.setResults(unique);
            ituneResponse.setResultCount(unique.size());
        }
        return ituneResponse;
    }

    private List<Result> sortAscendingDates(List<Result> ituneResponse) {
        Collections.sort(ituneResponse, (o1, o2) -> {
            if (TextUtils.isEmpty(o1.getReleaseDate()) || TextUtils.isEmpty(o2.getReleaseDate()))
                return 0;
            Date prevReleaseDate = DateUtils.getInstance().convertUTCDateStringToDateObject(o1.getReleaseDate(), DateUtils.DATE_FORMAT_8);
            Date nextReleaseDate = DateUtils.getInstance().convertUTCDateStringToDateObject(o2.getReleaseDate(), DateUtils.DATE_FORMAT_8);
            if (prevReleaseDate != null && nextReleaseDate != null) {
                return prevReleaseDate.compareTo(nextReleaseDate);
            }
            return 0;
        });
        return ituneResponse;
    }

}
