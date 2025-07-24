package com.thereputeo.awesomeanonymousforum.client.whoa.model;

import java.io.Serializable;
import java.util.List;

public class WhoaClientResponse implements Serializable {
    private List<MovieDetail> movieDetails;

    public WhoaClientResponse() {
    }

    public List<MovieDetail> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MovieDetail> movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    public String toString() {
        return "WhoaClientResponse{" +
                "movieDetails=" + movieDetails +
                '}';
    }
}
