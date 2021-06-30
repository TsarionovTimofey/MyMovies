package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.adapters.TrailerAdapter;
import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MovieDetailed;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewAddToFavourites;
    private ImageView imageViewPoster;

    private TextView textViewNameRu;
    private TextView textViewNameEn;
    private TextView textViewYear;
    private TextView textViewFilmLength;

    private TextView textViewRating;
    private TextView textViewDescription;
    private TextView textViewFacts;
    private TextView textViewPremiereWorld;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private int filmId;

    private MainViewModel viewModel;
    private MovieDetailed movieDetailed;
    private FavouriteMovie favouriteMovie;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            case R.id.itemFavourites:
                Intent intentFavourites = new Intent(this, FavoriteActivity.class);
                startActivity(intentFavourites);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);

        imageViewAddToFavourites = findViewById(R.id.imageViewAddToFavourites);
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewNameRu = findViewById(R.id.textViewTitle);
        textViewNameEn = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewPremiereWorld = findViewById(R.id.textViewReleaseDate);
        textViewFacts = findViewById(R.id.textViewFacts);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("filmId")) {
            filmId = intent.getIntExtra("filmId", -1);
        } else {
            finish();
        }

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);
        favouriteMovie = viewModel.getFavouriteMovieByID(filmId);
        setFavouritePicture();
        isFavourite();

        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        reviewAdapter = new ReviewAdapter();
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setAdapter(reviewAdapter);

        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);
            }
        });
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);

        JSONObject jsonObjectReviews = NetworkUtils.getJSONFromNetworkReviewsDownload(filmId);
        JSONObject jsonObjectTrailers = NetworkUtils.getJSONFromNetworkFilmVideosDownload(filmId);

        ArrayList<Review> reviews = JSONUtils.getReviewsFromJSON(jsonObjectReviews);
        ArrayList<Trailer> trailers = JSONUtils.getTrailersFromJSON(jsonObjectTrailers);

        reviewAdapter.setReviews(reviews);
        trailerAdapter.setTrailers(trailers);

        Toast.makeText(this, Integer.toString(filmId), Toast.LENGTH_SHORT).show();
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movieDetailed));
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
        }
        setFavouritePicture();
    }

    private void isFavourite() {
        if (favouriteMovie == null) {
            JSONObject jsonObjectMovieDetailed = NetworkUtils.getJSONFromNetworkFilmDetailDownload(filmId);
            movieDetailed = JSONUtils.getFilmDetailsFromJSON(jsonObjectMovieDetailed);
            Picasso.get().load(movieDetailed.getPosterUrl()).into(imageViewPoster);
            textViewNameRu.setText(movieDetailed.getNameRu());
            textViewNameEn.setText(movieDetailed.getNameEn());
            double rating = movieDetailed.getRating();
            textViewRating.setText(Double.toString(rating));
            if (rating < 5) {
                textViewRating.setTextColor(Color.RED);
            } else {
                textViewRating.setTextColor(Color.parseColor("#008500"));
            }
            textViewDescription.setText(movieDetailed.getDescription());
            textViewFacts.setText(movieDetailed.getFacts());
        } else {
            Picasso.get().load(favouriteMovie.getPosterUrl()).into(imageViewPoster);
            textViewNameRu.setText(favouriteMovie.getNameRu());
            textViewNameEn.setText(favouriteMovie.getNameEn());
            double rating = favouriteMovie.getRating();
            textViewRating.setText(Double.toString(rating));
            if (rating < 5) {
                textViewRating.setTextColor(Color.RED);
            } else {
                textViewRating.setTextColor(Color.parseColor("#008500"));
            }
            textViewDescription.setText(favouriteMovie.getDescription());
            textViewFacts.setText(favouriteMovie.getFacts());
        }
    }

    private void setFavouritePicture() {
        if (favouriteMovie == null) {
            imageViewAddToFavourites.setImageResource(R.drawable.favourite_add_to);
        } else {
            imageViewAddToFavourites.setImageResource(R.drawable.favourite_remove);
        }
    }
}