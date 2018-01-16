package com.ndroidpro.carparkingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarParkingLocationModel implements Parcelable {

    private String carParkingLocationId;
    private String carParkingLocationName;
    private int availableCarParking;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carParkingLocationId);
        dest.writeString(this.carParkingLocationName);
        dest.writeInt(this.availableCarParking);
    }

    protected CarParkingLocationModel(Parcel in) {
        this.carParkingLocationId = in.readString();
        this.carParkingLocationName = in.readString();
        this.availableCarParking = in.readInt();
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
