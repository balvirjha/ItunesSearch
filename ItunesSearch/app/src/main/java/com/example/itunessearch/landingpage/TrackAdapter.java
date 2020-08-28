package com.example.itunessearch.landingpage;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itunessearch.R;
import com.example.itunessearch.databinding.TrackItemsBinding;
import com.example.itunessearch.model.landing.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("ConstantConditions")
public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private List<Result> results;
    private List<Result> selectedResults;
    private final trackSelectViewClickListener mListener;
    private int selectedPosition = -1;

    public void setResults(List<Result> results, List<Result> selectedResults) {
        this.results = results;
        this.selectedResults = selectedResults;
        notifyDataSetChanged();
    }

    public TrackAdapter(trackSelectViewClickListener mListener) {
        this.mListener = mListener;
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        private final TrackItemsBinding itemTimelineBinding;

        TrackViewHolder(final TrackItemsBinding itemTimelineBinding) {
            super(itemTimelineBinding.getRoot());
            this.itemTimelineBinding = itemTimelineBinding;
            this.setIsRecyclable(false);
        }
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = null;
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TrackItemsBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.track_items, parent, false);
        return new TrackViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder timeLineViewHolder, int position) {
        Result result = results.get(position);
        if (mListener == null) {
            timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setVisibility(View.GONE);
        } else {
            timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setVisibility(View.VISIBLE);
            timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setTag(position);
        }
        timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setOnCheckedChangeListener(null);
        if (result != null) {
            if (selectedResults != null && selectedResults.size() > 0 && mListener != null) {
                for (Result result1 :
                        selectedResults) {
                    if (result1.getTrackId().equals(result.getTrackId())) {
                        result1.setSelected(true);
                        timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setChecked(true);
                    }
                }
            }
            timeLineViewHolder.itemTimelineBinding.setResult(result);
        }
        if (mListener != null) {
            timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.setOnClickListener(view -> {
                Integer pos = (Integer) timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.getTag();
                if (selectedResults != null && selectedResults.size()> pos && selectedResults.get(pos).isSelected()) {
                    selectedResults.get(pos).setSelected(false);
                } else if (selectedResults != null && selectedResults.size()> pos) {
                    selectedResults.get(pos).setSelected(true);
                }
                Toast.makeText(timeLineViewHolder.itemTimelineBinding.selectTrackCheckBox.getContext(), results.get(pos).getArtistName() + " clicked!", Toast.LENGTH_SHORT).show();
                mListener.onClickListener(results.get(position));
            });
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    @BindingAdapter("setImage")
    public static void setImageViewResource(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(url)
                    .resize(400, 400)
                    .centerInside()
                    .onlyScaleDown()
                    .error(android.R.drawable.picture_frame)
                    .into(imageView);
        }
    }

    public interface trackSelectViewClickListener {

        void onClickListener(Result result);
    }
}
