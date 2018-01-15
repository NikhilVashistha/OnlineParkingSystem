package com.ndroidpro.carparkingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarParkingModel implements Parcelable {

    private int selectedPosition;
    private String label;
    private int hour;
    private int price;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.selectedPosition);
        dest.writeString(this.label);
        dest.writeInt(this.hour);
        dest.writeInt(this.price);
    }

    public CarParkingModel() {
    }

    protected CarParkingModel(Parcel in) {
        this.selectedPosition = in.readInt();
        this.label = in.readString();
        this.hour = in.readInt();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<CarParkingModel> CREATOR = new Parcelable.Creator<CarParkingModel>() {
        public CarParkingModel createFromParcel(Parcel source) {
            return new CarParkingModel(source);
        }

        public CarParkingModel[] newArray(int size) {
            return new CarParkingModel[size];
        }
    };
}
