package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymovies.adapters.FavouriteMovieAdapter;
import com.example.mymovies.data.FavouriteMovie;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouriteMovies;
    private FavouriteMovieAdapter adapter;
    private MainViewModel viewModel;


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
                break;
            case R.id.itemFavourites:
                Intent intentFavourites = new Intent(this, FavoriteActivity.class);
                startActivity(intentFavourites);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);
        recyclerViewFavouriteMovies = findViewById(R.id.recyclerViewFavouriteMovies);
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new FavouriteMovieAdapter();
        recyclerViewFavouriteMovies.setAdapter(adapter);
        LiveData<List<FavouriteMovie>> favouriteMovies = viewModel.getFavouriteMovies();
        favouriteMovies.observe(this, new Observer<List<FavouriteMovie>>() {
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                adapter.setFavouriteMovies(favouriteMovies);
            }
        });

        adapter.setOnPosterClickListener(new FavouriteMovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                FavouriteMovie movie = adapter.getFavouriteMovies().get(position);
                Toast.makeText(FavoriteActivity.this, "clicked" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra("filmId", movie.getFilmId());
                startActivity(intent);
            }
        });

    }
}