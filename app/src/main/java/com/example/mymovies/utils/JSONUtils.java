package com.example.mymovies.utils;

import com.example.mymovies.data.MovieDetailed;
import com.example.mymovies.data.MoviePreview;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;

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
    private static final String KEY_FILM_lENGTH = "filmLength";
    private static final String KEY_POSTER_URL = "posterUrl";
    private static final String KEY_POSTER_URL_PREVIEW = "posterUrlPreview";
    private static final String KEY_RATING = "rating";
    private static final String KEY_RATING_VOTE_COUNT = "ratingVoteCount";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_FACTS = "facts";
    private static final String KEY_PREMIERE_WORLD = "premiereWorld";

    private static final String KEY_FILM_DETAIL_DATA = "data";
    private static final String KEY_FILM_DETAIL_RATING = "rating";

    private static final String KEY_REVIEWS = "reviews";
    private static final String KEY_REVIEW_AUTHOR = "reviewAutor";
    private static final String KEY_REVIEW_DESCRIPTION = "reviewDescription";

    private static final String KEY_TRAILERS = "trailers";
    private static final String KEY_URL = "url";
    private static final String KEY_NAME = "name";

    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonObject) {
        ArrayList<Review> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_REVIEWS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonReview = jsonArray.getJSONObject(i);
                String author = jsonReview.getString(KEY_REVIEW_AUTHOR);
                String description = jsonReview.getString(KEY_REVIEW_DESCRIPTION);
                Review review = new Review(author, description);
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Trailer> getTrailersFromJSON(JSONObject jsonObject) {
        ArrayList<Trailer> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_TRAILERS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonTrailer = jsonArray.getJSONObject(i);
                String url = jsonTrailer.getString(KEY_URL);
                String name = jsonTrailer.getString(KEY_NAME);
                Trailer trailer = new Trailer(url, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<MoviePreview> getTOPFilmsFromJSON(JSONObject jsonObject) {
        ArrayList<MoviePreview> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_FILMS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectFilmForTop = jsonArray.getJSONObject(i);
                int filmId = objectFilmForTop.getInt(KEY_FILM_ID);
                String nameRu = objectFilmForTop.getString(KEY_NAME_EN);
                String ratingString = objectFilmForTop.getString(KEY_RATING);
                double rating = ratingStringToRating(ratingString);
                String posterUrlPreview = objectFilmForTop.getString(KEY_POSTER_URL_PREVIEW);
                MoviePreview moviePreview = new MoviePreview(filmId, nameRu, posterUrlPreview, rating);
                result.add(moviePreview);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static double ratingStringToRating(String ratingString) {
        double rating;
        try {
            rating = Double.parseDouble(ratingString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            rating = (Double
                    .parseDouble(ratingString
                            .replaceAll("%", ""))) / 10;
        }
        return rating;
    }

    public static MovieDetailed getFilmDetailsFromJSON(JSONObject jsonObject) {
        MovieDetailed movieDetailed = new MovieDetailed(-1, "-", "-", 0, "-", "-", "-", 0, 0, "-", "aaa", "-");
        if (jsonObject == null) {
            return null;
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
            String filmLength = objectFilmDetailsData.getString(KEY_FILM_lENGTH);
            String description = objectFilmDetailsData.getString(KEY_DESCRIPTION);
            String facts = builderFacts.toString();
            String premiereWorld = objectFilmDetailsData.getString(KEY_PREMIERE_WORLD);
            double rating = objectFilmDetailsRating.getDouble(KEY_FILM_DETAIL_RATING);
            int ratingVoteCount = objectFilmDetailsRating.getInt(KEY_RATING_VOTE_COUNT);
            movieDetailed.setFilmId(filmId);
            movieDetailed.setNameRu(nameRu);
            movieDetailed.setNameEn(nameEn);
            movieDetailed.setPosterUrl(posterUrl);
            movieDetailed.setRating(rating);
            movieDetailed.setRatingVoteCount(ratingVoteCount);
            movieDetailed.setFilmLength(filmLength);
            movieDetailed.setDescription(description);
            movieDetailed.setFacts(facts);
            movieDetailed.setPremiereWorld(premiereWorld);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieDetailed;
    }
}