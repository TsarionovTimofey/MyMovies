package com.example.mymovies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MoviePreview;

import java.util.List;

@Dao
public interface MovieDao {
    //  For Top Rated
    @Query("SELECT * FROM moviesForTop")
    LiveData<List<MoviePreview>> getAllMovies();

    @Query("SELECT * FROM moviesForTop WHERE filmId == :movieId ")
    MoviePreview getMovieByid(int movieId);

    @Query("DELETE FROM moviesForTop")
    void deleteAllMovies();

    @Insert
    void insertMovie(MoviePreview moviePreview);

    @Delete
    void deleteMovie(MoviePreview moviePreview);

    //  For FavouriteMovies
    @Query("SELECT * FROM favourite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Insert
    void insertFavouriteMovie(FavouriteMovie favouriteMovie);

    @Delete
    void deleteFavouriteMovie(FavouriteMovie favouriteMovie);

    @Query("SELECT * FROM favourite_movies WHERE filmId == :filmId ")
    FavouriteMovie getFavoriteMovieByid(int filmId);


}
