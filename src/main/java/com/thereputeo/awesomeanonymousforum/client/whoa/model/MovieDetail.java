package com.thereputeo.awesomeanonymousforum.client.whoa.model;

import java.io.Serializable;

public class MovieDetail implements Serializable {
    private String movie;
    private Integer year;
    private String release_date;
    private String director;
    private String character;
    private String movie_duration;
    private String timestamp;
    private String full_line;
    private Integer current_whoa_in_movie;
    private Integer total_whoas_in_movie;
    private Object whoa_grouping;
    private String poster;
    private Video video;
    private String audio;

    public MovieDetail() {
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getMovie_duration() {
        return movie_duration;
    }

    public void setMovie_duration(String movie_duration) {
        this.movie_duration = movie_duration;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFull_line() {
        return full_line;
    }

    public void setFull_line(String full_line) {
        this.full_line = full_line;
    }

    public int getCurrent_whoa_in_movie() {
        return current_whoa_in_movie;
    }

    public void setCurrent_whoa_in_movie(int current_whoa_in_movie) {
        this.current_whoa_in_movie = current_whoa_in_movie;
    }

    public int getTotal_whoas_in_movie() {
        return total_whoas_in_movie;
    }

    public void setTotal_whoas_in_movie(int total_whoas_in_movie) {
        this.total_whoas_in_movie = total_whoas_in_movie;
    }

    public Object getWhoa_grouping() {
        return whoa_grouping;
    }

    public void setWhoa_grouping(Object whoa_grouping) {
        this.whoa_grouping = whoa_grouping;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "MovieDetail{" +
                "movie='" + movie + '\'' +
                ", year=" + year +
                ", release_date='" + release_date + '\'' +
                ", director='" + director + '\'' +
                ", character='" + character + '\'' +
                ", movie_duration='" + movie_duration + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", full_line='" + full_line + '\'' +
                ", current_whoa_in_movie=" + current_whoa_in_movie +
                ", total_whoas_in_movie=" + total_whoas_in_movie +
                ", whoa_grouping=" + whoa_grouping +
                ", poster='" + poster + '\'' +
                ", video=" + video +
                ", audio='" + audio + '\'' +
                '}';
    }
}
