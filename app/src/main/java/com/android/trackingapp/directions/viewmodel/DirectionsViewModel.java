package com.android.trackingapp.directions.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android.trackingapp.directions.model.Direction;
import com.android.trackingapp.directions.repository.DirectionsRepository;

/**
 * Created by MohamedHamdy on 6/5/2018.
 */

public class DirectionsViewModel extends ViewModel {
    public LiveData<Direction> getDirectionLiveData(String origin, String destination, String key){
        return DirectionsRepository.getInstance().getDirectionsTo(origin,destination,key);
    }
    public  LiveData<String[]> getPoylineLiveData(Direction direction){
        return DirectionsRepository.getInstance().getPolyline(direction);
    }
}
