package com.android.trackingapp.di;

import com.android.trackingapp.TrackingActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TrackingActivityModule {
    @ContributesAndroidInjector
    abstract TrackingActivity contributeActivity();
}
