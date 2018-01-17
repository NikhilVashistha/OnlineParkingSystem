package com.ndroidpro.carparkingsystem.adapter;

import com.ndroidpro.carparkingsystem.model.Slots;

public class EdgeItem extends AbstractItem {

    public EdgeItem(String label) {
        super(label);
    }

    public EdgeItem(Slots slot) {
        super(slot);
    }

    @Override
    public int getType() {
        return TYPE_EDGE;
    }

}
