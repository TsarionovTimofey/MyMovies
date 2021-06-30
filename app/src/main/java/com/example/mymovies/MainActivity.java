package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymovies.adapters.MoviePreviewAdapter;
import com.example.mymovies.data.MoviePreview;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager
        .LoaderCallbacks<JSONObject> {

    private RecyclerView recyclerViewPosters;
    private MoviePreviewAdapter moviePreviewAdapterPopular;
    private MoviePreviewAdapter moviePreview;
    private MoviePreviewAdapter moviePreviewAwait;

    private MainViewModel viewModel;

    private static final int LOADER_ID = 12345;
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderManager = LoaderManager.getInstance(this);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(MainViewModel.class);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));

        moviePreviewAdapterPopular = new MoviePreviewAdapter();
        moviePreview = new MoviePreviewAdapter();
        moviePreviewAwait = new MoviePreviewAdapter();
        setTopRated();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_top:
                        setTopRated();
                        return true;
                    case R.id.action_popular:
                        setPopular();
                        return true;
                    case R.id.action_top_await:
                        setTopAwait();
                        return true;
                }
                return false;
            }
        });

        LiveData<List<MoviePreview>> moviesTopRatedFromLiveData = viewModel.getMoviesTopRated();
        moviesTopRatedFromLiveData.observe(this, new Observer<List<MoviePreview>>() {
            @Override
            public void onChanged(List<MoviePreview> moviesTop) {
                moviePreview.setMoviePreviews(moviesTop);
            }
        });


        moviePreview.setOnPosterClickListener(new MoviePreviewAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                MoviePreview movie = moviePreview.getMoviePreviews().get(position);
                Toast.makeText(MainActivity.this, "clicked" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("filmId", movie.getFilmId());
                startActivity(intent);
            }
        });
        moviePreview.setOnReachEndListener(new MoviePreviewAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setTopRated() {
        int typeOfTop = NetworkUtils.INT_TOP_250_BEST_FILMS;
        downloadDataForTopRated(typeOfTop, 1);
        recyclerViewPosters.setAdapter(moviePreview);
    }

    private void setPopular() {
        int typeOfTop = NetworkUtils.INT_TOP_100_POPULAR;
        JSONObject jsonObjectPopular = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<MoviePreview> moviesPopular = JSONUtils.getTOPFilmsFromJSON(jsonObjectPopular);
        moviePreviewAdapterPopular.setMoviePreviews(moviesPopular);
        recyclerViewPosters.setAdapter(moviePreviewAdapterPopular);
    }

    private void setTopAwait() {
        int typeOfTop = NetworkUtils.INT_TOP_AWAIT_FILMS;
        JSONObject jsonObjectTopAwaits = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<MoviePreview> moviesTopAwaits = JSONUtils.getTOPFilmsFromJSON(jsonObjectTopAwaits);
        moviePreviewAwait.setMoviePreviews(moviesTopAwaits);
        recyclerViewPosters.setAdapter(moviePreviewAwait);
    }

    private void downloadDataForTopRated(int typeOfTop, int page) {
        JSONObject jsonObjectTop = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, page);
        ArrayList<MoviePreview> moviesTop = JSONUtils.getTOPFilmsFromJSON(jsonObjectTop);
        if (moviesTop != null && !moviesTop.isEmpty()) {
            viewModel.deleteAllMovies();
            for (MoviePreview moviePreview : moviesTop) {
                viewModel.insertMovie(moviePreview);
            }
        }
    }

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

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this, args);
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}