package com.example.horinoa.weatherhacks;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by horinoA on 2016/02/09.
 */

public interface GeoCoderAPI {
    @GET("/maps/api/geocode/json?sensor=false&language=ja")
    Call<GeoEntity> getGeo(@Query("address") String address);
}
