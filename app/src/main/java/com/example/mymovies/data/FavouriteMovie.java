package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends MovieDetailed {
    public FavouriteMovie(int filmId, String nameRu, String nameEn, int year, String filmLength, String posterUrl, String posterUrlPreview, double rating, int ratingVoteCount, String description, String facts, String premiereWorld) {
        super(filmId, nameRu, nameEn, year, filmLength, posterUrl, posterUrlPreview, rating, ratingVoteCount, description, facts, premiereWorld);
    }

    @Ignore
    public FavouriteMovie(MovieDetailed movie) {
        super(movie.getFilmId(), movie.getNameRu(), movie.getNameEn(), movie.getYear(),
                movie.getFilmLength(), movie.getPosterUrl(), movie.getPosterUrlPreview(), movie.getRating(), movie.getRatingVoteCount(), movie.getDescription(), movie.getFacts(), movie.getPremiereWorld());
    }
}
