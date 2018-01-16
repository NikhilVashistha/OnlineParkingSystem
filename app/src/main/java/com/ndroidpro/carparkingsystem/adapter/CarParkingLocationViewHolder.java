package com.ndroidpro.carparkingsystem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class CarParkingLocationViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;

    public CarParkingLocationViewHolder( View itemView ) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
    }

    public void bindToPost(CarParkingLocationModel carParkingLocationModel) {
        tvName.setText(carParkingLocationModel.getCarParkingLocationName());
    }
}
