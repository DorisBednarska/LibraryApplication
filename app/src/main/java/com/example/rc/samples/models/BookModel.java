package com.example.rc.samples.models;

import java.io.Serializable;

public class BookModel implements Serializable {

    private Long id;

    private String title;

    private String author;

    private String url;

    private String isbn;

    public BookModel(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getIsbn() {
        return isbn;
    }
}
