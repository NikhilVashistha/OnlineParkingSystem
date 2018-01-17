package com.ndroidpro.carparkingsystem.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.ndroidpro.carparkingsystem.Constants;
import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.activity.AddNewParkingLocation;
import com.ndroidpro.carparkingsystem.activity.BaseActivity;
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
    protected void onBindViewHolder(final CarParkingLocationViewHolder viewHolder, final int position,
                                    final CarParkingLocationModel carParkingLocationModel) {
        final DatabaseReference databaseReference = getRef(position);

        final String locationId = databaseReference.getKey();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(carParkingLocationModel, locationId);
            }
        });

        final Context context = viewHolder.itemView.getContext();
        final boolean userAdmin = ((BaseActivity) context).isUserAdmin();

        viewHolder.bindToPost(carParkingLocationModel, userAdmin, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userAdmin) {
                    carParkingLocationModel.setCarParkingLocationId(locationId);

                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.INTENT_EDIT_CAR_PARKING_LOCATION, true);
                    bundle.putParcelable(Constants.INTENT_CAR_PARKING_LOCATION_DATA, carParkingLocationModel);
                    ActivityUtils.startActivity(bundle, AddNewParkingLocation.class);
                }
            }
        });
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
