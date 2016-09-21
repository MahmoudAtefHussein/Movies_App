package com.example.mahmoud.movies_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.mahmoud.movies_app.Fragments.DetailFragment;
import com.example.mahmoud.movies_app.Fragments.MainFragment;
import com.example.mahmoud.movies_app.Interfaces.Data_Received;
import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Data_Received{

    boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState==null)
        {
            MainFragment mainFragment = new MainFragment();
            mainFragment.setMovieData(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_main_fragment, mainFragment)
                    .commit();
        }


        FrameLayout my_frame = (FrameLayout) findViewById(R.id.movie_details_container);
        if (null==my_frame)
        {
            mTwoPane = false;
        }
        else
        {
            mTwoPane = true;
        }




    }


    @Override
    public void movies(Bundle movies)
    {
        if (mTwoPane)
        {
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(movies);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container,detailFragment)
                    .commit();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtras(movies);
            startActivity(intent);
        }
    }


    @Override
    public void movies_data(ArrayList<Movie> movie_info) {

    }

    @Override
    public void movies_reviews(ArrayList<Movie> movie_reviews) {

    }

    @Override
    public void movies_trailers(ArrayList<Movie> movie_trailers) {

    }




}
