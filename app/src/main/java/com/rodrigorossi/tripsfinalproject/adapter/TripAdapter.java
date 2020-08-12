package com.rodrigorossi.tripsfinalproject.adapter;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodrigorossi.tripsfinalproject.R;
import com.rodrigorossi.tripsfinalproject.model.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    private List<Trip> tripList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textViewTrip;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrip  = itemView.findViewById(R.id.textViewTrip);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }

    public TripAdapter(List<Trip> list){
        this.tripList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_adapter, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trip t = tripList.get(position);
        holder.textViewTrip.setText(t.getDestiny());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
