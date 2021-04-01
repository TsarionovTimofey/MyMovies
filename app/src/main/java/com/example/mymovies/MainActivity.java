package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SwitchMaterial switchSort;
    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapterPopular;
    private MovieAdapter movieAdapterTop;
    private MovieAdapter movieAdapterTopAwait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchSort = findViewById(R.id.switchTypeOfTop);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapterPopular = new MovieAdapter();
        movieAdapterTop = new MovieAdapter();
        movieAdapterTopAwait = new MovieAdapter();
        int typeOfTop;
        typeOfTop = NetworkUtils.INT_TOP_250_BEST_FILMS;
        JSONObject jsonObjectTop = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
        ArrayList<Movie> moviesTop = JSONUtils.getTOPFilmsFromJSON(jsonObjectTop);
        movieAdapterTop.setMovies(moviesTop);
        recyclerViewPosters.setAdapter(movieAdapterTop);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int typeOfTop;
                switch (item.getItemId()) {
                    case R.id.action_top:
                        typeOfTop = NetworkUtils.INT_TOP_250_BEST_FILMS;
                        JSONObject jsonObjectTop = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
                        ArrayList<Movie> moviesTop = JSONUtils.getTOPFilmsFromJSON(jsonObjectTop);
                        movieAdapterTop.setMovies(moviesTop);
                        recyclerViewPosters.setAdapter(movieAdapterTop);
                        return true;
                    case R.id.action_popular:
                        typeOfTop = NetworkUtils.INT_TOP_100_POPULAR;
                        JSONObject jsonObjectPopular = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
                        ArrayList<Movie> moviesPopular = JSONUtils.getTOPFilmsFromJSON(jsonObjectPopular);
                        movieAdapterPopular.setMovies(moviesPopular);
                        recyclerViewPosters.setAdapter(movieAdapterPopular);
                        return true;
                    case R.id.action_top_await:
                        typeOfTop = NetworkUtils.INT_TOP_AWAIT_FILMS;
                        JSONObject jsonObjectTopAwaits = NetworkUtils.getJSONFromNetworkTopDownload(typeOfTop, 1);
                        ArrayList<Movie> moviesTopAwaits = JSONUtils.getTOPFilmsFromJSON(jsonObjectTopAwaits);
                        movieAdapterTopAwait.setMovies(moviesTopAwaits);
                        recyclerViewPosters.setAdapter(movieAdapterTopAwait);
                        return true;
                }
                return false;
            }
        });


    }
}