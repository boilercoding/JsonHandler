package com.boilercoding.jsonhandler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Feed;
import model.children.Children;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://www.reddit.com/";
    private static final String LOGIN_URL = "https://www.reddit.com/api/login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnGetData = (Button) findViewById(R.id.btnGetData);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        final EditText etName = (EditText) findViewById(R.id.input_username);
        final EditText etPass = (EditText) findViewById(R.id.input_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = etName.getText().toString();
                String pass = etPass.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(LOGIN_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RedditAPI redditAPI = retrofit.create(RedditAPI.class);

                HashMap<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");


                Call<ResponseBody> call = redditAPI.login(headerMap, uName, uName, pass, "json");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(MainActivity.this, "onResponse: Server Response: " + response.toString(), Toast.LENGTH_LONG).show();

                        try{
                            String json = response.body().string();
                            Toast.makeText(MainActivity.this, "onResponse: json: " + json, Toast.LENGTH_LONG).show();

                        }catch (IOException e){
                            Toast.makeText(MainActivity.this,  "onResponse: JSONException: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RedditAPI redditAPI = retrofit.create(RedditAPI.class);
                Call<Feed> call = redditAPI.getData();

                call.enqueue(new Callback<Feed>() {
                    @Override
                    public void onResponse(Call<Feed> call, Response<Feed> response) {
                        Toast.makeText(MainActivity.this, "onResponse: Server Response: " + response.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "onResponse: received information: " + response.body().toString(), Toast.LENGTH_SHORT).show();

                        ArrayList<Children> childrenList = response.body().getData().getChildren();
                        for( int i = 0; i<childrenList.size(); i++){
                            Toast.makeText(MainActivity.this, "onResponse: \n" +
                                    "kind: " + childrenList.get(i).getKind() + "\n" +
                                    "contest_mode: " + childrenList.get(i).getData().getContest_mode() + "\n" +
                                    "subreddit: " + childrenList.get(i).getData().getSubreddit()  + "\n" +
                                    "author: " + childrenList.get(i).getData().getAuthor()  + "\n" +
                                    "-------------------------------------------------------------------------\n\n", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Feed> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Something went wrong: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
