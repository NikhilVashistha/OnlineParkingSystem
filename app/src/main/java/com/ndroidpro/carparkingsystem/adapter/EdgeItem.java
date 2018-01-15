package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.CarParkingModel;

public class EdgeItem extends AbstractItem {

    private CarParkingModel mCarParkingModel;

    public EdgeItem(String label) {
        super(label);
    }

    public EdgeItem(CarParkingModel carParkingModel) {
        super(carParkingModel);
    }

    @Override
    public int getType() {
        return TYPE_EDGE;
    }

}
