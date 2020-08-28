package com.example.itunessearch.landingpage;

import com.example.itunessearch.model.Event;
import com.example.itunessearch.model.ItuneResult;
import com.example.itunessearch.model.Resource;
import com.example.itunessearch.model.landing.ItuneResponse;
import com.example.itunessearch.model.landing.Result;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("rawtypes")
public class LandingPageViewModalTest {

    LandingPageViewModal landingPageViewModal;
    LandingPageUseCase repo;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        repo = mock(LandingPageUseCase.class);
        landingPageViewModal = new LandingPageViewModal(repo);
    }

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

        when(repo.getTracks()).thenReturn(liveData);

        Observer observer = mock(Observer.class);
        landingPageViewModal.getTracks().observeForever(observer);

        verify(observer).onChanged(any());
    }


}