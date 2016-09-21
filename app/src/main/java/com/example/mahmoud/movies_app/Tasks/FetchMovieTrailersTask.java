package com.example.mahmoud.movies_app.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mahmoud.movies_app.BuildConfig;
import com.example.mahmoud.movies_app.Interfaces.Data_Received;
import com.example.mahmoud.movies_app.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTrailersTask extends AsyncTask<String, Void,ArrayList<Movie>> {


    Data_Received movieTrailers;


    private final String LOG_TAG = FetchMovieTrailersTask.class.getSimpleName();



    public void setMovieDataTrailers(Data_Received data)
    {
        this.movieTrailers=data;

    }


    @Override
    protected ArrayList<Movie> doInBackground(String... params) {



        HttpURLConnection urlCon = null;
        BufferedReader reader = null;
        String movieJsonDataTrailerStr=null;


        try
        {

            String baseUrl = "http://api.themoviedb.org/3/movie/"+ params[0]+"/videos?";
            String apiKey = "api_key=" + BuildConfig.OPEN_MOVIE_MAP_API_KEY;
            URL url = new URL(baseUrl.concat(apiKey));


            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("GET");
            urlCon.connect();

            InputStream input = urlCon.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (input == null)
            {
                return null;
            }


            reader = new BufferedReader(new InputStreamReader(input));

            String line;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
            {
                return null;
            }
            movieJsonDataTrailerStr = buffer.toString();
            Log.v(LOG_TAG,"Movies String: " + movieJsonDataTrailerStr);

        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "HTTP Call Failed"+ e);
            return null;

        }
        finally
        {
            if (urlCon != null)
            {
                urlCon.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    Log.e(LOG_TAG, "Failed to close buffered reader"+ e);
                }

            }
        }

        try
        {
            return getMovieJsonTrailers(movieJsonDataTrailerStr);
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, e.getMessage()+ e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies)
    {
        super.onPostExecute(movies);
        if (movies !=null)
        {
            movieTrailers.movies_trailers(movies);
        }
    }


    private ArrayList<Movie> getMovieJsonTrailers(String movieJsonTrailersStr)
            throws JSONException {

        final String results = "results";
        final String trailer_name = "name";
        final String trailer_key = "key";


        JSONObject movieObj = new JSONObject(movieJsonTrailersStr);
        JSONArray movieArr = movieObj.getJSONArray(results);

        ArrayList<Movie>myList=new ArrayList<Movie>();

        for (int i = 0; i < movieArr.length(); i++)
        {
            JSONObject movieJsonObject = movieArr.getJSONObject(i);

            String movie_trailer_name     = movieJsonObject.getString(trailer_name);
            String movie_trailer_key      = movieJsonObject.getString(trailer_key);

            Movie movie = new Movie();
            movie.setTrailer_name(movie_trailer_name);
            movie.setTrailer_key(movie_trailer_key);
            myList.add(movie);
        }

        return myList;
    }
}
