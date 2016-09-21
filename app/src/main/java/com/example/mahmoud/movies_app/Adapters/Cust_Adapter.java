package com.example.mahmoud.movies_app.Adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;
import com.squareup.picasso.Picasso;


public class Cust_Adapter extends ArrayAdapter<Movie> {

    private Context context;
    ArrayList<Movie> movieData=new ArrayList<Movie>();



    public Cust_Adapter(Context con, ArrayList<Movie> moviePosters) {
        super(con,0,moviePosters);
        this.context=con;
        this.movieData = moviePosters;
    }



    @Override
    public int getCount() {
        return movieData.size();
    }
    @Override
    public Movie getItem(int poster_position) {
        return movieData.get(poster_position);
    }
    @Override
    public long getItemId(int poster_position) {
        return poster_position;
    }
    @Override
    public View getView(final int poster_position, View item_view, ViewGroup viewGroup)
    {
        if (item_view == null)
        {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            item_view = inflater.inflate(R.layout.cust_adapter, null);

        }


        ImageView image =(ImageView) item_view.findViewById(R.id.movie_poster);
        String pic_path="http://image.tmdb.org/t/p/w185/" + movieData.get(poster_position).getPoster_path();
        Picasso.with(context).load(pic_path).into(image);
        TextView movieName = (TextView) item_view.findViewById(R.id.movie_name);
        movieName.setText(movieData.get(poster_position).getTitle());

        return item_view;
    }



}

