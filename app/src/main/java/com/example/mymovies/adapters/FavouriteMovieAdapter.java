package com.example.mymovies.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.FavouriteMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMovieAdapter extends RecyclerView.Adapter<FavouriteMovieAdapter.MovieViewHolder> {

    private List<FavouriteMovie> favouriteMovies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public FavouriteMovieAdapter() {
        this.favouriteMovies = new ArrayList<>();
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if (position > favouriteMovies.size() - 2 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        FavouriteMovie favouriteMovie = favouriteMovies.get(position);
        Picasso.get().load(favouriteMovie.getPosterUrl()).into(holder.imageViewSmallPoster);
        double rating = favouriteMovie.getRating();
        holder.rating.setText(Double.toString(rating));
        if (rating < 5) {
            holder.rating.setTextColor(Color.RED);
        } else {
            holder.rating.setTextColor(Color.parseColor("#008500"));
        }
    }

    @Override
    public int getItemCount() {
        return favouriteMovies.size();
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

    public void addFavouriteMovies(List<FavouriteMovie> favouriteMovies) {
        this.favouriteMovies.addAll(favouriteMovies);
        notifyDataSetChanged();
    }

    public void setFavouriteMovies(List<FavouriteMovie> favouriteMovies) {
        this.favouriteMovies = favouriteMovies;
        notifyDataSetChanged();
        Log.i("TAG", favouriteMovies.get(0).getNameEn());
    }

    public List<FavouriteMovie> getFavouriteMovies() {
        return favouriteMovies;
    }
}



