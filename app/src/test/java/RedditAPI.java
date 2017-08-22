package com.boilercoding.jsonhandler;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import model.Feed;

/**
 * Created by Boilercoding on 8/22/2017.
 */

public interface RedditAPI {

    String BASE_URL = "https://www.reddit.com/";

    @Headers("Content-Type: application/json")
    @GET(".json")
    Call<Feed> getData();
}
