package com.example.mymovies.data;

public class Review {
    private String author;
    private String description;

    public Review(String author, String description) {
        this.author = author;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}