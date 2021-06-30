package com.example.mymovies.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    private static final String API_KEY = "43f6a6b8-ba52-4f25-9ece-9a4663385aad";
    private static final String BASE_URL_FOR_TOP =
            "https://kinopoiskapiunofficial.tech/api/v2.2/films/top";
    private static final String TYPE_OF_TOP_100_POPULAR = "TOP_100_POPULAR_FILMS";
    private static final String TYPE_OF_TOP_250_BEST_FILMS = "TOP_250_BEST_FILMS";
    private static final String TYPE_OF_TOP_AWAIT_FILMS = "TOP_AWAIT_FILMS";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_TYPE_OF_TOP = "type";

    public static final int INT_TOP_100_POPULAR = 0;
    public static final int INT_TOP_250_BEST_FILMS = 1;
    public static final int INT_TOP_AWAIT_FILMS = 2;

    private static final String BASE_URL_FOR_FILM_DETAILS =
            "https://kinopoiskapiunofficial.tech/api/v2.1/films";

    private static final String BASE_URL_REVIEWS =
            "https://kinopoiskapiunofficial.tech/api/v1/reviews";

    private static URL buildURLTypeOfTop(int intTypeOfTop, int page) {
        URL result = null;
        String typeOfTop;
        switch (intTypeOfTop) {
            // 0
            case INT_TOP_100_POPULAR:
                typeOfTop = TYPE_OF_TOP_100_POPULAR;
                break;
            // 1
            case INT_TOP_250_BEST_FILMS:
                typeOfTop = TYPE_OF_TOP_250_BEST_FILMS;
                break;
            // 2
            default:
                typeOfTop = TYPE_OF_TOP_AWAIT_FILMS;
        }
        Uri uri = Uri.parse(BASE_URL_FOR_TOP).buildUpon()
                .appendQueryParameter(PARAMS_TYPE_OF_TOP, typeOfTop)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLDetail(int filmId) {
        URL result = null;
        Uri uri = Uri.parse(BASE_URL_FOR_FILM_DETAILS).buildUpon()
                .appendPath(Integer.toString(filmId))
                .appendQueryParameter("append_to_response", "RATING")
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLVideos(int filmId) {
        URL result = null;
        Uri uri = Uri.parse(BASE_URL_FOR_FILM_DETAILS).buildUpon()
                .appendPath(Integer.toString(filmId))
                .appendPath("videos")
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildURLReviews(int filmId) {
        URL result = null;
        Uri uri = Uri.parse(BASE_URL_REVIEWS).buildUpon()
                .appendQueryParameter("filmId", Integer.toString(filmId))
                .appendQueryParameter("page", Integer.toString(1))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetworkTopDownload(int chooserTypeOfTop, int page) {
        JSONObject result = null;
        URL url = buildURLTypeOfTop(chooserTypeOfTop, page);
        try {
            result = new JSONLoadTaskDownload().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetworkFilmDetailDownload(int filmId) {
        JSONObject result = null;
        URL url = buildURLDetail(filmId);
        try {
            result = new JSONLoadTaskDownload().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetworkFilmVideosDownload(int filmId) {
        JSONObject result = null;
        URL url = buildURLVideos(filmId);
        try {
            result = new JSONLoadTaskDownload().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONFromNetworkReviewsDownload(int filmId) {
        JSONObject result = null;
        URL url = buildURLReviews(filmId);
        try {
            result = new JSONLoadTaskDownload().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static class JSONLoader extends AsyncTaskLoader<JSONObject> {

        private Bundle bundle;

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null) {
                return null;
            }
            String urlString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result = null;
            if (url == null) {
                return null;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("accept", "application/json");
                httpURLConnection.setRequestProperty("X-API-KEY", API_KEY);
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }

    private static class JSONLoadTaskDownload extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                httpURLConnection.setRequestProperty("accept", "application/json");
                httpURLConnection.setRequestProperty("X-API-KEY", API_KEY);
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }

}
