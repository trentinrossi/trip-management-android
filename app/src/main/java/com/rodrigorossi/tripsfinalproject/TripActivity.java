package com.rodrigorossi.tripsfinalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.rodrigorossi.tripsfinalproject.model.ActivityOpenMode;

public class TripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ActivityOpenMode mode = (ActivityOpenMode) bundle.getSerializable("MODE");

        switch (mode) {
            case NEW:
                startForNew();
                break;
            case UPDATE:
                startForUpdate();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_menu, menu);
        return true;
    }

    private void startForNew() {
        setTitle(getString(R.string.lblNewTrip));
    }

    private void startForUpdate() {
        setTitle(getString(R.string.lblUpdateTrip));
    }
}