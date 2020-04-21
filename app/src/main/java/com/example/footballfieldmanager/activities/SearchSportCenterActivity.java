package com.example.footballfieldmanager.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.sport_center.LDMSportCenter;
import com.example.footballfieldmanager.controller.sport_center.SportCenterDelegate;
import com.example.footballfieldmanager.controller.sport_center.SportCenterIF;
import com.example.footballfieldmanager.fragments.SportCenterFragment;
import com.example.footballfieldmanager.model.FootballField;
import com.example.footballfieldmanager.model.SportCenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.footballfieldmanager.R;

import java.util.List;

public class SearchSportCenterActivity extends AppCompatActivity implements SportCenterFragment.OnListFragmentInteractionListener, SportCenterDelegate {

    private SportCenterFragment sportCenterFragment;
    private EditText centerEditText;
    private SportCenterIF sportCenterIF = new LDMSportCenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sport_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        centerEditText = (EditText)findViewById(R.id.city_edit_text);
        sportCenterFragment =(SportCenterFragment) getSupportFragmentManager().findFragmentById(R.id.center_search_fragment);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = centerEditText.getText().toString();
                sportCenterIF.setDelegate(SearchSportCenterActivity.this);
                RequestQueue queue = RequestQueueSingleton.getInstance(SearchSportCenterActivity.this).getQueue();
                sportCenterIF.searchSportCenters(cityName, queue);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(SportCenter item) {

        Intent intent = new Intent(SearchSportCenterActivity.this, BookMatchActivity.class);
        intent.putExtra("center", item);
        startActivity(intent);

    }

    @Override
    public void sportCenterDownloaded(List<SportCenter> centers) {
        sportCenterFragment.setList(centers, this);
    }

    @Override
    public void sportCenterDownloadFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void footballFieldDownloaded(List<FootballField> footballFields) {
//        NOT USED HERE
    }
}
