package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.Slots;

public class CenterItem extends AbstractItem {

    public CenterItem(String label) {
        super(label);
    }

    public CenterItem(Slots slot) {
        super(slot);
    }

    @Override
    public int getType() {
        return TYPE_CENTER;
    }

}
