package com.ndroidpro.carparkingsystem.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.listener.ClickListener;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class CarParkingLocationAdapter extends FirebaseRecyclerAdapter<CarParkingLocationModel, CarParkingLocationViewHolder> {

    private ClickListener clickListener;

    public CarParkingLocationAdapter(FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    public CarParkingLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_car_parking_location, parent, false);
        return new CarParkingLocationViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(CarParkingLocationViewHolder viewHolder, final int position,
                                    final CarParkingLocationModel carParkingLocationModel) {
        final DatabaseReference databaseReference = getRef(position);

        final String locationId = databaseReference.getKey();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(carParkingLocationModel, locationId);
            }
        });

        viewHolder.bindToPost(carParkingLocationModel);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
