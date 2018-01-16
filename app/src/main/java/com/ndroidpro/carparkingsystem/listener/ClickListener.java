package com.ndroidpro.carparkingsystem.listener;

import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public interface ClickListener {
    void onItemClick(CarParkingLocationModel carParkingLocationModel, String locationId);
}