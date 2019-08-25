package com.airit.test.Interfaces;

import com.airit.test.Models.HomeModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HomeAPI {

    @GET("contacts.json")
    Call <List<HomeModel>> getData();
}
