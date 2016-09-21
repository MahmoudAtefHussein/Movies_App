package com.example.mahmoud.movies_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mahmoud.movies_app.Movie;
import com.example.mahmoud.movies_app.R;

import java.util.ArrayList;


public class Trailers_Adapter extends ArrayAdapter<Movie> {

    private Context context;
    ArrayList<Movie> movie_Trailers=new ArrayList<Movie>();


    public Trailers_Adapter(Context con, ArrayList<Movie>movieTrailers)
    {
        super(con,0,movieTrailers);
        this.context=con;
        this.movie_Trailers=movieTrailers;
    }

    @Override
    public int getCount()
    {
       return movie_Trailers.size();
    }

    @Override
    public Movie getItem(int position)
    {
        return movie_Trailers.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.trailers_adapter, null);
        }

        TextView trailerName = (TextView) convertView.findViewById(R.id.trailer_name);
        trailerName.setText(movie_Trailers.get(position).getTrailer_name());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + movie_Trailers.get(position).getTrailer_key()));
                context.startActivity(intent);
            }

        });

        return convertView;
    }
}
