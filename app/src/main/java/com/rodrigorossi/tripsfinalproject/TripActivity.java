package com.rodrigorossi.tripsfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import com.rodrigorossi.tripsfinalproject.model.ActivityOpenMode;
import com.rodrigorossi.tripsfinalproject.model.Trip;

import java.util.Random;

public class TripActivity extends AppCompatActivity {
    public static String KEY_ID = "ID";
    public static String KEY_DESTINY = "DESTINY";
    public static String KEY_INITIAL_MILEAGE = "INITIAL_MILEAGE";
    public static String KEY_FINAL_MILEAGE = "FINAL_MILEAGE";
    public static String KEY_TRIP_TYPE = "TRIP_TYPE";
    public static String KEY_VEHICLE_TYPE = "VEHICLE_TYPE";
    public static String KEY_REFUND = "REFUND";

    private Trip trip;
    private EditText editDestiny, editInitialMileage, editFinalMileage;
    private RadioGroup radioTripType;
    private Spinner spinnerVehicleType;
    private Switch switchRefund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        trip = new Trip();

        editDestiny = findViewById(R.id.editDestiny);
        editInitialMileage = findViewById(R.id.editInitialMileage);
        editFinalMileage = findViewById(R.id.editFinalMileage);
        radioTripType = findViewById(R.id.radioGroupTripType);
        spinnerVehicleType = findViewById(R.id.spinnerVehicleType);
        switchRefund = findViewById(R.id.switchRefound);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ActivityOpenMode mode = (ActivityOpenMode) bundle.getSerializable("MODE");
            if (mode != null) {
                switch (mode) {
                    case NEW:
                        startForNew();
                        break;
                    case UPDATE:
                        startForUpdate(bundle);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuItem_trip_save) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startForNew() {
        setTitle(getString(R.string.lblNewTrip));
    }

    private void startForUpdate(Bundle bundle) {
        setTitle(getString(R.string.lblUpdateTrip));

        trip.setId(bundle.getInt(KEY_ID));
        trip.setDestiny(bundle.getString(KEY_DESTINY));
        trip.setInitialMileage(bundle.getInt(KEY_INITIAL_MILEAGE));
        trip.setFinalMileage(bundle.getInt(KEY_FINAL_MILEAGE));
        trip.setTripType(bundle.getInt(KEY_TRIP_TYPE));
        trip.setVehicle(bundle.getInt(KEY_VEHICLE_TYPE));
        trip.setRefund(bundle.getBoolean(KEY_REFUND));

        editDestiny.setText(trip.getDestiny());
        editInitialMileage.setText(String.valueOf(trip.getInitialMileage()));
        editFinalMileage.setText(String.valueOf(trip.getFinalMileage()));
        radioTripType.check(trip.getTripType());
        spinnerVehicleType.setSelection(trip.getVehicle());
        switchRefund.setChecked(trip.isRefund());
    }

    private void save() {
        if (trip.getId() == 0) {
            // TODO quando implementar o BD pegar o primeiro ID disponível (auto-incremento)
            trip.setId(new Random().nextInt());
        }

        trip.setDestiny(editDestiny.getText().toString());
        trip.setInitialMileage(Integer.parseInt(editInitialMileage.getText().toString()));
        trip.setFinalMileage(Integer.parseInt(editFinalMileage.getText().toString()));
        trip.setTripType(radioTripType.getCheckedRadioButtonId());
        trip.setVehicle(spinnerVehicleType.getSelectedItemPosition());
        trip.setRefund(switchRefund.isChecked());

        setResult(Activity.RESULT_OK, setExtraBundles());
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private Intent setExtraBundles() {
        Intent intent = new Intent();
        intent.putExtra(KEY_ID, trip.getId());
        intent.putExtra(KEY_DESTINY, trip.getDestiny());
        intent.putExtra(KEY_INITIAL_MILEAGE, trip.getInitialMileage());
        intent.putExtra(KEY_FINAL_MILEAGE, trip.getFinalMileage());
        intent.putExtra(KEY_TRIP_TYPE, trip.getTripType());
        intent.putExtra(KEY_VEHICLE_TYPE, trip.getVehicle());
        intent.putExtra(KEY_REFUND, trip.isRefund());
        return intent;
    }
}