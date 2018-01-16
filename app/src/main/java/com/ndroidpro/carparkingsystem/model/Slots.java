package com.ndroidpro.carparkingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Slots implements Parcelable {

    private int parkingSlotId;
    private String parkingLocationId;
    private boolean isBooked = false;
    private long lastBookingTime;

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(int parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }

    public String getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(String parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public long getLastBookingTime() {
        return lastBookingTime;
    }

    public void setLastBookingTime(long lastBookingTime) {
        this.lastBookingTime = lastBookingTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.parkingSlotId);
        dest.writeString(this.parkingLocationId);
        dest.writeByte(isBooked ? (byte) 1 : (byte) 0);
        dest.writeLong(this.lastBookingTime);
    }

    public Slots() {
    }

    protected Slots(Parcel in) {
        this.parkingSlotId = in.readInt();
        this.parkingLocationId = in.readString();
        this.isBooked = in.readByte() != 0;
        this.lastBookingTime = in.readLong();
    }

    public static final Parcelable.Creator<Slots> CREATOR = new Parcelable.Creator<Slots>() {
        public Slots createFromParcel(Parcel source) {
            return new Slots(source);
        }

        public Slots[] newArray(int size) {
            return new Slots[size];
        }
    };
}
