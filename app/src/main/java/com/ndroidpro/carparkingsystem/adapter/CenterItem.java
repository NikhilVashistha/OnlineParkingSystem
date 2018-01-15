package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.CarParkingModel;

public class CenterItem extends AbstractItem {

    private CarParkingModel mCarParkingModel;

    public CenterItem(String label) {
        super(label);
    }

    public CenterItem(CarParkingModel carParkingModel) {
        super(carParkingModel);
    }

    @Override
    public int getType() {
        return TYPE_CENTER;
    }

}
