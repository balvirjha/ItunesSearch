package com.example.itunessearch.cartpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itunessearch.databinding.FragmentCartBinding;
import com.example.itunessearch.landingpage.MainActivity;
import com.example.itunessearch.landingpage.TrackAdapter;
import com.example.itunessearch.model.SelectedTracks;
import com.example.itunessearch.model.landing.Result;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("unused")
public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "selectedTracks";
    private SelectedTracks selectedTrack;
    FragmentCartBinding binding;
    private TrackAdapter trackAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(SelectedTracks selectedTrack) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, selectedTrack);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedTrack = (SelectedTracks) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).enableBackButton(true);

        showTracks();
    }

    private void showTracks() {
        trackAdapter = new TrackAdapter(null);
        RecyclerView recyclerViewTimeLine = binding.recyclerViewCart;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerViewTimeLine.setLayoutManager(mLayoutManager);
        recyclerViewTimeLine.setAdapter(trackAdapter);
        setListData(selectedTrack.getResults());
    }

    private void setListData(List<Result> listData) {
        trackAdapter.setResults(listData, null);
        trackAdapter.notifyDataSetChanged();
    }
}