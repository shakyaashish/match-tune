package com.studio.matchtune.Api;


import com.studio.matchtune.ResponceModel.Attributedata;
import com.studio.matchtune.ResponceModel.LoginRoot;
import com.studio.matchtune.ResponceModel.MusicResponce;
import com.studio.matchtune.inputModel.MusicResponcedata;
import com.studio.matchtune.inputModel.responcedata;
import com.studio.matchtune.model.responcedatalogin;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {



    @POST("/musics")
    Call<MusicResponce> getMusicList(@Body JSONObject signupPos,@HeaderMap Map<String, String> headers);

//    @HeaderMap Map<String, String> headers,

    @POST("/tokens")
    Call<LoginRoot> getUserLogin(@Body responcedata signupPos);



    @POST("/tokens")
    Call<LoginRoot> getUserNotLogin(@Body responcedatalogin signupPos);






}
