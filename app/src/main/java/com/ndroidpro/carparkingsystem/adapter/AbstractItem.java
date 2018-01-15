package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.CarParkingModel;

public abstract class AbstractItem {

    public static final int TYPE_CENTER = 0;
    public static final int TYPE_EDGE = 1;
    public static final int TYPE_EMPTY = 2;
    private CarParkingModel mCarParkingModel;

    private String label;


    public AbstractItem(String label) {
        this.label = label;
    }

    public AbstractItem(CarParkingModel carParkingModel) {
        this.mCarParkingModel = carParkingModel;
    }

    public CarParkingModel getCarParkingModel() {
        return mCarParkingModel;
    }

    public void setCarParkingModel(CarParkingModel carParkingModel) {
        mCarParkingModel = carParkingModel;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    abstract public int getType();




}
