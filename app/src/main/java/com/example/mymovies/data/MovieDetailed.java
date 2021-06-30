package com.example.mymovies.data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moviesDetailed")
public class MovieDetailed {
    @PrimaryKey
    private int filmId;
    private String nameRu;
    private String nameEn;
    private int year;
    private String filmLength;
    private String posterUrl;
    private String posterUrlPreview;
    private double rating;
    private int ratingVoteCount;

    private String description;
    private String facts;
    private String premiereWorld;

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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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

    public int getRatingVoteCount() {
        return ratingVoteCount;
    }

    public void setRatingVoteCount(int ratingVoteCount) {
        this.ratingVoteCount = ratingVoteCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getPremiereWorld() {
        return premiereWorld;
    }

    public void setPremiereWorld(String premiereWorld) {
        this.premiereWorld = premiereWorld;
    }

    public MovieDetailed(int filmId, String nameRu, String nameEn, int year, String filmLength, String posterUrl, String posterUrlPreview, double rating, int ratingVoteCount, String description, String facts, String premiereWorld) {
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.year = year;
        this.filmLength = filmLength;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
        this.rating = rating;
        this.ratingVoteCount = ratingVoteCount;
        this.description = description;
        this.facts = facts;
        this.premiereWorld = premiereWorld;
    }
}
