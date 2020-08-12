package com.rodrigorossi.tripsfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.rodrigorossi.tripsfinalproject.model.Trip;
import com.rodrigorossi.tripsfinalproject.util.RecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTrips;
    private RecyclerView.LayoutManager layoutManager;
    private TripAdapter tripAdapter;
    private ArrayList<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTrips = findViewById(R.id.recyclerViewTrips);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewTrips.setLayoutManager(layoutManager);
        recyclerViewTrips.setHasFixedSize(true);
        recyclerViewTrips.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        addTripOnList();

        recyclerViewTrips.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewTrips, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Trip t = trips.get(position);
                        Toast.makeText(getApplicationContext(), t.getDestiny(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Trip t = trips.get(position);
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
                    startActivityTrip(menuItem);
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
                startActivityTrip(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startActivityAbout(MenuItem item) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void startActivityTrip(MenuItem item) {
        Intent intent = new Intent(this, TripActivity.class);
        startActivity(intent);
    }

    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.lblAreYouSure).setTitle(R.string.lblConfirmation);

        builder.setPositiveButton(R.string.lblYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO Implementar um método de exclusão da viagem da lista
            }
        });
        builder.setNegativeButton(R.string.lblCancel,null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addTripOnList() {
        trips.add(new Trip(1, "Foz"));
        trips.add(new Trip(2, "Cascavel"));
        trips.add(new Trip(3, "Toledo"));
        trips.add(new Trip(4, "Guaíra"));
        trips.add(new Trip(5, "Maringa"));
        trips.add(new Trip(6, "Salto do Lontra"));

        tripAdapter = new TripAdapter(trips);
        recyclerViewTrips.setAdapter(tripAdapter);
    }
}
