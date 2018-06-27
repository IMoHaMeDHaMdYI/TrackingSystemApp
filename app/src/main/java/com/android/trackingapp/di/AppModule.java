package com.android.trackingapp.di;

import android.arch.lifecycle.ViewModelProvider;

import com.android.trackingapp.directions.repository.DirectionServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
public class AppModule {
    @Singleton
    @Provides
    DirectionServiceApi proviceDirectionsApi() {
        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DirectionServiceApi.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelBuilder)
    {
        return new ViewModelFactory(viewModelBuilder.build());
    }


}
