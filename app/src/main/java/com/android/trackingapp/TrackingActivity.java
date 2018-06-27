package com.android.trackingapp;

import android.*;
import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.trackingapp.di.Injectable;
import com.android.trackingapp.directions.viewmodel.DirectionsViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import javax.inject.Inject;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback
        , LocationListener, GoogleMap.OnMapClickListener , Injectable {


    GoogleMap mMap;
    private LocationManager locationManager;
    private String locationProvider;
    private Location lastLocation;
    private Marker myLocationMarker, targetMarker;
    DirectionsViewModel directionsViewModel;
    private Polyline[] polylineArray;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        directionsViewModel = ViewModelProviders.of(this,viewModelFactory).get(DirectionsViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 0);

            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(new Criteria(), false);
        locationManager.requestLocationUpdates(locationProvider, 1, 10, this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        lastLocation = locationManager.getLastKnownLocation(locationProvider);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("location", "My location  " + location.getLatitude());
        lastLocation = location;
        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        if (myLocationMarker != null) myLocationMarker.remove();
        myLocationMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(latlng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(mMap);
                }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (targetMarker != null) targetMarker.remove();
        targetMarker = mMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        Location targetLocation = new Location("");
        targetLocation.setLongitude(latLng.longitude);
        targetLocation.setLatitude(latLng.latitude);
        directionsViewModel.getDirectionLiveData(lastLocation.getLatitude()
                        + ","
                        + lastLocation.getLongitude(), latLng.latitude + "," + latLng.longitude,
                getString(R.string.api_key))
                .observe(this, direction -> {
                    directionsViewModel.getPoylineLiveData(direction).observe(this,
                            polylines -> {
                                if (polylineArray != null) {
                                    for (Polyline polyline : polylineArray) {
                                        polyline.remove();
                                    }
                                }
                                if (polylines != null) {
                                    polylineArray = new Polyline[polylines.length];
                                    Log.d("reached here", "reached here");
                                    for (int i=0;i<polylines.length;i++) {
                                        PolylineOptions options = new PolylineOptions();
                                        options.color(Color.RED);
                                        options.width(10);
                                        options.addAll(PolyUtil.decode(polylines[i]));
                                        polylineArray[i] = mMap.addPolyline(options);

                                    }
                                }

                            });
                });
    }
}
