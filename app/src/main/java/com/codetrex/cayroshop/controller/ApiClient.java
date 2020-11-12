package com.codetrex.cayroshop.controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://Cayroapi.codetrex.in/api/";
    private static Retrofit retrofit = null;
    private static final int processTime = 1;




    public static Retrofit getClient() {

        //OAuth 1.0 configuration
        /*OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(TWITTER_API_KEY, TWITTER_API_SECRET_KEY);
        consumer.setTokenWithSecret(TWITTER_ACCESS_TOKEN, TWITTER_ACCESS_TOKEN_SECRET);*/
//String authToken = "Bearer WS46OHXQvb4KBWnW_kPan1eHP5bOVmInCSo3cThpsgtQLaohpanCKS4KrIgspIZSaxQhjn8m224kwz4nUYbstKhrknaXImV3bhvODH69eLfyld_EVGNJ-Je7WL1Z4-fhU5btMn73-7niCtVdcJaeZiPmlEqBHZwQIXT9GVcpjXWx1bS9B2flV1CQtJLgFhwkhq6Yb4IRGVxpPPnO8i7K22COP9Z4K6XRIFEPrtQ5v4emdLNlK8hec9IEfukr6duE2aT1OHrwq0h-pgMVmC0J83oyfI5S1p0cyrbD5pyryMk";

        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept","application/json")
                        //.addHeader("Authorization","Bearer "+authToken)

                        //.addHeader(Constants.API_KEY, Constants.API_KEY_VALUE)
//                      .addHeader("hash", generateHash())
//                      .addHeader("api_key",apiKey)
//                    //.addHeader("authtoken","authtoken")
                        .build();
                return chain.proceed(newRequest);
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(processTime, TimeUnit.MINUTES);
        builder.connectTimeout(processTime, TimeUnit.MINUTES);
        builder.writeTimeout(processTime, TimeUnit.MINUTES);
        //builder.addInterceptor(new SigningInterceptor(consumer));
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
