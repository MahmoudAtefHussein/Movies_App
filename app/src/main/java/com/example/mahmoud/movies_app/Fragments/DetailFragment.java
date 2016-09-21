package com.example.mahmoud.movies_app.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.movies_app.Adapters.Reviews_Adapter;
import com.example.mahmoud.movies_app.Adapters.Trailers_Adapter;
import com.example.mahmoud.movies_app.Favorite_Database;
import com.example.mahmoud.movies_app.Interfaces.Data_Received;
import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;
import com.example.mahmoud.movies_app.Tasks.FetchMovieReviewsTask;
import com.example.mahmoud.movies_app.Tasks.FetchMovieTrailersTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailFragment extends Fragment implements Data_Received{


    ImageView poster,back_poster;
    TextView title,overview,release_date,vote_average;
    Button favorites_button;
    Bundle bundle;
    Movie my_movie;
    Favorite_Database favorite_movie;
    ListView trailers,reviews;
    String id,movie_title,movie_back_path,movie_poster_path,movie_overview,movie_release_date,movie_vote_average;

    public DetailFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        id = String.valueOf(bundle.getLong("id"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_main for this fragment
        View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        favorite_movie = new Favorite_Database(getActivity());

        title            = (TextView)  detailView.findViewById(R.id.original_title);
        back_poster      = (ImageView) detailView.findViewById(R.id.img_backdrop);
        poster           = (ImageView) detailView.findViewById(R.id.movie_poster2);
        overview         = (TextView)  detailView.findViewById(R.id.overview);
        release_date     = (TextView)  detailView.findViewById(R.id.release_date);
        vote_average     = (TextView)  detailView.findViewById(R.id.vote_average);
        favorites_button = (Button)    detailView.findViewById(R.id.favorite_button);
        trailers         = (ListView)  detailView.findViewById(R.id.trailers);
        reviews          = (ListView)  detailView.findViewById(R.id.reviews);




        if(bundle!=null)
        {
             movie_title        = bundle.getString("title");
             movie_back_path    = bundle.getString("backdrop_path");
             movie_poster_path  = bundle.getString("poster_path");
             movie_overview     = bundle.getString("overview");
             movie_release_date = bundle.getString("release_date");
             movie_vote_average = bundle.getString("vote_average");

            title.setText(movie_title);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" +movie_back_path )
                    .into(back_poster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" +movie_poster_path )
                    .into(poster);
            overview.setText(movie_overview);
            release_date.setText(movie_release_date);
            vote_average.setText( movie_vote_average + " / 10 ");
        }

        my_movie = new Movie(movie_title,movie_poster_path,movie_back_path,movie_overview,movie_release_date,movie_vote_average,Long.parseLong(id));

        FetchMovieTrailersTask movieTrailers = new FetchMovieTrailersTask();
        movieTrailers.setMovieDataTrailers(this);
        movieTrailers.execute(id);

        FetchMovieReviewsTask movieReviews =new FetchMovieReviewsTask();
        movieReviews.setMovieDataReviews(this);
        movieReviews.execute(id);


        favorites_button.setBackgroundColor(Color.BLUE);
        favorites_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                favorites_button.setBackgroundColor(Color.rgb(255, 215, 0));
                favorites_button.setTextColor(Color.BLACK);

                    if (favorite_movie.check_movie_exist(bundle.getString("original_title")))
                    {
                        Toast.makeText(getActivity()," Movie Is Already In Favorites. ", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        favorite_movie.insert_movie(my_movie);
                        Toast.makeText(getActivity()," Movie Is Added To Favorites. ", Toast.LENGTH_LONG).show();
                    }
            }

        });


        return detailView;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void movies_data(ArrayList<Movie> movie_info)
    {

    }

    @Override
    public void movies_reviews(ArrayList<Movie> movie_reviews)
    {
        reviews.setAdapter(new Reviews_Adapter(getActivity().getApplicationContext(), movie_reviews));
        setListViewHeightBasedOnChildren(reviews);
    }

    @Override
    public void movies_trailers(ArrayList<Movie> movie_trailers)
    {
        trailers.setAdapter(new Trailers_Adapter(getActivity(), movie_trailers));
        setListViewHeightBasedOnChildren(trailers);
    }

    @Override
    public void movies(Bundle movies) {

    }




}
