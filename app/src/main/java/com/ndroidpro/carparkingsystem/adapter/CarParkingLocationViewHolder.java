package com.ndroidpro.carparkingsystem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

public class CarParkingLocationViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private ImageView imvEdit;

    public CarParkingLocationViewHolder( View itemView ) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
        imvEdit = (ImageView) itemView.findViewById(R.id.btn_edit);
    }

    public void bindToPost(CarParkingLocationModel carParkingLocationModel,
                           boolean userAdmin, View.OnClickListener onClickListener) {
        tvName.setText(carParkingLocationModel.getCarParkingLocationName());
        if(userAdmin) {
            imvEdit.setVisibility(View.VISIBLE);
            imvEdit.setOnClickListener(onClickListener);
        }else {
            imvEdit.setVisibility(View.GONE);
        }
    }
}
