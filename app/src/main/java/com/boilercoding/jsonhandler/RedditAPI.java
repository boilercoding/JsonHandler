package com.boilercoding.jsonhandler;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import model.Feed;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Boilercoding on 8/22/2017.
 */

public interface RedditAPI {

    String BASE_URL = "https://www.reddit.com/";

    @Headers("Content-Type: application/json")
    @GET(".json")
    Call<Feed> getData();

    @POST("{user}")
    Call<ResponseBody> login(
            @HeaderMap Map<String, String> headers,
            @Path("user") String username,  //codingwithmitch
            @Query("user") String user,       //?user=codingwithmitch
            @Query("passwd") String password, //&passwd=Mitchtabian1234!
            @Query("api-type") String type    //&api-type=json
    );
}
