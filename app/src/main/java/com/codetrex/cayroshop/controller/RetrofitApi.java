package com.codetrex.cayroshop.controller;

import com.codetrex.cayroshop.model.ClientShippingAddress;
import com.codetrex.cayroshop.model.GetShippingAddress;
import com.codetrex.cayroshop.model.RegisterUser;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApi {

    @Headers("Content-Type: application/json")
    @POST("auth/RegisterClient")
    Call<RegisterUser>registeruser2(@Body JSONObject body);

    @POST("Client/GetShippingAddress/{ClientId}")
    Call<ArrayList<ClientShippingAddress>> getclientShippingAddress(@Path("ClientId") String ClientId);
}