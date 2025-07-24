package com.thereputeo.awesomeanonymousforum.client.whoa;


import com.thereputeo.awesomeanonymousforum.client.whoa.model.WhoaClientResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WhoabInterface {
    @GET("random")
    Call<WhoaClientResponse> getRandomWhoaMovie();
}
