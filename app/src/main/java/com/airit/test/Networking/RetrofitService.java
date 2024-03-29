package com.airit.test.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creating the Retrofit Service
 */
public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.androidhive.info/json/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
