package com.taptapvpn.speedproxyvpn.APIClient_Class;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApis_Class {
    @GET("/")
    Call<ApiResponse_Class> requestip(@Query("format") String formate);

}
