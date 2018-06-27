package com.android.trackingapp.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.android.trackingapp.MVVMApplication;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjectionModule;

public class AppInjector {
    private AppInjector(){}

    public static void init(MVVMApplication mvvmApplication){
        DaggerAppComponent.builder().application(mvvmApplication)
                .build().inject(mvvmApplication);
        mvvmApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(){

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity) {
        if(activity instanceof  Injectable){
            AndroidInjection.inject(activity);
        }
    }
}
