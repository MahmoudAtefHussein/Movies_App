package com.example.mahmoud.movies_app;


public class Movie {

    private   long id;
    private  String title;
    private  String poster_path;
    private  String backdrop_path;
    private  String original_title;
    private  String overview ;
    private  String release_date ;
    private  String vote_average;
    private  String trailer_name;
    private  String trailer_key;
    private  String author;
    private  String content;


    public Movie(String title, String poster, String back_poster, String overview, String release_date, String vote_average,long movie_id)
    {
        this.poster_path = poster;
        this.backdrop_path=back_poster;
        this.original_title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.id=movie_id;
    }

    public Movie(String movie_author, String movie_content)
    {
        this.author=movie_author;
        this.content=movie_content;
    }

    public Movie(long movie_id, String movie_title, String movie_poster_path,String movie_backdrop_path, String movie_original_title, String movie_overview, String movie_release_date, String movie_vote_average)
    {

        this.id = movie_id;
        this.title = movie_title;
        this.poster_path = movie_poster_path;
        this.backdrop_path=movie_backdrop_path;
        this.original_title = movie_original_title;
        this.overview = movie_overview;
        this.release_date = movie_release_date;
        this.vote_average = movie_vote_average;
    }

    public Movie()
    {

    }


    public void setId(long id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setBackdrop_path(String backdrop_path)
    {
        this.backdrop_path = backdrop_path;
    }

    public void setPoster_path(String poster_path)
    {
        this.poster_path = poster_path;
    }

    public void setOriginal_title(String original_title)
    {
        this.original_title=original_title;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public void setVote_average(String vote_average)
    {
        this.vote_average = vote_average;
    }

    public void setTrailer_name(String trailer_name)
    {
        this.trailer_name=trailer_name;
    }

    public void setTrailer_key(String trailer_key)
    {
        this.trailer_key = trailer_key;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPoster_path()
    {
        return poster_path;
    }

    public String getBackdrop_path()
    {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getTrailer_name()
    {
        return trailer_name;
    }

    public String getTrailer_key()
    {
         return trailer_key;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

}

