package com.pegalite.pegaserver;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterfaces {
    @Headers("Content-Type: application/json")
    @POST("/addChildEventListener")
    Call<ResponseBody> addChildEventListener(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("/addListenerForSingleValueEvent")
    Call<ResponseBody> addListenerForSingleValueEvent(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @PUT("/setValue")
    Call<ResponseBody> setValue(@Body RequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("/removeValue")
    Call<ResponseBody> removeValue(@Body RequestBody requestBody);

}
