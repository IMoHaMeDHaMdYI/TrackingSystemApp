package com.android.trackingapp.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.trackingapp.directions.viewmodel.DirectionsViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory{

    private final HashMap<Class,Callable<? extends ViewModel>> creators;
    ViewModelFactory(ViewModelSubComponent viewModelSubComponent){
        creators = new HashMap<>();
        creators.put(DirectionsViewModel.class, viewModelSubComponent::directionsViewModel);
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.d("modelClass","modelClass + " +  modelClass);
        Callable<? extends ViewModel> creator = creators.get(modelClass);
        if(creator == null ){
            for(Map.Entry<Class,Callable<? extends ViewModel>> entry : creators.entrySet()){
                if(modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if(creator == null){
            throw new IllegalArgumentException("Unknown class model" + modelClass);
        }
        try{
            return (T)creator.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
