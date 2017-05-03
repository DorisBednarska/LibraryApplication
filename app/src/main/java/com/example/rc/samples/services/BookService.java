package com.example.rc.samples.services;

import com.example.rc.samples.models.BookModel;
import com.example.rc.samples.models.ResponseBookModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookService {

    String BOOKS = "/books";
    String FREEBOOKS = BOOKS + "/search/findFreeBooks";

    @GET("/users/{id}/books")
    Call<ResponseBookModel> getMyBooks(@Path("id") Long id, @Header("Authorization") String token);

    @GET(FREEBOOKS)
    Call<ResponseBookModel> getBooks();

    @POST(BOOKS)
    Call<Void> createBook(@Body BookModel bookModel);

}
