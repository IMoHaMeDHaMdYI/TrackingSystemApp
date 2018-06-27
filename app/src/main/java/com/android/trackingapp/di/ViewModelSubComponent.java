package com.android.trackingapp.di;

import com.android.trackingapp.directions.viewmodel.DirectionsViewModel;

import dagger.Subcomponent;

@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }
    DirectionsViewModel directionsViewModel();
}
