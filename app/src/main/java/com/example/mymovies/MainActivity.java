package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapterPopular;
    private MovieAdapter movieAdapterTop;
    private MovieAdapter movieAdapterTopAwait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapterPopular = new MovieAdapter();
        movieAdapterTop = new MovieAdapter();
        movieAdapterTopAwait = new MovieAdapter();
        setTopRated();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int typeOfTop;
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


    }

    private void setTopRated() {
        int typeOfTop = NetworkUtils.INT_TOP_250_BEST_FILMS;
        JSONObject jsonObjectTop = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<Movie> moviesTop = JSONUtils.getTOPFilmsFromJSON(jsonObjectTop);
        movieAdapterTop.setMovies(moviesTop);
        recyclerViewPosters.setAdapter(movieAdapterTop);
        movieAdapterTop.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Toast.makeText(MainActivity.this, "clicked" + position, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            }
        });
        movieAdapterTop.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPopular() {
        int typeOfTop = NetworkUtils.INT_TOP_100_POPULAR;
        JSONObject jsonObjectPopular = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<Movie> moviesPopular = JSONUtils.getTOPFilmsFromJSON(jsonObjectPopular);
        movieAdapterPopular.setMovies(moviesPopular);
        recyclerViewPosters.setAdapter(movieAdapterPopular);
    }

    private void setTopAwait() {
        int typeOfTop = NetworkUtils.INT_TOP_AWAIT_FILMS;
        JSONObject jsonObjectTopAwaits = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<Movie> moviesTopAwaits = JSONUtils.getTOPFilmsFromJSON(jsonObjectTopAwaits);
        movieAdapterTopAwait.setMovies(moviesTopAwaits);
        recyclerViewPosters.setAdapter(movieAdapterTopAwait);
    }
}