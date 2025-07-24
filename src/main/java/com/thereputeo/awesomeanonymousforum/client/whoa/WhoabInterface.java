package com.thereputeo.awesomeanonymousforum.client.whoa;


import com.thereputeo.awesomeanonymousforum.client.whoa.model.MovieDetail;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface WhoabInterface {
    @GET("random")
    Call<List<MovieDetail>> getRandomWhoaMovie();
}
