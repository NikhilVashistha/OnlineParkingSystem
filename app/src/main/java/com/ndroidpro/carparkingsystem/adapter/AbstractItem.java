package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.Slots;

public abstract class AbstractItem {

    public static final int TYPE_CENTER = 0;
    public static final int TYPE_EDGE = 1;
    public static final int TYPE_EMPTY = 2;
    private Slots mSlot;

    private String label;


    public AbstractItem(String label) {
        this.label = label;
    }

    public AbstractItem(Slots slot) {
        this.mSlot = slot;
    }

    public Slots getSlot() {
        return mSlot;
    }

    public void setSlot(Slots slot) {
        mSlot = slot;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    abstract public int getType();




}
