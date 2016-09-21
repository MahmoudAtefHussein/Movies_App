package com.example.mahmoud.movies_app.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mahmoud.movies_app.Adapters.Cust_Adapter;
import com.example.mahmoud.movies_app.Favorite_Database;
import com.example.mahmoud.movies_app.Interfaces.Data_Received;
import com.example.mahmoud.movies_app.Tasks.FetchMovieDataTask;
import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;

import java.util.ArrayList;


public class MainFragment extends Fragment implements Data_Received {

    GridView grid;
    SharedPreferences shared_preferences;
    SharedPreferences.Editor editor;
    ArrayList<Movie>favorite_movies;
    Favorite_Database favorite;
    Data_Received my_listener;
    Bundle bundle = new Bundle();



    public MainFragment()
    {
        favorite = new Favorite_Database(getActivity());
    }




    @Override
    public void onResume() {
        super.onResume();
        update_movies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.main_fragment,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.popular)
        {
            editor.putInt("choose", 1);
            FetchMovieDataTask fetchPopularMovie = new FetchMovieDataTask();
            fetchPopularMovie.setPopularMovieData(this);
            fetchPopularMovie.execute("popular");
        }
        else if (id == R.id.most_rated)
        {
            editor.putInt("choose", 2);
            FetchMovieDataTask fetchPopularMovie = new FetchMovieDataTask();
            fetchPopularMovie.setPopularMovieData(this);
            fetchPopularMovie.execute("top_rated");
        }
        else
        {
            editor.putInt("choose", 3);
            favorite_movies = favorite.fetch_movie();
            grid.setAdapter(new Cust_Adapter(getActivity(), favorite_movies));
        }

        editor.apply();
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



            grid = (GridView) rootView.findViewById(R.id.gridView);
            favorite = new Favorite_Database(getActivity());
            shared_preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            editor = shared_preferences.edit();

        if (!CheckInternet(getContext().getApplicationContext()))
        {
            Toast.makeText(getContext().getApplicationContext(),"Sorry,There is no internet connection. " , Toast.LENGTH_SHORT).show();
        }

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Movie mov = (Movie)adapterView.getItemAtPosition(i);

                    bundle.putLong  ("id",mov.getId());
                    bundle.putString("original_title", mov.getOriginal_title());
                    bundle.putString("title",mov.getTitle());
                    bundle.putString("backdrop_path",mov.getBackdrop_path());
                    bundle.putString("poster_path", mov.getPoster_path());
                    bundle.putString("release_date", mov.getRelease_date());
                    bundle.putString("overview",mov.getOverview());
                    bundle.putString("vote_average", mov.getVote_average());

                    my_listener.movies(bundle);


                }
            });


        return rootView;
    }


    public void update_movies()
    {
        int choose = shared_preferences.getInt("choose", 1);

        if (choose == 1)
        {
            FetchMovieDataTask fetchPopularMovie = new FetchMovieDataTask();
            fetchPopularMovie.setPopularMovieData(this);
            fetchPopularMovie.execute("popular");

        }
        else if (choose == 2)
        {
            FetchMovieDataTask fetchPopularMovie = new FetchMovieDataTask();
            fetchPopularMovie.setPopularMovieData(this);
            fetchPopularMovie.execute("top_rated");
        }
        else
        {
            favorite_movies = favorite.fetch_movie();
            grid.setAdapter(new Cust_Adapter(getActivity(), favorite_movies));
        }
    }


    public static boolean CheckInternet(Context context)
    {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }


    public void setMovieData(Data_Received data)
    {
        this.my_listener=data;
    }


    @Override
    public void movies_data (ArrayList<Movie>result)
    {

        grid.setAdapter(new Cust_Adapter(getActivity().getApplicationContext(),result));

    }



    @Override
    public void movies_reviews(ArrayList<Movie> movie_reviews) {

    }

    @Override
    public void movies_trailers(ArrayList<Movie> movie_trailers) {

    }

    @Override
    public void movies(Bundle movies) {


    }


}