package com.example.mymovies.utils;

import com.example.mymovies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_FILM_ID = "filmId";
    private static final String KEY_FILMS = "films";
    private static final String KEY_NAME_RU = "nameRu";
    private static final String KEY_NAME_EN = "nameEn";
    private static final String KEY_YEAR = "year";
    private static final String KEY_FILM_lENGHTH = "filmLength";
    private static final String KEY_POSTER_URL = "posterUrl";
    private static final String KEY_POSTER_URL_PREVIEW = "posterUrlPreview";
    private static final String KEY_RATING = "rating";
    private static final String KEY_RATING_VOTE_COUNT = "ratingVoteCount";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FACTS = "facts";
    private static final String KEY_PREMIERE_WORLD = "premiereWorld";


    private static final String KEY_FILM_DETAIL_DATA = "data";
    private static final String KEY_FILM_DETAIL_RATING = "rating";

    public static ArrayList<Movie> getTOPFilmsFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArrayTOPFilms = jsonObject.getJSONArray(KEY_FILMS);
            for (int i = 0; i < jsonArrayTOPFilms.length(); i++) {
                JSONObject objectFilmForTop = jsonArrayTOPFilms.getJSONObject(i);
                int filmId = objectFilmForTop.getInt(KEY_FILM_ID);
                String nameRu = objectFilmForTop.getString(KEY_NAME_EN);
//                String rating = objectFilmForTop.getString(KEY_RATING);
                String posterUrlPreview = objectFilmForTop.getString(KEY_POSTER_URL_PREVIEW);
                Movie movieForTop = new Movie(filmId, nameRu, posterUrlPreview);
                result.add(movieForTop);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Movie> getFilmDetailsFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONObject objectFilmDetailsData = jsonObject.getJSONObject(KEY_FILM_DETAIL_DATA);
            JSONArray jsonArrayFacts = objectFilmDetailsData.getJSONArray(KEY_FACTS);
            StringBuilder builderFacts = new StringBuilder();
            for (int i = 0; i < jsonArrayFacts.length(); i++) {
                String filmFact = jsonArrayFacts.getString(i);
                builderFacts.append(filmFact).append("\n");
            }
            JSONObject objectFilmDetailsRating = jsonObject.getJSONObject(KEY_RATING);
            int filmId = objectFilmDetailsData.getInt(KEY_FILM_ID);
            String nameRu = objectFilmDetailsData.getString(KEY_NAME_RU);
            String nameEn = objectFilmDetailsData.getString(KEY_NAME_EN);
            String posterUrl = objectFilmDetailsData.getString(KEY_POSTER_URL);
            int year = Integer.parseInt(objectFilmDetailsData.getString(KEY_YEAR));
            String filmLength = objectFilmDetailsData.getString(KEY_FILM_lENGHTH);
            String description = objectFilmDetailsData.getString(KEY_DESCRIPTION);
            String facts = builderFacts.toString();
            String premiereWorld = objectFilmDetailsData.getString(KEY_PREMIERE_WORLD);
            double rating = objectFilmDetailsRating.getInt(KEY_FILM_DETAIL_RATING);
            int ratingVoteCount = objectFilmDetailsRating.getInt(KEY_RATING_VOTE_COUNT);
            Movie movie = new Movie(filmId, nameRu, nameEn, year, filmLength, posterUrl,
                    rating, ratingVoteCount, description, facts, premiereWorld);
            result.add(movie);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}