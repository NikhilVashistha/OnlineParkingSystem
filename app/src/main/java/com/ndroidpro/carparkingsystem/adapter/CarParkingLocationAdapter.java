package com.ndroidpro.carparkingsystem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.listener.ClickListener;
import com.ndroidpro.carparkingsystem.model.CarParkingLocationModel;

import java.util.Collections;
import java.util.List;

public class CarParkingLocationAdapter extends RecyclerView.Adapter<CarParkingLocationAdapter.ViewHolder>{

    private List<CarParkingLocationModel> locationList = Collections.emptyList();
    private ClickListener clickListener;

    public CarParkingLocationAdapter( List<CarParkingLocationModel> locationList ) {
        this.locationList = locationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car_parking_location, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CarParkingLocationModel carParkingLocationModel = locationList.get(position);
        holder.tvName.setText(carParkingLocationModel.getCarParkingLocationName());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(getAdapterPosition(), view);
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
