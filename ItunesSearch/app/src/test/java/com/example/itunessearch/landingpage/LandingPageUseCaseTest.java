package com.example.itunessearch.landingpage;

import com.example.itunessearch.api.ApiService;
import com.example.itunessearch.model.Event;
import com.example.itunessearch.model.ItuneResult;
import com.example.itunessearch.model.Resource;
import com.example.itunessearch.model.landing.ItuneResponse;
import com.example.itunessearch.model.landing.Result;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked", "ConstantConditions"})
public class LandingPageUseCaseTest {
    LandingPageUseCase landingPageUseCase;
    ApiService apiService;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        apiService = mock(ApiService.class);
        landingPageUseCase = new LandingPageUseCase(apiService);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testGetSortedTracks() {
        Observer observer = mock(Observer.class);
        List<Result> resultList = new ArrayList<>();
        landingPageUseCase.getSortedTracks(SearchState.ARTIST_NAME, resultList).observeForever(observer);
        ArgumentCaptor<Event<Resource<List<Result>>>> argumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(observer).onChanged(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getContentIfNotHandled().data.size(), 0);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testGetTracks() {
        MutableLiveData<Event<Resource<ItuneResult>>> liveData = new MutableLiveData();
        ItuneResult ituneResult = new ItuneResult();
        ItuneResponse ituneResponse = new ItuneResponse();
        ituneResponse.setResultCount(0);
        List<Result> resultList = new ArrayList<>();
        ituneResponse.setResults(resultList);
        ituneResult.setItuneResponse(ituneResponse);
        Resource<ItuneResult> resource = Resource.success(ituneResult);
        Event<Resource<ItuneResult>> event = new Event<>(resource);
        liveData.setValue(event);

        Call<ItuneResponse> ituneResponseCall = mock(Call.class);

        when(apiService.getTracks(any())).thenReturn(ituneResponseCall);

        Observer observer = mock(Observer.class);
        landingPageUseCase.getTracks().observeForever(observer);

        verify(observer).onChanged(any());
    }
}