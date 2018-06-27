package com.android.trackingapp.di;

import android.app.Activity;
import android.app.Application;

import com.android.trackingapp.MVVMApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        TrackingActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(MVVMApplication mvvmApplication);
}
