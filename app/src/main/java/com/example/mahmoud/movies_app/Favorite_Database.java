package com.example.mahmoud.movies_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Favorite_Database extends SQLiteOpenHelper {


    private static String database_name ="movie_database";
    SQLiteDatabase movie_database;

    public Favorite_Database(Context context)
    {
        super(context, database_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE movie" +
                "(_id integer primary key autoincrement,title text ,poster text ,backdrop_poster text ," +
                "overview text ,release_date text ,vote_average text ,movie_id long )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version)
    {
        sqLiteDatabase.execSQL("drop table if exists movie");
        onCreate(sqLiteDatabase);
    }


    public void insert_movie(Movie movie)
    {
        movie_database = getWritableDatabase();
        ContentValues row_movies = new ContentValues();
        row_movies.put("title", movie.getOriginal_title());
        row_movies.put("poster", movie.getPoster_path());
        row_movies.put("backdrop_poster", movie.getBackdrop_path());
        row_movies.put("overview", movie.getOverview());
        row_movies.put("release_date", movie.getRelease_date());
        row_movies.put("vote_average", movie.getVote_average());
        row_movies.put("movie_id", movie.getId());
        movie_database.insert("movie",null,row_movies);
        movie_database.close();

    }




    public ArrayList<Movie> fetch_movie()
    {
        ArrayList<Movie> myList = new ArrayList<>();
        movie_database = getReadableDatabase();
        String [] myTable = {"title","poster","backdrop_poster","overview","release_date","vote_average","movie_id"};
        Cursor cursor = movie_database.query("movie",myTable,null,null,null,null,null);

        if (cursor.moveToFirst())
        {
            do
            {
                Movie movie = new Movie(cursor.getString(0),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getLong(6));

                myList.add(movie);

            } while (cursor.moveToNext());
        }
        movie_database.close();
        return myList;
    }

    public boolean check_movie_exist (String title)
    {
        movie_database=getReadableDatabase();
        String retrieved_title;
        String [] titles = {"title"};
        Cursor cursor=movie_database.query("movie",titles,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                retrieved_title=cursor.getString(0);
                if (retrieved_title.equals(title)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.moveToFirst();
        return false;
    }

}
