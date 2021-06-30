package com.example.mymovies.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.MoviePreview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviePreviewAdapter extends RecyclerView.Adapter<MoviePreviewAdapter.MovieViewHolder> {

    private List<MoviePreview> moviePreviews;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public MoviePreviewAdapter() {
        this.moviePreviews = new ArrayList<>();
    }


    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position > moviePreviews.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        MoviePreview moviePreview = moviePreviews.get(position);
        Picasso.get().load(moviePreview.getPosterUrlPreview()).into(holder.imageViewSmallPoster);
        double rating = moviePreview.getRating();
        holder.rating.setText(Double.toString(rating));
        if (rating > 7.5) {
            holder.rating.setTextColor(Color.parseColor("#008500"));
        } else if (rating < 7.5 & rating > 6.6) {
            holder.rating.setTextColor(Color.YELLOW);
        } else {
            holder.rating.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return moviePreviews.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;
        private TextView rating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            rating = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public List<MoviePreview> getMoviePreviews() {
        return moviePreviews;
    }

    public void addMovies(List<MoviePreview> moviePreviews) {
        this.moviePreviews.addAll(moviePreviews);
        notifyDataSetChanged();
    }

    public void setMoviePreviews(List<MoviePreview> moviePreviews) {
        this.moviePreviews = moviePreviews;
        notifyDataSetChanged();
    }
}
