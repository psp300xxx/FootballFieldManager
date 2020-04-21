package com.example.footballfieldmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.sport_center.LDMSportCenter;
import com.example.footballfieldmanager.controller.sport_center.SportCenterDelegate;
import com.example.footballfieldmanager.controller.sport_center.SportCenterIF;
import com.example.footballfieldmanager.fragments.SportCenterFragment;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.SportCenter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class HomeActivity extends AppCompatActivity implements SportCenterDelegate, LocationListener, SportCenterFragment.OnListFragmentInteractionListener {





    private SportCenterFragment fragment;
    private SportCenterIF sportCenterIF;
    private String currentCity;
    private final int LOCATION_PERMISSION = 13221;
    private LocationManager locationManager;
    private final int MIN_DISTANCE_CHANGE_METER = 0;
    private final int MIN_TIME_BETWEEN_UPDATES = 1 * 1000; //20 SECONDS
    private Button searchFriendsButton;
    private Button searchSportCenterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        askGeoPermission();
        searchFriendsButton = (Button) findViewById(R.id.search_friends_button);
        searchFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchFriendsActivity.class);
                startActivity(intent);
            }
        });
        fragment = (SportCenterFragment) getSupportFragmentManager().findFragmentById(R.id.sport_center_fragment);
        searchSportCenterButton = (Button) findViewById(R.id.search_sport_center_button);
        searchSportCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchSportCenterActivity.class);
                startActivity(intent);
            }
        });
        loadCurrentCity();
    }

    public void askGeoPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        loadCurrentCity();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String permissions[], @NonNull int[] grantResults) {
        if (!(requestCode == LOCATION_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, "permission not granted", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onListFragmentInteraction(SportCenter item) {
        Intent intent = new Intent(HomeActivity.this, BookMatchActivity.class);
        intent.putExtra("center", item);
        startActivity(intent);
    }

    private void loadCurrentCity() {
        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, getResources().getText(R.string.location_error), Toast.LENGTH_SHORT).show();
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BETWEEN_UPDATES, MIN_DISTANCE_CHANGE_METER, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadCurrentCity();
    }

    @Override
    public void sportCenterDownloaded(List<SportCenter> centers) {
        fragment.setList(centers, this);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void sportCenterDownloadFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void footballFieldDownloaded(List<FootballField> footballFields) {

    }

    @Override
    public void onLocationChanged(Location location) {
        sportCenterIF = new LDMSportCenter();
        sportCenterIF.setDelegate(this);
        RequestQueue queue = RequestQueueSingleton.getInstance(this).getQueue();
        sportCenterIF.searchSportCenters(location.getLatitude(), location.getLongitude(), queue);
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
}
