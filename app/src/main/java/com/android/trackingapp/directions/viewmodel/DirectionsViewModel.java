package com.android.trackingapp.directions.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.android.trackingapp.directions.model.Direction;
import com.android.trackingapp.directions.repository.DirectionServiceApi;
import com.android.trackingapp.directions.repository.DirectionsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by MohamedHamdy on 6/5/2018.
 */
public class DirectionsViewModel extends ViewModel {
    DirectionsRepository directionsRepository;
    @Inject
    DirectionsViewModel(DirectionsRepository directionsRepository){
        this.directionsRepository = directionsRepository;
    }
    public LiveData<Direction> getDirectionLiveData(String origin, String destination, String key){
        return directionsRepository.getDirectionsTo(origin,destination,key);
    }
    public  LiveData<String[]> getPoylineLiveData(Direction direction){
        return directionsRepository.getPolyline(direction);
    }
}
