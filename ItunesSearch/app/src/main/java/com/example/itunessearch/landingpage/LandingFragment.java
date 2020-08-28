package com.example.itunessearch.landingpage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itunessearch.R;
import com.example.itunessearch.SearchApplication;
import com.example.itunessearch.cartpage.CartFragment;
import com.example.itunessearch.databinding.FragmentLandingBinding;
import com.example.itunessearch.model.ItuneResult;
import com.example.itunessearch.model.Resource;
import com.example.itunessearch.model.SelectedTracks;
import com.example.itunessearch.model.landing.ItuneResponse;
import com.example.itunessearch.model.landing.Result;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.itunessearch.landingpage.SearchState.ARTIST_NAME;
import static com.example.itunessearch.landingpage.SearchState.ARTWORK_URL_100;
import static com.example.itunessearch.landingpage.SearchState.COLLECTION_NAME;
import static com.example.itunessearch.landingpage.SearchState.COLLECTION_PRICE;
import static com.example.itunessearch.landingpage.SearchState.RELEASE_DATE;
import static com.example.itunessearch.landingpage.SearchState.TRACK_NAME;

@SuppressWarnings("ConstantConditions")
public class LandingFragment extends Fragment implements TrackAdapter.trackSelectViewClickListener {
    private LandingPageViewModal viewModel;
    private FragmentLandingBinding binding;
    private TrackAdapter trackAdapter;
    private SearchState searchState, sortState;
    private List<Result> selectedTracks = new ArrayList<>();

    private LandingFragment() {
        // Required empty private constructor
    }

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchApplication app = SearchApplication.getInstance();
        LandingPageUseCase landingPageUseCase = new LandingPageUseCase(app.getApiService());
        LandingPageViewModelFactory viewModelFactory = new LandingPageViewModelFactory(landingPageUseCase);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(LandingPageViewModal.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLandingBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        ((MainActivity) getActivity()).enableBackButton(false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showTracks();
        viewModel.getTracks().observe(getViewLifecycleOwner(), resourceEvent -> {
            Resource<ItuneResult> ituneResultResource = resourceEvent.getContentIfNotHandled();
            binding.setResource(ituneResultResource);
            if (ituneResultResource != null) {
                ItuneResult data = ituneResultResource.data;
                if (data != null) {
                    ItuneResponse ituneResponse = data.getItuneResponse();
                    if (ituneResponse != null) {
                        if (ituneResponse.getResultCount() > 0) {
                            setListData(ituneResponse.getResults());
                        } else {
                            setListData(ituneResponse.getResults());
                            Toast.makeText(getActivity(), R.string.no_track, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ((MainActivity) getActivity()).showErrorDialog(data.getTitle(), data.getDescription());
                    }
                }
            }
        });
        viewModel.getSearchEventLiveData().observe(getViewLifecycleOwner(), resourceEvent -> {
            Resource<List<Result>> listResource = resourceEvent.getContentIfNotHandled();
            if (listResource != null) {
                List<Result> resultList = listResource.data;
                setListData(resultList);
            }
        });

        viewModel.getSortEventLiveData().observe(getViewLifecycleOwner(), resourceEvent -> {
            Resource<List<Result>> listResource = resourceEvent.getContentIfNotHandled();
            if (listResource != null) {
                List<Result> resultList = listResource.data;
                setListData(resultList);
            }
        });
    }

    private void showTracks() {
        trackAdapter = new TrackAdapter(this);
        RecyclerView recyclerViewTimeLine = binding.recyclerViewTimeLine;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerViewTimeLine.setLayoutManager(mLayoutManager);
        recyclerViewTimeLine.setAdapter(trackAdapter);
    }

    private void setListData(List<Result> listData) {
        trackAdapter.setResults(listData, selectedTracks);
        trackAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.landing_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                enableSearch();
                break;
            case R.id.sort:
                enableSort();
                break;
            case R.id.cart:
                gotoCartScreen();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
    private void gotoCartScreen() {
        if (selectedTracks.size() > 0) {
            Set<Result> set = new LinkedHashSet<>();
            set.addAll(selectedTracks);
            selectedTracks.clear();
            selectedTracks.addAll(set);
            Log.e("bvc", "Size selected: " + selectedTracks.size());
            SelectedTracks selectedTrack = new SelectedTracks();
            selectedTrack.setResults(selectedTracks);
            CartFragment cartFragment = CartFragment.newInstance(selectedTrack);
            ((MainActivity) getActivity()).loadFragment(cartFragment, CartFragment.class.getSimpleName());
        } else {
            Toast.makeText(getActivity(), "No item in the cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void enableSort() {
        TransitionManager.beginDelayedTransition(binding.parentLayout);
        binding.radioScrollSort.setVisibility(View.VISIBLE);
        binding.closeButtonSort.setVisibility(View.VISIBLE);

        binding.radioScroll.setVisibility(View.GONE);
        binding.searchEdit.setVisibility(View.GONE);
        binding.closeButton.setVisibility(View.GONE);
        binding.closeButtonSort.setOnClickListener(view -> {
            TransitionManager.beginDelayedTransition(binding.parentLayout);
            binding.radioScrollSort.setVisibility(View.GONE);
            binding.closeButtonSort.setVisibility(View.GONE);
        });
        binding.sortOptionRadioGroupSort.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.actionArtistnameSort:
                    sortState = ARTIST_NAME;
                    viewModel.sortData(sortState);
                    break;
                case R.id.actionArtworkurl100Sort:
                    sortState = ARTWORK_URL_100;
                    viewModel.sortData(sortState);
                    break;
                case R.id.actionCollectionnameSort:
                    sortState = COLLECTION_NAME;
                    viewModel.sortData(sortState);
                    break;
                case R.id.actionCollectionpriceSort:
                    sortState = COLLECTION_PRICE;
                    viewModel.sortData(sortState);
                    break;
                case R.id.actionReleasedateSort:
                    sortState = RELEASE_DATE;
                    viewModel.sortData(sortState);
                    break;
                case R.id.actionTracknameSort:
                    sortState = TRACK_NAME;
                    viewModel.sortData(sortState);
                    break;
            }

        });
    }

    private void enableSearch() {
        TransitionManager.beginDelayedTransition(binding.parentLayout);
        binding.radioScroll.setVisibility(View.VISIBLE);
        binding.searchEdit.setVisibility(View.VISIBLE);
        binding.closeButton.setVisibility(View.VISIBLE);
        binding.searchEdit.addTextChangedListener(filterTextWatcher);
        binding.radioScrollSort.setVisibility(View.GONE);
        binding.closeButtonSort.setVisibility(View.GONE);
        binding.closeButton.setOnClickListener(view -> {
            TransitionManager.beginDelayedTransition(binding.parentLayout);
            binding.radioScroll.setVisibility(View.GONE);
            binding.searchEdit.setVisibility(View.GONE);
            binding.closeButton.setVisibility(View.GONE);
        });
        binding.searchOptionRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.actionArtistname:
                    searchState = ARTIST_NAME;
                    break;
                case R.id.actionArtworkurl100:
                    searchState = ARTWORK_URL_100;
                    break;
                case R.id.actionCollectionname:
                    searchState = COLLECTION_NAME;
                    break;
                case R.id.actionCollectionprice:
                    searchState = COLLECTION_PRICE;
                    break;
                case R.id.actionReleasedate:
                    searchState = RELEASE_DATE;
                    break;
                case R.id.actionTrackname:
                    searchState = TRACK_NAME;
                    break;
            }

        });
    }


    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (searchState) {
                case ARTIST_NAME:
                    viewModel.getSearchedTracks(new SearchParam(ARTIST_NAME, s.toString()));
                    break;
                case ARTWORK_URL_100:
                    viewModel.getSearchedTracks(new SearchParam(ARTWORK_URL_100, s.toString()));
                    break;
                case COLLECTION_NAME:
                    viewModel.getSearchedTracks(new SearchParam(COLLECTION_NAME, s.toString()));
                    break;
                case COLLECTION_PRICE:
                    viewModel.getSearchedTracks(new SearchParam(COLLECTION_PRICE, s.toString()));
                    break;
                case RELEASE_DATE:
                    viewModel.getSearchedTracks(new SearchParam(RELEASE_DATE, s.toString()));
                    break;
                case TRACK_NAME:
                    viewModel.getSearchedTracks(new SearchParam(TRACK_NAME, s.toString()));
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClickListener(Result result) {
        if (!selectedTracks.contains(result)) {
            selectedTracks.add(result);
        }
    }
}