package com.ndroidpro.carparkingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CarParkingLocationModel implements Parcelable {

    private String carParkingLocationId;
    private String carParkingLocationName;
    private int availableCarParking;

    private ArrayList<Slots> mSlots;

    public CarParkingLocationModel() {
    }

    public String getCarParkingLocationName() {
        return carParkingLocationName;
    }

    public void setCarParkingLocationName(String carParkingLocationName) {
        this.carParkingLocationName = carParkingLocationName;
    }

    public int getAvailableCarParking() {
        return availableCarParking;
    }

    public void setAvailableCarParking(int availableCarParking) {
        this.availableCarParking = availableCarParking;
    }

    public String getCarParkingLocationId() {
        return carParkingLocationId;
    }

    public void setCarParkingLocationId(String carParkingLocationId) {
        this.carParkingLocationId = carParkingLocationId;
    }

    public ArrayList<Slots> getSlots() {
        return mSlots;
    }

    public void setSlots(ArrayList<Slots> slots) {
        mSlots = slots;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carParkingLocationId);
        dest.writeString(this.carParkingLocationName);
        dest.writeInt(this.availableCarParking);
        dest.writeList(this.mSlots);
    }

    protected CarParkingLocationModel(Parcel in) {
        this.carParkingLocationId = in.readString();
        this.carParkingLocationName = in.readString();
        this.availableCarParking = in.readInt();
        this.mSlots = new ArrayList<Slots>();
        in.readList(this.mSlots, getClass().getClassLoader());
    }

    public static final Creator<CarParkingLocationModel> CREATOR = new Creator<CarParkingLocationModel>() {
        public CarParkingLocationModel createFromParcel(Parcel source) {
            return new CarParkingLocationModel(source);
        }

        public CarParkingLocationModel[] newArray(int size) {
            return new CarParkingLocationModel[size];
        }
    };
}
