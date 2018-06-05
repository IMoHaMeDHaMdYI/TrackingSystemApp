package com.android.trackingapp.directions.repository;

import com.android.trackingapp.directions.model.Direction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MohamedHamdy on 6/5/2018.
 */

public interface DirectionServiceApi {
    @GET("directions/json")
    Call<Direction> getDirectionTo(@Query("origin") String origin, @Query("destination")String destination, @Query("key") String key );
}
