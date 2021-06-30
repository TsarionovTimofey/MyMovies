package com.example.mymovies;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MoviePreview;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static MoviesDatabase database;

    private LiveData<List<MoviePreview>> moviesTopRated;
    private LiveData<List<FavouriteMovie>> favouriteMovies;


    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MoviesDatabase.getInstance(getApplication());
        moviesTopRated = database.movieDao().getAllMovies();
        favouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    //For Preview
    public MoviePreview getMovieByID(int filmId) {
        try {
            return new GetMovieTask().execute(filmId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<MoviePreview>> getMoviesTopRated() {
        return moviesTopRated;
    }

    public void deleteAllMovies() {
        new DeleteMoviesTask().execute();
    }

    public void deleteMovies() {
        new DeleteMoviesTask().execute();
    }

    public void deleteMovie(MoviePreview moviePreview) {
        new DeleteMovieTask().execute(moviePreview);
    }

    public void insertMovie(MoviePreview moviePreview) {
        new InsertMovieTask().execute(moviePreview);
    }

    private static class DeleteMovieTask extends AsyncTask<MoviePreview, Void, Void> {
        @Override
        protected Void doInBackground(MoviePreview... moviePreviews) {
            if (moviePreviews != null && moviePreviews.length > 0) {
                database.movieDao().deleteMovie(moviePreviews[0]);
            }
            return null;
        }
    }

    private static class InsertMovieTask extends AsyncTask<MoviePreview, Void, Void> {
        @Override
        protected Void doInBackground(MoviePreview... moviePreviews) {
            if (moviePreviews != null && moviePreviews.length > 0) {
                database.movieDao().insertMovie(moviePreviews[0]);
            }
            return null;
        }
    }

    private static class DeleteMoviesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... integers) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class GetMovieTask extends AsyncTask<Integer, Void, MoviePreview> {

        @Override
        protected MoviePreview doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieByid(integers[0]);
            }
            return null;
        }
    }

    //For Favourites
    public FavouriteMovie getFavouriteMovieByID(int filmId) {
        try {
            return new GetFavouriteMovieTask().execute(filmId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void deleteFavouriteMovie(FavouriteMovie movie) {
        new DeleteFavouriteMovieTask().execute(movie);
    }

    public void insertFavouriteMovie(FavouriteMovie movie) {
        new InsertFavouriteMovieTask().execute(movie);
    }


    private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie> {

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getFavoriteMovieByid(integers[0]);
            }
            return null;
        }
    }

}
