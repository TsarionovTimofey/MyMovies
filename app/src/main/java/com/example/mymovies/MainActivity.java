package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.Movie;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        JSONObject jsonObject = NetworkUtils.getJSONFromNetworkTopDownload(NetworkUtils.INT_TOP_250_BEST_FILMS, 1);
        ArrayList<Movie> moviesTop = JSONUtils.getTOPFilmsFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();
        for (Movie movie : moviesTop) {
            builder.append(movie.getFilmId()).append("\n");
            builder.append(movie.getNameRu()).append("\n");
            builder.append(movie.getPosterUrlPreview()).append("\n");
        }
        Log.i("result", builder.toString());
        if (jsonObject == null) {
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();
            String tostring = jsonObject.toString();
            textView.setText(tostring);
        }
        JSONObject jsonObject1 = NetworkUtils.getJSONFromNetworkFilmDetailDownload(301);
        ArrayList<Movie> movieDetailed = JSONUtils.getFilmDetailsFromJSON(jsonObject1);
        StringBuilder builder2 = new StringBuilder();
        for (Movie movie : movieDetailed) {
            builder2.append(movie.getFilmId()).append("\n");
            builder2.append(movie.getNameRu()).append("\n");
            builder2.append(movie.getNameEn()).append("\n");
            builder2.append(movie.getYear()).append("\n");
            builder2.append(movie.getFilmLength()).append("\n");
            builder2.append(movie.getPosterUrl()).append("\n");
            builder2.append(movie.getRating()).append("\n");
            builder2.append(movie.getRatingVoteCount()).append("\n");
            builder2.append(movie.getDescription()).append("\n");
            builder2.append(movie.getFacts()).append("\n");
            builder2.append(movie.getPremiereWorld()).append("\n");
        }
        Log.i("result", builder2.toString());

        if (jsonObject == null) {
            Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();
            String tostring = jsonObject1.toString();
            editText.setText(tostring);
        }

    }
}