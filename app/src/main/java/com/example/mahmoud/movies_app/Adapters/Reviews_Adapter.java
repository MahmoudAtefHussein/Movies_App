package com.example.mahmoud.movies_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;

import java.util.ArrayList;


public class Reviews_Adapter extends ArrayAdapter<Movie> {

    private Context context;
    ArrayList<Movie> movie_Reviews=new ArrayList<Movie>();

    public Reviews_Adapter(Context con, ArrayList<Movie> movieReviews) {
        super(con,0, movieReviews);
        this.context=con;
        this.movie_Reviews=movieReviews;
    }
    @Override
    public int getCount()
    {
        return movie_Reviews.size();
    }

    @Override
    public Movie getItem(int position)
    {
        return movie_Reviews.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.reviews_adapter, null);
        }

        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView content= (TextView) convertView.findViewById(R.id.content);
        author.setText(movie_Reviews.get(position).getAuthor());
        content.setText(movie_Reviews.get(position).getContent());
        return convertView;
    }
}
