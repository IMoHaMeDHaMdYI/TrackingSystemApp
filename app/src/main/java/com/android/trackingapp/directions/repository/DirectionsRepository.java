package com.android.trackingapp.directions.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.trackingapp.Client;
import com.android.trackingapp.directions.model.Direction;
import com.android.trackingapp.directions.model.Step;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by MohamedHamdy on 6/5/2018.
 */
@Singleton
public class DirectionsRepository {
    private static DirectionsRepository directionsRepository;
    private DirectionServiceApi directionServiceApi;
    private String baseUrl = "https://maps.googleapis.com/maps/api/";
    @Inject
    DirectionsRepository(DirectionServiceApi directionServiceApi){
        this.directionServiceApi = directionServiceApi;
    }

    public LiveData<Direction> getDirectionsTo(String origin, String destination, String key){
        final MutableLiveData<Direction> data = new MutableLiveData<>();
        directionServiceApi.getDirectionTo(origin,destination,key)
                .enqueue(new Callback<Direction>() {
                    @Override
                    public void onResponse(@NonNull Call<Direction> call, @NonNull Response<Direction> response) {
                        if(response.isSuccessful()){
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<String[]> getPolyline(Direction direction){
        MutableLiveData<String[]> polyline = new MutableLiveData<>();
        String[] polylineArray = null;

        if(direction != null && direction.getStatus().equals("OK")){
            int count = direction.getRoutes().get(0).getLegs().get(0).getSteps().size();
            Step[] steps = direction.getRoutes().get(0).getLegs().get(0).getSteps().toArray(new Step[count]);
            polylineArray = new String[count];
            for(int i=0;i<count;i++){
                polylineArray[i] = steps[i].getPolyline().getPoints();
            }
        }
        polyline.setValue(polylineArray);
        return polyline;
    }


}
