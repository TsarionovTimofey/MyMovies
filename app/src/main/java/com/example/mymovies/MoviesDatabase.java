package com.example.mymovies;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MovieDetailed;
import com.example.mymovies.data.MoviePreview;


@Database(entities = {MoviePreview.class, MovieDetailed.class, FavouriteMovie.class}, version = 4, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static MoviesDatabase database;
    private static final String DB_NAME_MOVIES = "movies.db";
    private static final Object LOCK = new Object();

    public static MoviesDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MoviesDatabase.class, DB_NAME_MOVIES).fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract MovieDao movieDao();
}
