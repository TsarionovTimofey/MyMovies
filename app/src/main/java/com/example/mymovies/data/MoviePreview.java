package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviesForTop")
public class MoviePreview {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int filmId;
    private String nameRu;
    private String posterUrlPreview;
    private double rating;

    public MoviePreview(int uniqueId, int filmId, String nameRu, String posterUrlPreview, double rating) {
        this.uniqueId = uniqueId;
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.posterUrlPreview = posterUrlPreview;
        this.rating = rating;
    }

    @Ignore
    public MoviePreview(int filmId, String nameRu, String posterUrlPreview, double rating) {
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.posterUrlPreview = posterUrlPreview;
        this.rating = rating;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
