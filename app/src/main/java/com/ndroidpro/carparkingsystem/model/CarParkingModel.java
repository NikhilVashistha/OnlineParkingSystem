package com.ndroidpro.carparkingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarParkingModel implements Parcelable {

    private int selectedPosition;
    private String label;
    private int hour;
    private int price;
    private String userId;
    private String token;
    private Slots mSlots;

    public Slots getSlots() {
        return mSlots;
    }

    public void setSlots(Slots slots) {
        mSlots = slots;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CarParkingModel() {
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
        dest.writeString(this.userId);
        dest.writeString(this.token);
        dest.writeParcelable(this.mSlots, 0);
    }

    protected CarParkingModel(Parcel in) {
        this.selectedPosition = in.readInt();
        this.label = in.readString();
        this.hour = in.readInt();
        this.price = in.readInt();
        this.userId = in.readString();
        this.token = in.readString();
        this.mSlots = in.readParcelable(Slots.class.getClassLoader());
    }

    public static final Creator<CarParkingModel> CREATOR = new Creator<CarParkingModel>() {
        public CarParkingModel createFromParcel(Parcel source) {
            return new CarParkingModel(source);
        }

        public CarParkingModel[] newArray(int size) {
            return new CarParkingModel[size];
        }
    };
}
