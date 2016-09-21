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



public class FetchMovieDataTask extends AsyncTask<String, Void,ArrayList<Movie>>{

        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();

        Data_Received movieData;

        public void setPopularMovieData(Data_Received data)
        {
            this.movieData=data;
        }


        private ArrayList<Movie> getMovieJsonData(String movieJsonStr)
                throws JSONException {

            final String results = "results";
            final String id = "id";
            final String title = "title";
            final String poster_path ="poster_path";
            final String backdrop_path="backdrop_path";
            final String original_title = "original_title";
            final String overview = "overview";
            final String release_date ="release_date";
            final String vote_average = "vote_average";


            JSONObject movieObj = new JSONObject(movieJsonStr);
            JSONArray movieArr = movieObj.getJSONArray(results);

            ArrayList<Movie>myList=new ArrayList<Movie>();

            for (int i = 0; i < movieArr.length(); i++)
            {

                JSONObject movieJsonObject = movieArr.getJSONObject(i);

                long movie_id               = movieJsonObject.getLong(id);
                String movie_title          = movieJsonObject.getString(title);
                String movie_poster_path    = movieJsonObject.getString(poster_path);
                String movie_backdrop_path  = movieJsonObject.getString(backdrop_path);
                String movie_original_title = movieJsonObject.getString(original_title);
                String movie_overview       = movieJsonObject.getString(overview);
                String movie_release_date   = movieJsonObject.getString(release_date);
                String movie_vote_average   = movieJsonObject.getString(vote_average);

                Movie movie = new Movie(movie_id,movie_title,movie_poster_path,movie_backdrop_path,movie_original_title,movie_overview,movie_release_date,movie_vote_average);
                myList.add(movie);
            }


            return myList;
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            HttpURLConnection urlCon = null;
            BufferedReader reader = null;
            String movieJsonStr=null;


            try
            {

                String baseUrl = "http://api.themoviedb.org/3/movie/" +params[0]+"?";
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
                movieJsonStr = buffer.toString();
                Log.v(LOG_TAG,"Movies String: " + movieJsonStr);

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
                return getMovieJsonData(movieJsonStr);
            }
            catch (JSONException e)
            {
                Log.e(LOG_TAG, e.getMessage()+ e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            super.onPostExecute(result);

            if(result !=null)
            {
                movieData.movies_data(result);
            }
        }



    }
