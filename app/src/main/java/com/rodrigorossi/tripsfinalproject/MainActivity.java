package com.rodrigorossi.tripsfinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rodrigorossi.tripsfinalproject.adapter.TripAdapter;
import com.rodrigorossi.tripsfinalproject.model.ActivityOpenMode;
import com.rodrigorossi.tripsfinalproject.model.Trip;
import com.rodrigorossi.tripsfinalproject.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips = new ArrayList<>();
    private Trip tripSelected;
    private int positionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTrips = findViewById(R.id.recyclerViewTrips);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewTrips.setLayoutManager(layoutManager);
        recyclerViewTrips.setHasFixedSize(true);
        recyclerViewTrips.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        createFakeTripsList();

        recyclerViewTrips.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewTrips, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        tripSelected = trips.get(position);
                        positionSelected = position;
                        Toast.makeText(getApplicationContext(), tripSelected.getDestiny(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        tripSelected = trips.get(position);
                        positionSelected = position;
                        view.startActionMode(mActionModeCallback);
                    }
                }));
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflate = actionMode.getMenuInflater();
            inflate.inflate(R.menu.main_menu_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuContextEdit:
                    startActivityTrip(menuItem, ActivityOpenMode.UPDATE);
                    actionMode.finish();
                    return true;
                case R.id.menuContextDelete:
                    showConfirmDeleteDialog();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAbout:
                startActivityAbout(item);
                return true;
            case R.id.menuItemNew:
                startActivityTrip(item, ActivityOpenMode.NEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();

            tripSelected.setDestiny(bundle.getString(TripActivity.KEY_DESTINY));
            tripSelected.setInitialMileage(bundle.getInt(TripActivity.KEY_INITIAL_MILEAGE));
            tripSelected.setFinalMileage(bundle.getInt(TripActivity.KEY_FINAL_MILEAGE));
            tripSelected.setTripType(bundle.getInt(TripActivity.KEY_TRIP_TYPE));
            tripSelected.setVehicle(bundle.getInt(TripActivity.KEY_VEHICLE_TYPE));
            tripSelected.setRefund(bundle.getBoolean(TripActivity.KEY_REFUND));

            if (requestCode == ActivityOpenMode.NEW.getCode()) {
                tripSelected.setId(bundle.getInt(TripActivity.KEY_ID));
                addNewItem(tripSelected);
            } else {
                updateExistingItem();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startActivityAbout(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void startActivityTrip(MenuItem item, ActivityOpenMode openMode) {
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra("MODE", openMode);
        intent.putExtra(TripActivity.KEY_ID, trips.get(positionSelected).getId());
        intent.putExtra(TripActivity.KEY_DESTINY, trips.get(positionSelected).getDestiny());
        intent.putExtra(TripActivity.KEY_INITIAL_MILEAGE, trips.get(positionSelected).getInitialMileage());
        intent.putExtra(TripActivity.KEY_FINAL_MILEAGE, trips.get(positionSelected).getFinalMileage());
        intent.putExtra(TripActivity.KEY_TRIP_TYPE, trips.get(positionSelected).getTripType());
        intent.putExtra(TripActivity.KEY_VEHICLE_TYPE, trips.get(positionSelected).getVehicle());
        intent.putExtra(TripActivity.KEY_REFUND, trips.get(positionSelected).isRefund());
        startActivityForResult(intent, openMode.getCode());
    }

    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lblAreYouSure).setTitle(R.string.lblConfirmation);

        builder.setPositiveButton(R.string.lblYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteItem();
            }
        });
        builder.setNegativeButton(R.string.lblCancel,null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem() {
        trips.remove(positionSelected);
//        recyclerViewTrips.removeViewAt(positionSelected);
        tripAdapter.notifyDataSetChanged();
    }

    private void addNewItem(Trip trip) {
        trips.add(trip);
        tripAdapter.notifyDataSetChanged();
    }

    private void updateExistingItem() {
        trips.remove(positionSelected);
        trips.add(positionSelected, tripSelected);
        tripAdapter.notifyDataSetChanged();
    }

    private void createFakeTripsList() {
        trips.add(new Trip(1, "Foz", 123123, 123123, R.id.radioWork, true, 1));
        trips.add(new Trip(2, "Cascavel", 123123, 123123, R.id.radioLeisure, false, 0));
        trips.add(new Trip(3, "Toledo", 123123, 123123, R.id.radioPrivate, true, 2));
        trips.add(new Trip(4, "Guaíra", 123123, 123123, R.id.radioPrivate, true, 1));
        trips.add(new Trip(5, "Marechal Candido Rondon", 123123, 123123, R.id.radioPrivate, true, 1));
        trips.add(new Trip(6, "Curitiba", 123123, 123123, R.id.radioPrivate, false, 2));
        trips.add(new Trip(7, "Blumenau", 123123, 123123, R.id.radioPrivate, true, 1));

        tripAdapter = new TripAdapter(trips);
        recyclerViewTrips.setAdapter(tripAdapter);
    }
}
