package com.example.mahmoud.movies_app.Interfaces;

import android.os.Bundle;

import com.example.mahmoud.movies_app.Movie;

import java.util.ArrayList;


public interface Data_Received
{
    void  movies_data(ArrayList<Movie> movie_info);
    void  movies_reviews(ArrayList<Movie> movie_reviews);
    void  movies_trailers(ArrayList<Movie> movie_trailers);
    void  movies (Bundle movies);

}